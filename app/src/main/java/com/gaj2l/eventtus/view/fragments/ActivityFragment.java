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
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.view.activities.BaseActivity;
import com.gaj2l.eventtus.view.adapters.ActivityAdapter;

import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.ArrayList;
import java.util.List;

public class ActivityFragment extends Fragment {
    private List<Activity> activities;

    public ActivityFragment() {
        // FIXME POR QUE NAO TEM NENHUM DADO NO BD
        activities = new ArrayList();
        Activity a = new Activity();
        a.setName("Palestra um");
        a.setId(1);
        a.setDtStart(OffsetDateTime.of(2017, 01, 06, 17, 10, 0, 0, ZoneOffset.UTC));
        a.setDtEnd(OffsetDateTime.of(2017, 01, 06, 17, 50, 0, 0, ZoneOffset.UTC));
        a.setLocalName("Prédio 12 - Sala 201");
        activities.add(a);
        Activity b = new Activity();
        b.setName("Palestra DOIS");
        b.setId(2);
        b.setDtStart(OffsetDateTime.of(2017, 01, 06, 8, 00, 0, 0, ZoneOffset.UTC));
        b.setDtEnd(OffsetDateTime.of(2017, 01, 07, 18, 30, 0, 0, ZoneOffset.UTC));
        b.setLocalName("Prédio 15 - Sala 301");
        activities.add(b);
        //
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((BaseActivity) getActivity()).setTitle(R.string.title_activities);
        ((BaseActivity) getActivity()).hideFloatingActionButton();
        return inflater.inflate(R.layout.fragment_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.activities_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new ActivityAdapter(activities));

    }
}
