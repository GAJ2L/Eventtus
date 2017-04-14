package com.gaj2l.eventtus.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.activities.adapters.ActivityAdapter;
import com.gaj2l.eventtus.models.Activity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 13/04/17.
 */

public class ActivityActivity extends BaseActivity
{
    private List<Activity> activitis;
    private ActivityAdapter   adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activitis = new ArrayList();
        Activity a = new Activity();
        a.setName("Name 1");
        a.setLocalName("Predio 1");
        activitis.add(a);activitis.add(a);activitis.add(a);activitis.add(a);activitis.add(a);activitis.add(a);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.events_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ActivityAdapter(this,activitis);
        recyclerView.setAdapter(adapter);
    }
}


