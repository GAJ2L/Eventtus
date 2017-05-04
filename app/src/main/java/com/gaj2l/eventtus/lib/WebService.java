package com.gaj2l.eventtus.lib;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by lucas tomasi on 30/04/17.
 */

public class WebService
{
    private static final String BASE_URL = "http://jacksonmajolo.ml/Eventtus/WebService.php";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String clazz, String method, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.get(WebService.getUrl(clazz,method), params, responseHandler);
    }

    public static void post(String clazz, String method, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.post(WebService.getUrl(clazz,method), params, responseHandler);
    }

    public static String getUrl(String clazz, String method)
    {
        return WebService.BASE_URL+"?class="+clazz+"&method="+method;
    }
}