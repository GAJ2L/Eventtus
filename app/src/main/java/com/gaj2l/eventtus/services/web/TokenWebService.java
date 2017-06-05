package com.gaj2l.eventtus.services.web;

import com.gaj2l.eventtus.lib.WebService;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by lucas on 27/05/17.
 */

public class TokenWebService
{
    private static String CLASS = "TokenService";

    public static void save(String email)
    {
        Map params = new HashMap();
        params.put("email",email);
        params.put("token", FirebaseInstanceId.getInstance().getToken() );

        WebService.post(CLASS, "save", new RequestParams(params), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {}

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
        });
    }
}
