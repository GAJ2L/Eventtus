package com.gaj2l.eventtus.services.web;

import android.support.design.widget.Snackbar;

import com.gaj2l.eventtus.lib.WebService;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by lucas on 01/05/17.
 */

public class EmailWebService
{
    private static String CLASS = "EmailService";

    /**
     *
     * @param body      corpo do email (HTML)
     * @param subject   assunto do email
     * @param from_name nome  de quem envia
     * @param from_mail email de quem envia
     * @param to_name   nome  de quem recebe
     * @param to_mail   email de quem recebe
     * @param action    ação apos o retorno do metodo
     */
    public static void send( String body, String subject, String from_name, String from_mail, String to_name, String to_mail, final ActionEvent action )
    {
        Map params = new HashMap();
        params.put( "body"      , body      );
        params.put( "subject"   , subject   );
        params.put( "from_mail" , from_mail );
        params.put( "to_mail"   , to_mail   );
        params.put( "to_name"   , to_name   );
        params.put( "from_name" , from_name );

        WebService.post(EmailWebService.CLASS,"send",new RequestParams(params),new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                try
                {
                    action.afterSend( response.getString("status") , response.getString("message") );
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.err.println("CODE: " + statusCode + " Message: " + throwable.getMessage() );
            }
        });
    }

    public static abstract class ActionEvent
    {
        public abstract void afterSend( String status, String message );
    }
}
