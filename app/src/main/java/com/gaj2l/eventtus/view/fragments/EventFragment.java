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
        events = new ArrayList();
        Event e = new Event();
        e.setName("Eventtus");
        e.setDtStart( OffsetDateTime.of(2017,01,01,8,10,0,0, ZoneOffset.UTC) );
        e.setDtEnd( OffsetDateTime.of(2017,01,16,17,50,0,0, ZoneOffset.UTC) );
        e.setDescription("Nunca é tarde para inovar");
        events.add(e);
        events.add(e);
        events.add(e);
        events.add(e);
        events.add(e);
        events.add(e);
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

        recyclerView.setAdapter(new EventAdapter(events));
    }
}
