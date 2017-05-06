package com.gaj2l.eventtus.view.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.view.activities.AttachmentActivity;
import com.gaj2l.eventtus.view.activities.BaseActivity;
import com.gaj2l.eventtus.view.activities.LocationActivity;
import com.gaj2l.eventtus.view.activities.QuestionActivity;
import com.gaj2l.eventtus.view.activities.ToRateActivity;

/**
 * Created by lucas on 25/04/17.
 */

public class DetailActivityFragment extends Fragment
{
    private static Activity activity;

    private TextView txtName;
    private TextView txtDate;
    private TextView txtTime;
    private Button btnDetails;
    private Button btnAttachments;
    private Button btnToRate;
    private Button btnLocation;
    private Button btnSendQuestion;

    public DetailActivityFragment(){}

    public void setActivity(Activity activity){ this.activity = activity; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ((BaseActivity) getContext()).setTitle(R.string.title_details_activity);
        ((BaseActivity) getActivity()).hideFloatingActionButton();

        return inflater.inflate(R.layout.fragment_detail_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        txtName         = (TextView) view.findViewById(R.id.txtNameActivityAttachments);
        txtDate         = (TextView) view.findViewById(R.id.txtDateActivity);
        txtTime         = (TextView) view.findViewById(R.id.txtTimeActivity);
        btnDetails      = (Button) view.findViewById(R.id.btnDetailsEvents);
        btnAttachments  = (Button) view.findViewById(R.id.btnActivitiesEvent);
        btnToRate       = (Button) view.findViewById(R.id.btnContact);
        btnLocation     = (Button) view.findViewById(R.id.btnDeleteEvent);
        btnSendQuestion = (Button) view.findViewById(R.id.btnSendQuestionActivity);

        txtName.setText( activity.getName() );
        txtDate.setText( activity.getRangeDate() );
        txtTime.setText( activity.getRangeTime() );

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
    }

    private void onLocation(View v)
    {
        Intent location = new Intent(getContext(), LocationActivity.class);
        startActivity(location);
    }

    private void onToRate(View v)
    {
        Intent activity_attachment = new Intent( getContext(), ToRateActivity.class);
        activity_attachment.putExtra("activity",activity.getId());
        startActivity(activity_attachment);
    }

    private void onAttachments(View v)
    {
        Intent activity_to_rate = new Intent( getContext(), AttachmentActivity.class);
        activity_to_rate.putExtra("activity",activity.getId());
        startActivity(activity_to_rate);
    }

    private void onSendQuestion(View v)
    {
        Intent send_question = new Intent( getContext(), QuestionActivity.class);
        send_question.putExtra("activity",activity.getId());
        startActivity(send_question);
    }
}
