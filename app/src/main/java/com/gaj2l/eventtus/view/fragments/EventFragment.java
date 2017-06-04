package com.gaj2l.eventtus.view.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Internet;
import com.gaj2l.eventtus.lib.Preload;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.services.web.EventWebService;
import com.gaj2l.eventtus.view.activities.BaseActivity;
import com.gaj2l.eventtus.view.adapters.EventAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class EventFragment extends Fragment {
    private List<Event> events;
    private RecyclerView recyclerView;

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
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        recyclerView = (RecyclerView) view.findViewById(R.id.events_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        final FloatingActionButton actionA = (FloatingActionButton) view.findViewById(R.id.action_a);
        actionA.setIcon(R.drawable.camera);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((BaseActivity) getContext()).hasPermission()) {
                    ((BaseActivity) getContext()).registerEvent();
                } else {
                    ((BaseActivity) getContext()).requestPermission();
                }
            }
        });

        final FloatingActionButton actionB = (FloatingActionButton) view.findViewById(R.id.action_b);
        actionB.setIcon(R.drawable.pencil);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(view.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());

                alertDialog.setView(input);
                alertDialog.setTitle(R.string.message_code);
                alertDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getEventServer(input.getText().toString());
                    }
                });

                alertDialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });

                alertDialog.show();
            }
        });

        recyclerView.setAdapter(new EventAdapter(this.events));
    }

    private void redirect() {
        getFragmentManager().popBackStack();
        getFragmentManager().beginTransaction().replace(R.id.fragment, new EventFragment()).addToBackStack("EventFragment").commit();
    }

    private void getEventServer(String chave) {

        if (Internet.isConnect(recyclerView.getContext())) {
            final Preload p = new Preload(recyclerView.getContext());
            p.show();
            EventWebService.getEvent(Session.getInstance(recyclerView.getContext()).getString("email"), chave, new EventWebService.ActionEvent() {
                @Override
                public void onEvent(Event event) {
                    p.dismiss();
                    redirect();
                    int msg = (event != null) ? R.string.add_event_success : R.string.add_event_error;
                    Snackbar.make(recyclerView, msg, Snackbar.LENGTH_LONG).show();
                }
            });
        } else {
            Snackbar.make(recyclerView, R.string.err_conection, Snackbar.LENGTH_LONG).show();
        }
    }
}
