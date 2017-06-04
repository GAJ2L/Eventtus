package com.gaj2l.eventtus.services.web;

import com.gaj2l.eventtus.lib.WebService;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Answer;
import com.gaj2l.eventtus.models.Option;
import com.gaj2l.eventtus.models.Question;
import com.gaj2l.eventtus.models.Survey;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by artur on 6/2/17.
 */

public class SurveyWebService
{
    private static String CLASS = "SurveyService";

    public static void getSurvey( Activity activity, final ActionEvent<Survey> evt ) throws Exception
    {
        Map params = new HashMap();
        params.put( "activity_id", String.valueOf( activity.getActivityServiceId() ) );

        WebService.get( CLASS, "getSurveys",new RequestParams( params ),new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                evt.onEvent( parseSurvey( response ) );
            }
        });
    }

    public static Survey parseSurvey( JSONObject json )
    {
        Survey survey = null;

        try
        {
            JSONObject jSurvey = json.getJSONObject( "data" );

            JSONArray jQuestions = jSurvey.getJSONArray( "questions" );

            List<Question> questions = new ArrayList();

            for ( int i = 0; i < jQuestions.length(); i++ )
            {
                JSONObject jQuestion = jQuestions.getJSONObject( i );

                Question question = new Question( jQuestion.getString( "description" ) );
                question.id( jQuestion.getInt( "id" ) );

                JSONArray options = jQuestion.getJSONArray( "options" );

                for ( int j = 0; j < options.length(); j++ )
                {
                    JSONObject jOption = options.getJSONObject( j );

                    question.options( new Option( jOption.getString( "description" ),
                                                  jOption.getInt( "id" ) ) );
                }

                questions.add( question );
            }

            survey = new Survey( jSurvey.getString( "description" ), questions );
        }

        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return survey;
    }

    public static void finish(Survey survey, Activity activity, String mail, final ActionEvent<Boolean> evt )
    {
        try {
            Map params = new HashMap();

            params.put("activity_id", String.valueOf(activity.getActivityServiceId()));
            params.put("email", mail);

            JSONArray answers = new JSONArray();

            for ( Map.Entry<Integer, Answer> entry : survey.answers().entrySet() )
            {
                JSONObject answer = new JSONObject();

                answer.put( "question", entry.getKey() );
                answer.put( "option",   entry.getValue().option() );

                answers.put( answer );
            }

            params.put( "answers", answers.toString() );

            WebService.post(CLASS, "store", new RequestParams(params), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
                {
                    evt.onEvent( true );
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                }
            } );
        }

        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    public static abstract class ActionEvent<T>
    {
        public abstract void onEvent( T survey );
    }
}
