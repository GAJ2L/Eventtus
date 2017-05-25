package com.gaj2l.eventtus.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Event;

public class ActivityDetailsActivity extends AppCompatActivity
{
    private Activity activity;

    private TextView txtName;
    private TextView txtDt;
    private TextView txtLocation;
    private TextView txtEvt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_details_activity);

        initComponents();

        load();
    }

    private void load() {
        activity = ComponentProvider.getServiceComponent().getActivityService().get(getIntent().getExtras().getLong("activity"));
        Event event = ComponentProvider.getServiceComponent().getEventService().get( activity.getEventId() );

        txtName.setText(activity.getName());
        txtLocation.setText(activity.getLocalName());
        txtEvt.setText( event.getName() );
        txtDt.setText( Util.getAllDateFomatted( activity.getDtStart() ) + " - " + Util.getDateFomatted( activity.getDtEnd() ) );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initComponents()
    {
        txtName     = (TextView) findViewById( R.id.textActivityName);
        txtDt       = (TextView) findViewById( R.id.textActivityDate);
        txtLocation = (TextView) findViewById( R.id.textActivityLocation);
        txtEvt      = (TextView) findViewById( R.id.textActivityEvent);
    }
}
