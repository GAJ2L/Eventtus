package com.gaj2l.eventtus.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Internet;
import com.gaj2l.eventtus.lib.Message;
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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_to_rate_activity);
        setContentView(R.layout.activity_to_rate);

        activity = getIntent().getExtras().getLong("activity");

        btnToRate = (Button) findViewById(R.id.btnSendRate);
        txtComment = (TextView) findViewById(R.id.txtComment);
        rtbStar = (RatingBar) findViewById(R.id.rtbStar);


        btnToRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveEvaluation(v);
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void onSaveEvaluation(final View v)
    {
        final Preload p = new Preload(v.getContext());
        p.show();

        try
        {
            // check stars rate
            if (rtbStar.getRating() == 0)
            {
                p.dismiss();
                Message.show(v.getContext(), R.string.validate_fields_message);
                return;
            }

            btnToRate.setEnabled(false);
            btnToRate.setClickable(false);

            // check connection
            if (Internet.isConnect(getApplicationContext()))
            {
                Evaluation e = new Evaluation();
                e.setComment(txtComment.getText().toString());
                e.setStars(rtbStar.getRating());
                e.setDtStore(OffsetDateTime.now());
                e.setEmail(Session.getInstance(getApplicationContext()).getString("email"));
                e.setActivity(activity);

                EvaluationWebService.sendServer(e, new EvaluationWebService.Action()
                {
                    @Override
                    public void onEvaluate(String status)
                    {
                        if (status == "success")
                        {
                            Message.show(getApplicationContext(), R.string.success_evaluate);

                            new Thread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    try
                                    {
                                        Thread.sleep(1000);
                                        finish();
                                    }
                                    catch (Exception e1)
                                    {
                                        Message.error(getApplicationContext(),e1.getMessage());
                                        e1.printStackTrace();
                                    }
                                }
                            }).start();

                        }
                        else
                        {
                            Message.show(getApplicationContext(), R.string.error_evaluate);
                        }
                    }
                });

                // store object after send server
                ComponentProvider.getServiceComponent().getEvaluationService().store(e);
            }
            else
            {
                p.dismiss();
                btnToRate.setEnabled(true);
                btnToRate.setClickable(true);
                Message.show(getApplicationContext(), R.string.err_conection);
                return;
            }
        }
        catch (Exception e)
        {
            p.dismiss();
            btnToRate.setEnabled(true);
            btnToRate.setClickable(true);
            Message.error(getApplicationContext(), R.string.error_evaluate);
            e.printStackTrace();
        }
    }
}