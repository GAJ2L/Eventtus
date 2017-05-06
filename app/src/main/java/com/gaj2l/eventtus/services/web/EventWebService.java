package com.gaj2l.eventtus.services.web;


import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.lib.WebService;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Attachment;
import com.gaj2l.eventtus.models.Event;
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
                String msg = "";
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
                        activity.setDtStart( Util.parse2OffsetDateTime( response.getString("dt_start") ) );
                        activity.setDtEnd( Util.parse2OffsetDateTime( response.getString("dt_end") ) );
                        activity.setLocalName( actJson.getString( "local_name") );
                        activity.setLocalGeolocation( actJson.getString("local_geolocation") );
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
                    }
                    msg = "Success";
                }
                catch (Exception e)
                {
                    msg = e.getMessage();
                    e.printStackTrace();
                }

                finally
                {
                    action.onEvent(msg);
                }
            }
        });
    }

    public static abstract class ActionEvent
    {
        public abstract void onEvent( String msg );
    }
}
