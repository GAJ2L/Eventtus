package com.gaj2l.eventtus.view.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Internet;
import com.gaj2l.eventtus.lib.Message;
import com.gaj2l.eventtus.lib.Preload;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.services.web.EventWebService;
import com.gaj2l.eventtus.view.activities.BaseActivity;
import com.gaj2l.eventtus.view.activities.ContactActivity;
import com.gaj2l.eventtus.view.activities.EventDetailsActivity;
import com.gaj2l.eventtus.view.controllers.ViewController;

/**
 * Created by lucas on 25/04/17.
 */

public class DetailEventFragment extends Fragment
{
    private static Event event;

    private TextView txtName;
    private TextView txtStateEvent;
    private Button btnActivities;
    private Button btnContact;
    private Button btnDetails;
    private Button btnDelete;
    private Button btnRefresh;
    private CardView cardView;

    public DetailEventFragment() {}

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ((BaseActivity) getContext()).setTitle(R.string.title_details_event);
        return inflater.inflate(R.layout.fragment_detail_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        txtName = (TextView) view.findViewById(R.id.txtNameEvent);
        txtStateEvent = (TextView) view.findViewById(R.id.txtStateEvent);
        btnActivities = (Button) view.findViewById(R.id.btnActivitiesEvent);
        btnContact = (Button) view.findViewById(R.id.btnContact);
        btnDetails = (Button) view.findViewById(R.id.btnDetailsEvents);
        btnDelete = (Button) view.findViewById(R.id.btnDeleteEvent);
        btnRefresh = (Button) view.findViewById(R.id.btnRefreshEvent);
        cardView  = (CardView) view.findViewById( R.id.card_view_details_activity);

        txtName.setText(event.getName());

        txtStateEvent.setText(getResources().getString(Event.STATE_TITLE[event.getState()]) );
        txtStateEvent.setCompoundDrawablesRelativeWithIntrinsicBounds( Event.STATE_DRAWABLES[event.getState()],0,0,0 );
        txtStateEvent.setTextColor(getResources().getColor(Event.STATE_COLORS[event.getState()],null) );

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
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh(v);
            }
        });
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetails(v);
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetails(v);
            }
        });
    }

    public void onRefresh(final View v)
    {
        if( Internet.isConnect(v.getContext()) )
        {
            final Preload p = new Preload(v.getContext());

            p.show();

            final String email = Session.getInstance(getContext()).getString("email");

            EventWebService.refreshEvent( event.getEventServiceId(), email, new EventWebService.ActionEvent()
            {
                @Override
                public void onEvent(Event event)
                {
                    p.dismiss();
                    int msg = ( event != null ) ? R.string.update_event_success : R.string.add_event_error;
                    Message.show(v.getContext() , msg);
                    getFragmentManager().popBackStack();
                    DetailEventFragment fragment = new DetailEventFragment();
                    fragment.setEvent(event);
                    ((BaseActivity) v.getContext()).getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).addToBackStack("DetailEventFragment").commit();

                }
            });
        }
        else
        {
            Message.show(v.getContext(), R.string.err_conection);
        }
    }

    public void onActivities(View v)
    {
        Session.getInstance(getContext()).put("event_id",this.event.getId());
        ActivityFragment activityFragment = new ActivityFragment();
        
        ((BaseActivity) v.getContext()).getFragmentManager().beginTransaction().replace(R.id.fragment, activityFragment).addToBackStack("ActivityFragment").commit();
    }

    public void onContact(View v)
    {
        Intent contact = new Intent(v.getContext(), ContactActivity.class);
        contact.putExtra("to", event.getContactMail());
        startActivity(contact);
    }

    public void onDelete(final View v)
    {
        String msg   = getResources().getString(R.string.question_delete_msg);
        String title = getResources().getString(R.string.app_name);
        Message.confirm(getContext(),title,msg, new Message.Action()
        {
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

            ViewController.redirectEvents( ((BaseActivity) v.getContext()).getFragmentManager(), v.getContext() );
        }
        catch( Exception e )
        {
            e.printStackTrace();
            Message.show(v.getContext(),R.string.error_delete_event);
        }
    }

    public void onDetails(View v)
    {
        Intent dtl= new Intent(v.getContext(), EventDetailsActivity.class);
        dtl.putExtra("menu", event.getId() );
        startActivity(dtl);
    }
}