package com.gaj2l.eventtus.view.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.busines.socket.ClientSocket;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Evaluation;
import com.gaj2l.eventtus.view.activities.ActivityDetailsActivity;
import com.gaj2l.eventtus.view.activities.AttachmentActivity;
import com.gaj2l.eventtus.view.activities.BaseActivity;
import com.gaj2l.eventtus.view.activities.LocationActivity;
import com.gaj2l.eventtus.view.activities.QuestionActivity;
import com.gaj2l.eventtus.view.activities.SurveyActivty;
import com.gaj2l.eventtus.view.activities.ToRateActivity;

import org.threeten.bp.OffsetDateTime;

import java.util.List;

/**
 * Created by lucas on 25/04/17.
 */

public class DetailActivityFragment extends Fragment
{
    private static Activity activity;

    private TextView txtName;
    private TextView txtDateIni;
    private TextView txtDateFin;
    private Button btnDetails;
    private Button btnAttachments;
    private Button btnToRate;
    private Button btnLocation;
    private Button btnSendQuestion;
    private Button btnSurvey;
    private CardView view;

    public DetailActivityFragment(){}

    public void setActivity(Activity activity){ this.activity = activity; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ((BaseActivity) getContext()).setTitle(R.string.title_details_activity);

        return inflater.inflate(R.layout.fragment_detail_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        txtName         = (TextView) view.findViewById(R.id.txtNameActivityAttachments);
        txtDateIni      = (TextView) view.findViewById(R.id.txtDateIniActivity);
        txtDateFin      = (TextView) view.findViewById(R.id.txtDateFinActivity);
        btnDetails      = (Button) view.findViewById(R.id.btnDetailsEvents);
        btnAttachments  = (Button) view.findViewById(R.id.btnActivitiesEvent);
        btnToRate       = (Button) view.findViewById(R.id.btnContact);
        btnLocation     = (Button) view.findViewById(R.id.btnDeleteEvent);
        btnSendQuestion = (Button) view.findViewById(R.id.btnSendQuestionActivity);
        btnSurvey       = (Button) view.findViewById( R.id.btnShowSurvey );
        view            = (CardView) view.findViewById(R.id.card_view_details_activity);

        String val = (activity.getName().length() > 27)? activity.getName().substring(0,27) + "..." : activity.getName();
        txtName.setText( val );

        txtDateIni.setText(Util.getAllDateFomatted(activity.getDtStart()));
        txtDateFin.setText(Util.getAllDateFomatted(activity.getDtEnd()));

        // clica somente se a atividade começou e não foi avaliada
        if(  hasToRate() || !isStarted() )
        {
            btnToRate.setEnabled( false );
            btnToRate.setClickable( false );
            btnToRate.setBackgroundColor( Color.rgb(170,170,170) );
        }

        if( !isStarted() || isFinished() )
        {
            btnSendQuestion.setEnabled( false );
            btnSendQuestion.setClickable( false );
            btnSendQuestion.setBackgroundColor( Color.rgb(170,170,170) );

            btnSurvey.setEnabled( false );
            btnSurvey.setClickable( false );
            btnSurvey.setBackgroundColor( Color.rgb(170,170,170) );
        }

        btnToRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToRate(v);
            }
        });
        btnAttachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAttachments(v);
            }
        });
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLocation(v);
            }
        });
        btnSendQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendQuestion(v);
            }
        });
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetails(v);
            }
        });
        btnSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSurvey(v);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetails(v);
            }
        });
    }

    private boolean hasToRate()
    {
        List<Evaluation> l = ComponentProvider.getServiceComponent().getEvaluationService().getEvaluationsByActivity(activity.getId(), Session.getInstance(getContext()).getString("email"));

        return l != null && !l.isEmpty();
    }

    private boolean isStarted()
    {
        return OffsetDateTime.now().isAfter(activity.getDtStart());
    }

    private boolean isFinished()
    {
        return OffsetDateTime.now().isAfter(activity.getDtEnd().plusHours(2));
    }


    private void onLocation(View v)
    {
        Intent location = new Intent(getContext(), LocationActivity.class);
        location.putExtra("activity", activity.getId());
        startActivity(location);
    }

    private void onToRate(View v) {
        if (!hasToRate() && isStarted())
        {
            Intent activity_attachment = new Intent(getContext(), ToRateActivity.class);
            activity_attachment.putExtra("activity", activity.getId());
            startActivity(activity_attachment);
        }
        else
        {
            Snackbar.make(v,R.string.disable_button_evaluate,Snackbar.LENGTH_LONG).show();
        }
    }

    private void onAttachments(View v)
    {
        Intent activity_to_rate = new Intent( getContext(), AttachmentActivity.class);
        activity_to_rate.putExtra("activity",activity.getId());
        startActivity(activity_to_rate);
    }

    private void onDetails(View v)
    {
        Intent intent = new Intent( getContext(), ActivityDetailsActivity.class);
        intent.putExtra("activity",activity.getId());
        startActivity(intent);
    }

    private void onSurvey(View v)
    {
        Intent survey = new Intent(getContext(), SurveyActivty.class);
        survey.putExtra("activity", activity.getId());
        startActivity(survey);
    }

    private void onSendQuestion(View v)
    {
        if( ClientSocket.isRunning() && isStarted() && !isFinished() ) {
            Intent send_question = new Intent(getContext(), QuestionActivity.class);
            send_question.putExtra("activity", activity.getId());
            startActivity(send_question);
        } else {
            Snackbar.make(v,R.string.err_send_question_btn , Snackbar.LENGTH_LONG).show();
        }
    }
}
