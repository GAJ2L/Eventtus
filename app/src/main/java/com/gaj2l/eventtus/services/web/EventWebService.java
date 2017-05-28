package com.gaj2l.eventtus.services.web;


import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.lib.WebService;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Attachment;
import com.gaj2l.eventtus.models.Evaluation;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.models.Message;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by lucas tomasi on 30/04/17.
 */

public class EventWebService
{
    private static String CLASS = "EventService";

    public static void getEvent(String email, String hash, final ActionEvent action )
    {
        Map params = new HashMap();

        params.put( "email" , email );
        params.put( "hash"  , hash  );

        WebService.get(EventWebService.CLASS, "getEvent",new RequestParams(params),new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                action.onEvent(saveEvent(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                action.onEvent(null);
            }
        });
    }

    public static void refreshEvent(long eventServiceId, String email, final  ActionEvent action )
    {
        Map params = new HashMap();
        params.put("id"   , String.valueOf(eventServiceId));
        params.put("email", email);

        WebService.get(EventWebService.CLASS,"pullEvent", new RequestParams(params), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                action.onEvent(saveEvent(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                action.onEvent(null);
            }
        });
    }

    private static Event saveEvent(JSONObject response)
    {
        try
        {
            Event event = new Event();
            event.setEventServiceId( response.getLong("id") );
            event.setName( response.getString("name") );
            event.setBanner( response.getString("banner") );
            event.setContactMail( response.getString("contact_mail") );
            event.setContactName( response.getString("contact_name") );
            event.setContactPhone( response.getString("contact_phone") );
            event.setDescription( response.getString("description") );
            event.setDtStart( Util.parse2OffsetDateTime( response.getString("dt_start") ) );
            event.setDtEnd( Util.parse2OffsetDateTime( response.getString("dt_end") ) );
            event.setUserId(Session.getInstance(null).getLong("user"));

            // clearing event and activities and attachments
            ComponentProvider.getServiceComponent().getEventService().clearEvent(event);

            // saving event
            ComponentProvider.getServiceComponent().getEventService().store(event);

            JSONArray activities = response.getJSONArray("activities");

            for( int i = 0; i < activities.length(); i++ )
            {
                JSONObject actJson = activities.getJSONObject(i);

                Activity activity = new Activity();
                activity.setName( actJson.getString("name") );
                activity.setDtStart( Util.parse2OffsetDateTime( actJson.getString("dt_start") ) );
                activity.setDtEnd( Util.parse2OffsetDateTime( actJson   .getString("dt_end") ) );
                activity.setLocalName( actJson.getString( "local_name") );
                activity.setLocalGeolocation( actJson.getString("local_geolocation") );
                activity.setActivityServiceId( actJson.getLong("id") );
                activity.setEventId( event.getId() );

                // saving activity
                ComponentProvider.getServiceComponent().getActivityService().store(activity);

                if( actJson.has("attachments") )
                {
                    JSONArray attachments = actJson.getJSONArray("attachments");

                    for (int j = 0; j < attachments.length(); j++) {
                        JSONObject attJson = attachments.getJSONObject(j);
                        Attachment attachment = new Attachment();
                        attachment.setType(attJson.getInt("type"));
                        attachment.setSize(attJson.getString("size"));
                        attachment.setName(attJson.getString("name"));
                        attachment.setLocal(attJson.getString("local"));
                        attachment.setActivityId(activity.getId());

                        //saving attachment
                        ComponentProvider.getServiceComponent().getAttachmentService().store(attachment);
                    }
                }

                if( actJson.has("evaluation") )
                {
                    JSONObject evaluation = actJson.getJSONObject("evaluation");

                    Evaluation eval = new Evaluation();
                    eval.setStars( Float.parseFloat(evaluation.getString("stars")) );
                    eval.setComment(evaluation.getString("comment"));
                    eval.setEmail(evaluation.getString("email"));
                    eval.setDtStore( Util.parse2OffsetDateTime(evaluation.getString("dt_store")));
                    eval.setActivity( activity.getId());

                    //saving attachment
                    ComponentProvider.getServiceComponent().getEvaluationService().store(eval);
                }

                if( actJson.has("messages") )
                {
                    JSONArray messages = actJson.getJSONArray("messages");

                    for (int j = 0; j < messages.length(); j++)
                    {
                        JSONObject message = messages.getJSONObject(j);
                        Message me = new Message();
                        me.setActivityServiceId( activity.getActivityServiceId() );
                        me.setActivityId( activity.getId() );
                        me.setContent( message.getString("content") );
                        me.setDtStore( Util.parse2OffsetDateTime(message.getString("dt_store")));
                        me.setEmail( message.getString("email") );
                        me.setUserId( Session.getInstance(null).getLong("user") );

                        //saving message
                        ComponentProvider.getServiceComponent().getMessageService().store(me);
                    }
                }
            }

            return event;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static abstract class ActionEvent
    {
        public abstract void onEvent( Event event );
    }
}
