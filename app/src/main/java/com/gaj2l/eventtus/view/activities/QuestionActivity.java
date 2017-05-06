package com.gaj2l.eventtus.view.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.models.Attachment;
import com.gaj2l.eventtus.models.Message;
import com.gaj2l.eventtus.view.adapters.QuestionAdapter;

import org.threeten.bp.Clock;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private EditText txt;
    private RecyclerView recyclerView;
    private long activity_id;
    private long user_id;
    private List<String> messages = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_question);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt          = (EditText)findViewById( R.id.messageField );
        recyclerView = (RecyclerView) findViewById(R.id.listSndMsg);

        setTitle(R.string.title_send_a_question);

        activity_id = getIntent().getExtras().getLong("activity");
        user_id     = Session.getInstance(getApplicationContext()).getLong("user");
        messages    = getMessages();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnSendMsg);
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sndMsg(view);
            }
        });

        QuestionAdapter adapter = new QuestionAdapter( messages );
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private List<String> getMessages()
    {
        List<String> msgs      = new ArrayList();
        List<Message> messages = ComponentProvider.getServiceComponent().getMessageService().getMessagesByActivity( activity_id, user_id );

        List<Message> m2= ComponentProvider.getServiceComponent().getMessageService().list();

        if( messages !=  null && messages.size() != 0 )
        {
            for( Message message : messages )
            {
                msgs.add( message.getContent() );
            }
        }

        return msgs;
    }

    private void store(String msg) throws Exception
    {
        Message m = new Message();
        m.setActivityId(activity_id);
        m.setDtStore(OffsetDateTime.now());
        m.setContent(msg);
        m.setUserId(user_id);

        ComponentProvider.getServiceComponent().getMessageService().store(m);
    }

    private void sndMsg(View v)
    {
        try
        {
            String msg = txt.getText().toString();
            store(msg);
            Util.socket().send( msg );
            messages.add( msg );
            txt.setText( "" );
            txt.clearFocus();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Snackbar.make(v,R.string.error_send_message,Snackbar.LENGTH_LONG).show();
        }
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
}
