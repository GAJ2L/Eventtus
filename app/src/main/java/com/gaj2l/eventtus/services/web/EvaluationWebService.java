package com.gaj2l.eventtus.services.web;

import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.lib.WebService;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Evaluation;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by lucas on 06/05/17.
 */

public class EvaluationWebService
{
    private static String CLASS = "EvaluationService";

    public static void sendServer(final Evaluation evaluation, final Action response )
    {
        Map params = new HashMap();
        Activity activity = ComponentProvider.getServiceComponent().getActivityService().get(evaluation.getActivity());
        params.put("email"    , evaluation.getEmail());
        params.put("activity" , String.valueOf(activity.getActivityServiceId()));
        params.put("comment"  , evaluation.getComment());
        params.put("star"     , String.valueOf(evaluation.getStars()));
        params.put("dt_store" , Util.getAllDateFomatted(evaluation.getDtStore()));

        WebService.post(EvaluationWebService.CLASS, "store", new RequestParams(params), new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                response.onEvaluate("success");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                response.onEvaluate("error");
            }
        });
    }

    public static void getMedia(long activity_id, final AbstractAction action)
    {
        Map params = new HashMap();
        params.put("activity_id" , String.valueOf(activity_id) );

        WebService.get(EvaluationWebService.CLASS, "getMedia", new RequestParams(params),new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                try
                {
                    if( response.getString("status").equalsIgnoreCase("success") )
                    {
                        JSONObject data = response.getJSONObject("data");

                        int count    = data.getInt("count");
                        String media = data.getString("avg");
                        Double avg = ( media.equalsIgnoreCase("null"))? 0 : Double.parseDouble(media);
                        action.onEvaluate(avg.floatValue(),count);
                    }
                    else
                    {
                        action.onEvaluate(0,0);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                action.onEvaluate(0,0);
            }
        });
    }

    public static abstract class Action
    {
        public abstract void onEvaluate(String status);
    }

    public static abstract class AbstractAction
    {
        public abstract void onEvaluate(float stars, int count);
    }
}
