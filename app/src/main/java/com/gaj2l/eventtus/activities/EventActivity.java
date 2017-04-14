package com.gaj2l.eventtus.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.activities.adapters.EventAdapter;
import com.gaj2l.eventtus.models.Event;
import java.util.ArrayList;
import java.util.List;

public class EventActivity extends BaseActivity
{
    private List<Event>  events;
    private EventAdapter adapter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        events = new ArrayList();
        Event e = new Event();
        e.setName("Name 1");
        e.setDescription("desc 1");
        events.add(e);events.add(e);events.add(e);events.add(e);events.add(e);events.add(e);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.events_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new EventAdapter(this, events);
        recyclerView.setAdapter(adapter);
    }
}
