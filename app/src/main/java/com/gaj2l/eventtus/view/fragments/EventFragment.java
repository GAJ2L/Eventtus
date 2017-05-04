package com.gaj2l.eventtus.view.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.view.activities.BaseActivity;
import com.gaj2l.eventtus.view.adapters.EventAdapter;

import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment {
    private List<Event> events;

    public EventFragment() {
        // set events
        this.setUserEvents(Session.getInstance(getContext()).getLong("user"));
    }

    private void setUserEvents(long userId) {
        this.events = ComponentProvider.getServiceComponent().getEventService().getEventsByUser(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((BaseActivity) getContext()).setTitle(R.string.title_events);
        ((BaseActivity) getActivity()).showFloatingActionButton();
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.events_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new EventAdapter(this.events));
    }
}
