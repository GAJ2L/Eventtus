package com.gaj2l.eventtus.view.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.busines.socket.ClientSocket;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Internet;
import com.gaj2l.eventtus.lib.Message;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Attachment;
import com.gaj2l.eventtus.models.Evaluation;
import com.gaj2l.eventtus.models.Event;
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
    private static String color;

    private TextView txtName;
    private TextView txtDateIni;
    private TextView txtDateFin;
    private Button btnDetails;
    private Button btnAttachments;
    private Button btnToRate;
    private Button btnLocation;
    private Button btnSendQuestion;
    private Button btnSurvey;

    public DetailActivityFragment(){}

    public void setActivity(Activity activity){ this.activity = activity; }

    public void setColor(String color){ this.color = color; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ((BaseActivity) getContext()).setTitle(R.string.title_details_activity);

        if( color != null )
            ((BaseActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));

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

        txtName.setText( activity.getName() );
        txtDateIni.setText(Util.getAllDateFomatted(activity.getDtStart()));
        txtDateFin.setText(Util.getAllDateFomatted(activity.getDtEnd()));

        Event event = ComponentProvider.getServiceComponent().getEventService().get( activity.getEventId() );

        if( event.getCor() != null )
        {
            int color = Color.parseColor( event.getCor() );
            btnDetails.setBackgroundTintList( ColorStateList.valueOf(color) );
            btnAttachments.setBackgroundTintList( ColorStateList.valueOf(color) );
            btnToRate.setBackgroundTintList( ColorStateList.valueOf(color) );
            btnLocation.setBackgroundTintList( ColorStateList.valueOf(color) );
            btnSendQuestion.setBackgroundTintList( ColorStateList.valueOf(color) );
            btnSurvey.setBackgroundTintList( ColorStateList.valueOf(color) );
        }


        // clica somente se a atividade começou e não foi avaliada
        if(  hasToRate() || !isStarted() )
        {
            btnToRate.setEnabled( false );
            btnToRate.setClickable( false );
            btnToRate.setBackgroundTintList( ColorStateList.valueOf(Color.rgb(170,170,170)) );
        }

        if( !hasAttachments() )
        {
            btnAttachments.setEnabled( false );
            btnAttachments.setClickable( false );
            btnAttachments.setBackgroundTintList( ColorStateList.valueOf(Color.rgb(170,170,170)) );
        }

        if( !isStarted() || isFinished() )
        {
            btnSendQuestion.setEnabled( false );
            btnSendQuestion.setClickable( false );
            btnSendQuestion.setBackgroundTintList( ColorStateList.valueOf(Color.rgb(170,170,170)) );

            btnSurvey.setEnabled( false );
            btnSurvey.setClickable( false );
            btnSurvey.setBackgroundTintList( ColorStateList.valueOf(Color.rgb(170,170,170)) );
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

    private boolean hasAttachments()
    {
        List<Attachment> l = ComponentProvider.getServiceComponent().getAttachmentService().getAttachmentsByActivity(activity.getId());

        return  l!=null && !l.isEmpty();
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

    private void onToRate(View v)
    {
        if (!hasToRate() && isStarted())
        {
            Intent activity_attachment = new Intent(getContext(), ToRateActivity.class);
            activity_attachment.putExtra("activity", activity.getId());
            startActivity(activity_attachment);
        }
        else
        {
            Message.show(v.getContext(),R.string.disable_button_evaluate);
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
        if( Internet.isConnect(v.getContext()) )
        {
            Intent survey = new Intent(getContext(), SurveyActivty.class);
            survey.putExtra("activity", activity.getId());
            startActivity(survey);
        }
        else
        {
            Message.show( v.getContext(), R.string.err_conection);
        }
    }

    private void onSendQuestion(View v)
    {
        if( ClientSocket.isRunning() && isStarted() && !isFinished() )
        {
            Intent send_question = new Intent(getContext(), QuestionActivity.class);
            send_question.putExtra("activity", activity.getId());
            startActivity(send_question);
        }
        else
        {
            Message.show(v.getContext(),R.string.err_send_question_btn);
        }
    }
}