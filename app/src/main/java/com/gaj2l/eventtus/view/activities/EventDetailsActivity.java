package com.gaj2l.eventtus.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.models.Event;

public class EventDetailsActivity extends AppCompatActivity
{
    private Event event;

    private TextView txtName;
    private TextView txtInfo;
    private TextView txtContact;
    private TextView txtDt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_details_event);

        initComponents();

        load();
    }

    private void load() {
        event = ComponentProvider.getServiceComponent().getEventService().get(getIntent().getExtras().getLong("event"));

        txtName.setText(event.getName());
        txtInfo.setText(event.getDescription());

        txtContact.setText( event.getContactName() + "\n\n" +
                            event.getContactMail() + "\n\n" +
                            event.getContactPhone() );

        txtDt.setText( Util.getDateFomatted( event.getDtStart() ) + " - " + Util.getDateFomatted( event.getDtEnd() ) );
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
        txtName     = (TextView) findViewById( R.id.textEvtName);
        txtInfo     = (TextView) findViewById( R.id.textEvtInfo);
        txtContact  = (TextView) findViewById( R.id.textEvtContact);
        txtDt       = (TextView) findViewById( R.id.textEvtDate);
    }
}
