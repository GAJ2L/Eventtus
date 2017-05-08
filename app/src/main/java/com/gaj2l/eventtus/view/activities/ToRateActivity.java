package com.gaj2l.eventtus.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Preload;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.models.Evaluation;
import com.gaj2l.eventtus.services.web.EvaluationWebService;

import org.threeten.bp.OffsetDateTime;

/**
 * Created by lucas.tomasi on 28/04/17.
 */

public class ToRateActivity extends AppCompatActivity
{
    private Button btnToRate;
    private RatingBar rtbStar;
    private TextView txtComment;
    private long activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_to_rate_activity);
        setContentView(R.layout.activity_to_rate);

        activity   = getIntent().getExtras().getLong("activity");

        btnToRate  = (Button)    findViewById(R.id.btnSendRate);
        txtComment = (TextView)  findViewById(R.id.txtComment);
        rtbStar    = (RatingBar) findViewById(R.id.rtbStar);


        btnToRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveEvaluation(v);
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void onSaveEvaluation(final View v)
    {
        try
        {
            btnToRate.setEnabled(false);
            btnToRate.setClickable(false);
            final Preload p = new Preload(v.getContext());
            p.show();
            if(txtComment.getText().toString().trim().equals("") || rtbStar.getRating() == 0)
            {
                Snackbar.make(v,R.string.validate_fields_message,Snackbar.LENGTH_LONG).show();
                return;
            }
            Evaluation e = new Evaluation();
            e.setComment(txtComment.getText().toString());
            e.setStars(rtbStar.getRating());
            e.setDtStore(OffsetDateTime.now());
            e.setEmail(Session.getInstance(getApplicationContext()).getString("email"));
            e.setActivity(activity);

            ComponentProvider.getServiceComponent().getEvaluationService().store(e);

            EvaluationWebService.sendServer(e, new EvaluationWebService.Action() {
                @Override
                public void onEvaluate(String status) {
                    p.dismiss();
                    if(status == "success")
                    {
                        Snackbar.make(v, R.string.success_evaluate, Snackbar.LENGTH_LONG).show();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try
                                {
                                    Thread.sleep(1000);
                                    finish();
                                }
                                catch (InterruptedException e1)
                                {
                                    e1.printStackTrace();
                                }
                            }
                        }).start();
                    }
                    else
                    {
                        Snackbar.make(v, R.string.error_evaluate, Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Snackbar.make(v, R.string.error_evaluate, Snackbar.LENGTH_LONG).show();
        }
    }
}