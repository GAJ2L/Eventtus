package com.gaj2l.eventtus.view.activities;

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
import com.gaj2l.eventtus.models.Evaluation;

import org.threeten.bp.OffsetDateTime;

/**
 * Created by lucas.tomasi on 28/04/17.
 */

public class ToRateActivity extends AppCompatActivity
{
    private Button btnToRate;
    private RatingBar rtbStar;
    private TextView txtComment;
    private int activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_to_rate_activity);
        setContentView(R.layout.activity_to_rate);

        activity   = getIntent().getExtras().getInt("activity");

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

    private void onSaveEvaluation(View v)
    {
        Evaluation e = new Evaluation();
        e.setComment(txtComment.getText().toString());
        e.setStars(rtbStar.getRating());
        e.setDtStore(OffsetDateTime.now());

        Snackbar.make(v,"Comnetatdo",Snackbar.LENGTH_LONG).show();
        System.out.println(e);
    }
}
