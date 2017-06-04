package com.gaj2l.eventtus.view.activities;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Preload;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Answer;
import com.gaj2l.eventtus.models.Option;
import com.gaj2l.eventtus.models.Question;
import com.gaj2l.eventtus.models.Survey;
import com.gaj2l.eventtus.services.web.SurveyWebService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SurveyActivty extends AppCompatActivity {

    private Activity activity;
    private Survey survey;

    private TextView txtSurvey;
    private TextView txtQuestion;
    private Button btnBack;
    private Button btnSave;
    private Button btnNext;
    private ScrollView paneOptions;

    private Preload preload;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
        try {
            preload = new Preload(this);
            preload.show();

            activity = ComponentProvider.getServiceComponent().getActivityService().get(getIntent().getExtras().getLong("activity"));

            SurveyWebService.getSurvey( activity, new SurveyWebService.ActionEvent<Survey>() {
                @Override
                public void onEvent(Survey survey) {

                    SurveyActivty.this.survey = survey;

                    if ( SurveyActivty.this.survey != null && SurveyActivty.this.survey.hasQuestions()) {
                        loadOptions();

                        updateEditable();
                    } else {
                        setContentView( R.layout.no_survey);
                    }

                    preload.dismiss();
                }
            } );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private void loadOptions() {
        try {
            txtSurvey.setText( survey.title() );

            txtQuestion.setText( survey.question().name() );

            paneOptions.removeAllViews();

            RadioGroup rg = new RadioGroup( paneOptions.getContext() );

            rg.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    survey.answer( new Answer( survey.question(), checkedId ) );
                    updateEditable();
                }
            });

            for ( Option opt : survey.question().options() )  {
                final RadioButton rd = new RadioButton(rg.getContext());
                rd.setTextSize( 18 );
                rd.setPadding( 20,20,20,20 );
                rd.setTextColor( getResources().getColor( R.color.colorPrimaryDark, null ) );
                rd.setId( opt.value() );
                rd.setText( opt.name() );
                rg.addView(rd);

                if ( survey.answer() != null && survey.answer().option() == opt.value() ) {
                    rg.check( rd.getId() );
                }
            }

            paneOptions.addView(rg);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private void onSave( final View v ) {
        try {
            if (survey.canFinish()) {
                SurveyWebService.finish(survey, activity, Session.getInstance(getApplicationContext()).getString("email"), new SurveyWebService.ActionEvent<Boolean>() {
                    @Override
                    public void onEvent(Boolean survey) {
                        finish();
                        Snackbar.make( v, getString( R.string.success), Snackbar.LENGTH_LONG).show();
                    }
                });

            } else {
                Snackbar.make( v, getString( R.string.message_error_survey ), Snackbar.LENGTH_LONG).show();
            }

        }  catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private void onBack() {
        if ( survey.hasPrevious() ) {
            survey.previous();
            loadOptions();
        }
    }

    private void onNext() {
        if ( survey.hasNext() ) {
            survey.next();
            loadOptions();
        }
    }

    private void updateEditable()
    {
        btnSave.setEnabled( survey.canFinish() );
        btnSave.setClickable( survey.canFinish() );
        btnSave.setBackgroundColor( survey.canFinish() ? getResources().getColor( R.color.colorPrimaryDark, null ) : Color.rgb(170,170,170) );

        btnNext.setEnabled( survey.hasNext() );
        btnNext.setClickable( survey.hasNext() );
        btnNext.setBackgroundColor( survey.hasNext() ? getResources().getColor( R.color.colorPrimaryDark, null ) : Color.rgb(170,170,170) );

        btnBack.setEnabled( survey.hasPrevious() );
        btnBack.setClickable( survey.hasPrevious() );
        btnBack.setBackgroundColor( survey.hasPrevious() ? getResources().getColor( R.color.colorPrimaryDark, null ) : Color.rgb(170,170,170) );
    }

    private void initComponents() {
        txtQuestion = (TextView) findViewById(R.id.question_title);
        txtSurvey   = (TextView) findViewById(R.id.survey_text);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnSave = (Button) findViewById(R.id.btnSave);

        paneOptions = (ScrollView) findViewById( R.id.paneOptions);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave(v);updateEditable();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack(); updateEditable();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext();updateEditable();
            }
        });
    }
}
