package com.gaj2l.eventtus.services.web;


import android.os.Handler;
import android.os.HandlerThread;

import cz.msebera.android.httpclient.Header;
import com.gaj2l.eventtus.lib.WebService;
import com.gaj2l.eventtus.models.Event;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucas tomasi on 30/04/17.
 */

public class EventWebService
{
    private static String PATH = "evento.php";

    public static void getEvent(String email, String hash, final ActionEvent action )
    {
        Map params = new HashMap();

        params.put( "email" , email );
        params.put( "hash"  , hash  );

        WebService.get(EventWebService.PATH,new RequestParams(params),new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                Event event = new Event();
                try
                {
                    event.setName( response.getString("title") );
                    action.onEvent(event);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        });
    }

    public static abstract class ActionEvent
    {
        public abstract void onEvent( Event e );
    }
}
