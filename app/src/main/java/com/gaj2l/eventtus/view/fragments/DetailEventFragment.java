package com.gaj2l.eventtus.view.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Internet;
import com.gaj2l.eventtus.lib.Message;
import com.gaj2l.eventtus.lib.Preload;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.services.web.EventWebService;
import com.gaj2l.eventtus.view.activities.BaseActivity;
import com.gaj2l.eventtus.view.activities.ContactActivity;
import com.gaj2l.eventtus.view.activities.EventDetailsActivity;
import com.gaj2l.eventtus.view.adapters.ActivityAdapter;
import com.gaj2l.eventtus.view.controllers.ViewController;

import java.util.List;

/**
 * Created by lucas on 25/04/17.
 */

public class DetailEventFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    private static Event event;
    private static List<Activity> activities;
    private TextView txtStateEvent;
    private CardView cardView;
    private SwipeRefreshLayout swipeRefresh;

    public DetailEventFragment() {}

    public void setEvent(Event event)
    {
        //refresh event
        this.event = ComponentProvider.getServiceComponent().getEventService().getEventByServiceId( event.getEventServiceId() );

        this.activities = ComponentProvider.getServiceComponent().getActivityService().getActivitiesByEvent( this.event.getId() );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        setHasOptionsMenu(true);

        if( event != null ) {
            getActivity().setTitle(  event.getName() );
        }
        else {
            getActivity().setTitle( R.string.details_event );
        }

        return inflater.inflate(R.layout.fragment_detail_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        txtStateEvent = (TextView) view.findViewById(R.id.txtStateEvent);
        cardView  = (CardView) view.findViewById( R.id.card_view_details_activity);
        swipeRefresh = SwipeRefreshLayout.class.cast( view.findViewById( R.id.swiperefresh ) );

        txtStateEvent.setText(getResources().getString(Event.STATE_TITLE[event.getState()]) );
        txtStateEvent.setCompoundDrawablesRelativeWithIntrinsicBounds( Event.STATE_DRAWABLES[event.getState()],0,0,0 );
        txtStateEvent.setTextColor(getResources().getColor(Event.STATE_COLORS[event.getState()], null) );

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetails();
            }
        });


        Drawable drawable = new BitmapDrawable( getResources(),Util.base642bitmap( event.getBanner() ) );
        cardView.setBackground( drawable );

        //swipe refresh
        swipeRefresh.setOnRefreshListener( this );

        //Activities
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.activities_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ActivityAdapter(activities));
    }

    @Override
    public void onRefresh()
    {
        if( Internet.isConnect( getActivity() ) )
        {
            final Preload p = new Preload(getActivity());

            if ( ! swipeRefresh.isRefreshing() ) {
                p.show();
            }

            final String email = Session.getInstance( getActivity().getApplicationContext() ).getString("email");

            EventWebService.refreshEvent( event.getEventServiceId(), email, new EventWebService.ActionEvent()
            {
                @Override
                public void onEvent(Event event)
                {
                    p.dismiss();
                    int msg = ( event != null ) ? R.string.update_event_success : R.string.add_event_error;
                    Message.show( getActivity() , msg);
                    getFragmentManager().popBackStack();
                    DetailEventFragment fragment = new DetailEventFragment();
                    fragment.setEvent(event);
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).addToBackStack("DetailEventFragment").commit();

                }
            });
        }

        else
        {
            Message.show( getActivity(), R.string.err_conection);
        }
    }

    public void onContact()
    {
        Intent contact = new Intent(getActivity(), ContactActivity.class);
        contact.putExtra("to", event.getContactMail());
        startActivity(contact);
    }

    public void onDelete()
    {
        String msg   = getResources().getString(R.string.question_delete_msg);
        String title = getResources().getString(R.string.app_name);

        final android.app.Activity c = getActivity();

        Message.confirm( getActivity(),title,msg, new Message.Action()
        {
            @Override
            public void onPositiveButton() {
                delete( c );
            }
            @Override
            public void onNegativeButton() {}
        });
    }

    public void delete( android.app.Activity  c )
    {
        try
        {
            ComponentProvider.getServiceComponent().getEventService().clearEvent(event);

            Util.clearBackStake( c.getFragmentManager() );

            ViewController.redirectEvents( c.getFragmentManager(), c );
        }

        catch( Exception e )
        {
            e.printStackTrace();
            Message.show(getActivity(), R.string.error_delete_event);
        }
    }

    public void onDetails()
    {
        Intent dtl= new Intent( getActivity().getBaseContext(), EventDetailsActivity.class);
        dtl.putExtra("menu", event.getId() );
        startActivity(dtl);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        try
        {
            switch (item.getItemId()) {
                case R.id.contactEventAction:
                    onContact();
                    return true;
                case R.id.deleteEventAction:
                    onDelete();
                    return true;
                case R.id.detailsEventAction:
                    onDetails();
                    return true;
                case R.id.refreshEventAction:
                    onRefresh();
                    return true;
            }
        }

        catch ( Exception e )
        {
            Message.error( getActivity(), R.string.error );
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu ( Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.event_menu, menu);
    }

}