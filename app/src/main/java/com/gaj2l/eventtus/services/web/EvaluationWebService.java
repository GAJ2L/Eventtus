package com.gaj2l.eventtus.services.web;

import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.lib.WebService;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Evaluation;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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

    public static abstract class Action
    {
        public abstract void onEvaluate(String status);
    }
}
