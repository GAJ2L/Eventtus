package com.gaj2l.eventtus.activities;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.activities.adapters.EventAdapter;
import com.gaj2l.eventtus.models.Event;
import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment
{
    private List<Event> events;

    public EventFragment()
    {
        events = new ArrayList();
        Event e = new Event();
        e.setName("Crire XP");
        e.setDescription("15-03-2017 à 30-03-2017");
        events.add(e);events.add(e);events.add(e);events.add(e);events.add(e);events.add(e);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ((BaseActivity)getContext()).setTitle(R.string.title_events);

        View view = inflater.inflate(R.layout.fragment_event, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.events_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new EventAdapter(events));

        return view;
    }
}
