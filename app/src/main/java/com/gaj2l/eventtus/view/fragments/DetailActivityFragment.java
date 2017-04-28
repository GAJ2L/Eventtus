package com.gaj2l.eventtus.view.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.view.activities.BaseActivity;

/**
 * Created by lucas on 25/04/17.
 */

public class DetailActivityFragment extends Fragment
{
    private Activity activity;
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
        txtName         = (TextView) view.findViewById(R.id.txtNameActivity);
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


    }
}
