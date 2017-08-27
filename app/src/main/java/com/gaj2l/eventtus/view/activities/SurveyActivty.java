package com.gaj2l.eventtus.view.activities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Message;
import com.gaj2l.eventtus.lib.Preload;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Answer;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.models.Option;
import com.gaj2l.eventtus.models.Survey;
import com.gaj2l.eventtus.services.web.SurveyWebService;

public class SurveyActivty extends AppCompatActivity
{
    private Activity activity;
    private Survey survey;

    private Button btnBack, btnNext;

    private TextView txtSurvey;

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LayoutInflater layoutInflater;

    private Preload preload;

    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_survey_activty);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.survey);

        activity = ComponentProvider.getServiceComponent().getActivityService().get(getIntent().getExtras().getLong("activity"));

        Event event = ComponentProvider.getServiceComponent().getEventService().get( activity.getEventId() );

        if( event.getCor() !=null )
            color = Color.parseColor( event.getCor() );
        else
            color = getResources().getColor( R.color.colorPrimaryDark, null );

        initComponents();

        load();

        updateColors();
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

    private void load()
    {
        try
        {
            preload = new Preload(this); preload.show();

            SurveyWebService.getSurvey(activity, new SurveyWebService.ActionEvent<Survey>() {
                @Override
                public void onEvent(Survey survey) {

                SurveyActivty.this.survey = survey;

                if ( SurveyActivty.this.survey != null && SurveyActivty.this.survey.hasQuestions() ){
                    myViewPagerAdapter = new MyViewPagerAdapter();
                    viewPager.setAdapter(myViewPagerAdapter);
                    viewPager.addOnPageChangeListener( viewPagerPageChangeListener );
                    updateEditable(0);
                } else {

                    setContentView(R.layout.no_survey);

                    TextView.class.cast( findViewById( R.id.no_survey_info ) ).setTextColor( color );
                    TextView.class.cast( findViewById( R.id.no_survey_title ) ).setTextColor( color );

                    DrawableCompat.setTint( ImageView.class.cast( findViewById( R.id.no_survey_image ) ).getDrawable(), color );
                }

                preload.dismiss();
            }
            } );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateColors()
    {
        ColorDrawable drawable = new ColorDrawable( color );

        getSupportActionBar().setBackgroundDrawable( drawable );

        RelativeLayout container = (RelativeLayout) findViewById( R.id.survey_pane );

        ColorDrawable background = new ColorDrawable( color );

        background.setAlpha(30);

        container.setBackground( background );
    }

    private View composePane(ViewGroup container, final int position )
    {
        try {
            RelativeLayout layout = (RelativeLayout) layoutInflater.inflate(R.layout.survey_form, container, false);

            TextView.class.cast(layout.findViewById(R.id.question_title)).setText(survey.question(position).name());

            RadioGroup rg = new RadioGroup(layout.getContext());

            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    survey.answer( new Answer( survey.question( position ), checkedId ), position );
                }
            });

            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{ new int[]{-android.R.attr.state_enabled}, new int[]{android.R.attr.state_enabled} },
                    new int[] { Color.BLACK , color } );

            for (Option opt : survey.question( position ).options()) {
                final RadioButton rd = new RadioButton(rg.getContext());
                rd.setTextSize(20);
                rd.setPadding(20, 20, 20, 20);
                rd.setId(opt.value());
                rd.setText(opt.name());
                rd.setButtonTintList(colorStateList);

                rg.addView(rd);

                if (survey.answer(position) != null && survey.answer(position).option() == opt.value()) {
                    rg.check(rd.getId());
                }
            }

            ScrollView.class.cast(layout.findViewById(R.id.paneOptions)).addView(rg);

            return layout;

        } catch(Exception e){
            e.printStackTrace();
            Message.error(getApplicationContext(), R.string.error);
        }

        return null;
    }

    private void onSave() {
        try {
            if (survey.canFinish()) {
                SurveyWebService.finish(survey, activity, Session.getInstance(getApplicationContext()).getString("email"), new SurveyWebService.ActionEvent<Boolean>() {
                    @Override
                    public void onEvent(Boolean survey) {
                        finish();
                        Message.show( getApplicationContext(), getString(R.string.success) );
                    }
                });

            } else {
                Message.show( getApplicationContext(), getString(R.string.message_error_survey) );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateEditable( int position )
    {
        txtSurvey.setText( (position + 1) + "/" + survey.size() );

        btnNext.setText( position == survey.size() - 1 ? getString(R.string.finish): getString(R.string.next) );

        btnBack.setVisibility( position == 0 ? View.INVISIBLE : View.VISIBLE );
    }

    private void initComponents()
    {
        txtSurvey = (TextView) findViewById(R.id.survey_text);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);

        viewPager = (ViewPager) findViewById( R.id.pager_survey );


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem() + 1;
                if (current > 0 ) {
                    viewPager.setCurrentItem(current -2);
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int current = viewPager.getCurrentItem() + 1;
            if (current < survey.size() ) {
                viewPager.setCurrentItem(current);
            } else {
                onSave();
            }
            }
        });


        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    //  viewpager change listener
    private ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageSelected(int position)
        {
            updateEditable( position );
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {}

        @Override
        public void onPageScrollStateChanged(int arg0) {}
    };

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter
    {
        public MyViewPagerAdapter() {}

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            View view = composePane( container, position );

            container.addView( view );

            return view;
        }

        @Override
        public int getCount() {
            return survey.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) { container.removeView((View) object);}
    }
}
