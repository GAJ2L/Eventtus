package com.gaj2l.eventtus.view.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.view.adapters.QuestionAdapter;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private EditText txt;
    private RecyclerView recyclerView;

    private List<String> messages = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txt = (EditText)findViewById( R.id.messageField );

        recyclerView = (RecyclerView) findViewById(R.id.attachments_list);

        setTitle(R.string.title_questions);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sndMsg();
            }
        });

        QuestionAdapter adapter = new QuestionAdapter( messages );
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void sndMsg()
    {
        String msg = txt.getText().toString();

        messages.add( msg );

        Util.socket().send( msg );

        txt.setText( "" );
    }
}
