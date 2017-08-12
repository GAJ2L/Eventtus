package com.gaj2l.eventtus.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Internet;
import com.gaj2l.eventtus.lib.Preload;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.services.web.EvaluationWebService;

public class ActivityDetailsActivity extends AppCompatActivity
{
    private Activity activity;

    private TextView  txtName;
    private TextView  txtDt;
    private TextView  txtLocation;
    private TextView  txtEvt;
    private TextView  ttEvals;
    private RatingBar avgStars;

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

    private void load()
    {
        activity = ComponentProvider.getServiceComponent().getActivityService().get(getIntent().getExtras().getLong("activity"));
        Event event = ComponentProvider.getServiceComponent().getEventService().get( activity.getEventId() );

        txtName.setText(activity.getName());
        txtLocation.setText(activity.getLocalName());
        txtEvt.setText( event.getName() );
        txtDt.setText( Util.getAllDateFomatted( activity.getDtStart() ) + " - " + Util.getDateFomatted( activity.getDtEnd() ) );

        if(Internet.isConnect(getApplicationContext()))
        {
            final Preload p = new Preload(ActivityDetailsActivity.this);
            p.show();

            EvaluationWebService.getMedia(activity.getActivityServiceId(), new EvaluationWebService.AbstractAction()
            {
                @Override
                public void onEvaluate(float stars, int count)
                {
                    if (count != 0)
                    {
                        ttEvals.setText("Total: " + count);
                    }
                    else
                    {
                        ttEvals.setText(R.string.no_evaluations);
                    }

                    avgStars.setRating(stars);
                    avgStars.setClickable(false);
                    avgStars.setIsIndicator(true);

                    p.dismiss();
                }
            });
        }
        else
        {
            ttEvals.setText(R.string.err_conection);
            avgStars.setRating(0);
            avgStars.setClickable(false);
            avgStars.setIsIndicator(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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
        txtName     = (TextView) findViewById( R.id.textActivityName);
        txtDt       = (TextView) findViewById( R.id.textActivityDate);
        txtLocation = (TextView) findViewById( R.id.textActivityLocation);
        txtEvt      = (TextView) findViewById( R.id.textActivityEvent);
        ttEvals     = (TextView) findViewById(R.id.txtTotalEvaluation);
        avgStars    = (RatingBar) findViewById(R.id.avgStars);
    }
}