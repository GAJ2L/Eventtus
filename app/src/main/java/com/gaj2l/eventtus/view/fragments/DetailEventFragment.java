package com.gaj2l.eventtus.view.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.view.activities.BaseActivity;
import com.gaj2l.eventtus.view.adapters.ActivityAdapter;

/**
 * Created by lucas on 25/04/17.
 */

public class DetailEventFragment extends Fragment
{
    private Activity activity;
    private TextView txtName;
    private TextView txtDate;
    private TextView txtTime;

    public DetailEventFragment(){}

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
        txtName = (TextView) view.findViewById(R.id.txtNameActivity);
        txtDate = (TextView) view.findViewById(R.id.txtDateActivity);
        txtTime = (TextView) view.findViewById(R.id.txtTimeActivity);

        txtName.setText( activity.getName() );
        txtDate.setText( activity.getDtStart() + " à " + activity.getDtEnd() );
        txtTime.setText( activity.getLocalName() );
    }
}
