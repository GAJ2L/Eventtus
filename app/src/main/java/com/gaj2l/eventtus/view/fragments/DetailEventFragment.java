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
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.view.activities.BaseActivity;

/**
 * Created by lucas on 25/04/17.
 */

public class DetailEventFragment extends Fragment
{
    private static Event event;

    private TextView txtName;
    private TextView txtDate;
    private TextView txtDescription;
    private Button   btnActivities;
    private Button   btnContact;
    private Button   btnDetails;
    private Button   btnDelete;

    public DetailEventFragment(){}

    public void setEvent(Event event){ this.event= event; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ((BaseActivity) getContext()).setTitle(R.string.title_details_activity);
        ((BaseActivity) getActivity()).hideFloatingActionButton();

        return inflater.inflate(R.layout.fragment_detail_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        txtName        = (TextView) view.findViewById(R.id.txtNameEvent);
        txtDate        = (TextView) view.findViewById(R.id.txtDateEvent);
        txtDescription = (TextView) view.findViewById(R.id.txtDescriptionEvent);
        btnActivities  = (Button) view.findViewById(R.id.btnActivitiesEvent);
        btnContact     = (Button) view.findViewById(R.id.btnContact);
        btnDetails     = (Button) view.findViewById(R.id.btnDetailsEvents);
        btnDelete      = (Button) view.findViewById(R.id.btnDeleteEvent);

        txtName.setText( event.getName() );
        txtDate.setText( event.getRangeDate() );
        txtDescription.setText( event.getDescription() );

        btnActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActivities(v);
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContact(v);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete(v);
            }
        });

        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetails(v);
            }
        });
    }


    public void onActivities(View v)
    {
        ((BaseActivity) v.getContext()).getFragmentManager().beginTransaction().replace(R.id.fragment, new ActivityFragment()).addToBackStack("ActivityFragment").commit();
    }

    public void onDelete(View v) {}
    public void onContact(View v) {}
    public void onDetails(View v) {}
}
