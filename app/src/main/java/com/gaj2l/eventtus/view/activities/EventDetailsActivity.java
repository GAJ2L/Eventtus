package com.gaj2l.eventtus.view.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Message;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.models.Event;

public class EventDetailsActivity extends AppCompatActivity
{
    private Event event;

    private TextView txtName;
    private TextView txtInfo;
    private TextView txtContact;
    private TextView txtDt;

    private TextView lblName;
    private TextView lblInfo;
    private TextView lblContact;
    private TextView lblDt;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_details_event);

        initComponents();

        load();

        if( event.getCor() != null )
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(event.getCor())));
    }

    private void load()
    {
        try
        {
            event = ComponentProvider.getServiceComponent().getEventService().get(getIntent().getExtras().getLong("menu"));

            txtName.setText(event.getName());
            txtInfo.setText(event.getDescription());
            txtContact.setText(event.getContactName() + "\n\n" + event.getContactMail() + "\n\n" + event.getContactPhone());
            txtDt.setText(Util.getDateFomatted(event.getDtStart()) + " - " + Util.getDateFomatted(event.getDtEnd()));
            imageView.setImageBitmap( Util.base642bitmap( event.getBanner() ));
            imageView.setAdjustViewBounds( true );
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            if( event.getCor() != null )
            {
                int cor = Color.parseColor(event.getCor());
                lblContact.setTextColor(cor);
                lblDt.setTextColor(cor);
                lblInfo.setTextColor(cor);
                lblName.setTextColor(cor);
            }
        }
        catch ( Exception e )
        {
            Message.error(getApplicationContext(),e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
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

        lblName     = (TextView) findViewById( R.id.lblName);
        lblInfo     = (TextView) findViewById( R.id.lblDescription);
        lblContact  = (TextView) findViewById( R.id.lblContact);
        lblDt       = (TextView) findViewById( R.id.lblDate);

        imageView   = (ImageView) findViewById( R.id.eventImageView);
    }
}