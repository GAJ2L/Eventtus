package com.gaj2l.eventtus.view.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.models.Activity;

public class SurveyActivty extends AppCompatActivity {

    private Activity activity;
    private TextView txtQuestion;
    private Button btnBack;
    private Button btnSave;
    private Button btnNext;
    private ScrollView paneOptions;

    private int current = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_activty);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.survey);

        initComponents();

        load();
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

    private void load() {
        activity = ComponentProvider.getServiceComponent().getActivityService().get(getIntent().getExtras().getLong("activity"));

        txtQuestion.setText("1/10 - Praesent ut pretium sapien. Donec interdum semper dolor, ut eleifend lectus consequat sed. Nulla bibendum odio enim, a pretium urna consequat quis. Nam vestibulum eros erat, eget imperdiet lorem fermentum sed. ");

        loadOptions();
    }

    private void loadOptions()
    {
        paneOptions.removeAllViews();

        RadioGroup rg = new RadioGroup( paneOptions.getContext() );

        for ( int i = 0; i < current; i ++)
        {
            RadioButton rd = new RadioButton(rg.getContext());

            rd.setText(" Component " + i + " de " + current );

            rg.addView(rd);
        }

        paneOptions.addView(rg);
    }

    private void onSave() {
        Toast.makeText(getApplicationContext(),"SAVE", Toast.LENGTH_LONG ).show();
    }

    private void onBack() {

        current --;
        loadOptions();
    }

    private void onNext() {
        
        current ++;
        loadOptions();
    }

    private void initComponents() {
        txtQuestion = (TextView) findViewById(R.id.question_title);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnSave = (Button) findViewById(R.id.btnSave);

        paneOptions = (ScrollView) findViewById( R.id.paneOptions);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext();
            }
        });
    }
}
