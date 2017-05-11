package com.gaj2l.eventtus.view.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Message;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.view.activities.BaseActivity;
import com.gaj2l.eventtus.view.activities.ContactActivity;

import java.util.List;

/**
 * Created by lucas on 25/04/17.
 */

public class DetailEventFragment extends Fragment {
    private static Event event;

    private TextView txtName;
    private TextView txtDate;
    private Button btnActivities;
    private Button btnContact;
    private Button btnDetails;
    private Button btnDelete;

    public DetailEventFragment() {
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((BaseActivity) getContext()).setTitle(R.string.title_details_event);
        ((BaseActivity) getActivity()).hideFloatingActionButton();

        return inflater.inflate(R.layout.fragment_detail_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        txtName = (TextView) view.findViewById(R.id.txtNameEvent);
        txtDate = (TextView) view.findViewById(R.id.txtDateEvent);
        btnActivities = (Button) view.findViewById(R.id.btnActivitiesEvent);
        btnContact = (Button) view.findViewById(R.id.btnContact);
        btnDetails = (Button) view.findViewById(R.id.btnDetailsEvents);
        btnDelete = (Button) view.findViewById(R.id.btnDeleteEvent);

        txtName.setText(event.getName());
        txtDate.setText(event.getRangeDate());

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


    public void onActivities(View v) {
        ActivityFragment activityFragment = new ActivityFragment();
        Session.getInstance(getContext()).put("event_id",this.event.getId());
        ((BaseActivity) v.getContext()).getFragmentManager().beginTransaction().replace(R.id.fragment, activityFragment).addToBackStack("ActivityFragment").commit();
    }

    public void onContact(View v) {
        Intent contact = new Intent(v.getContext(), ContactActivity.class);
        contact.putExtra("to", event.getContactMail());
        startActivity(contact);
    }

    public void onDelete(final View v)
    {
        String msg   = getResources().getString(R.string.question_delete_msg);
        String title = getResources().getString(R.string.app_name);
        Message.confirm(getContext(),title,msg, new Message.Action() {
            @Override
            public void onPositiveButton() {
                delete(v);
            }
            @Override
            public void onNegativeButton() {}
        });
    }

    public void delete(View v)
    {
        try
        {
            ComponentProvider.getServiceComponent().getEventService().clearEvent(event);
            Util.clearBackStake(((BaseActivity) v.getContext()).getFragmentManager());
            ((BaseActivity) v.getContext()).getFragmentManager().beginTransaction().replace(R.id.fragment, new EventFragment()).addToBackStack("EventFragment").commit();
        }
        catch( Exception e )
        {
            e.printStackTrace();
            Snackbar.make(v,R.string.error_delete_event,Snackbar.LENGTH_LONG).show();
        }
    }

    public void onDetails(View v) {
    }
}
