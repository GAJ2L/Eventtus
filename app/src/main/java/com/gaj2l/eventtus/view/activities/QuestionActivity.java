package com.gaj2l.eventtus.view.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.busines.socket.ClientSocket;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.models.Message;
import com.gaj2l.eventtus.view.adapters.QuestionAdapter;

import org.threeten.bp.OffsetDateTime;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity
{
    private EditText txt;
    private RecyclerView recyclerView;
    private long activity_id;
    private long user_id;
    private List<Message> messages = new ArrayList();
    private ClientSocket client    = new ClientSocket()
    {
        @Override
        public void onRecive(String data) throws Exception
        {
            new Thread()
            {
                @Override
                public void run()
                {
                    QuestionActivity.this.runOnUiThread( new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            com.gaj2l.eventtus.lib.Message.show(getBaseContext(), "Recebido");
                        }
                    });
                }
            }.start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        client.start();

        setContentView(R.layout.activity_question);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt = (EditText) findViewById(R.id.messageField);
        recyclerView = (RecyclerView) findViewById(R.id.listSndMsg);

        setTitle(R.string.title_send_a_question);

        activity_id = getIntent().getExtras().getLong("activity");
        user_id = Session.getInstance(getApplicationContext()).getLong("user");

        Activity activity = ComponentProvider.getServiceComponent().getActivityService().get(activity_id);

        Event event = ComponentProvider.getServiceComponent().getEventService().get(activity.getEventId());

        if ( event.getCor() != null )
        {
            getSupportActionBar().setBackgroundDrawable( new ColorDrawable(Color.parseColor( event.getCor() ) ) );

            DrawableCompat.setTint( Button.class.cast( findViewById( R.id.btnSendMsg ) ).getCompoundDrawables()[0], Color.parseColor( event.getCor() ) );
        }

        messages = getMessages();

        Button fab = (Button) findViewById(R.id.btnSendMsg);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sndMsg(view);
            }
        });

        fab.setEnabled(false);

        txt.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){}

            @Override
            public void afterTextChanged(Editable arg0) {
                enableSubmitIfReady();
            }
        });

        QuestionAdapter adapter = new QuestionAdapter(messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        try
        {
            // envia mensagem para o server
            client.send("eventtus");
        }
        catch (Exception e)
        {
            com.gaj2l.eventtus.lib.Message.error(getApplicationContext(),e.getMessage());
            e.printStackTrace();
        }
    }

    private void enableSubmitIfReady()
    {
        boolean isReady = txt.getText().toString().length() >= 1;

        Button fab = (Button) findViewById(R.id.btnSendMsg);
        fab.setEnabled(isReady);
    }

    private List<Message> getMessages()
    {
        List<Message> m = ComponentProvider.getServiceComponent().getMessageService().getMessagesByActivity(activity_id, user_id);
        return (m != null && !m.isEmpty()) ? m : new ArrayList();
    }

    private Message store(String msg) throws Exception
    {
        Message m = new Message();
        m.setActivityId(activity_id);
        m.setDtStore(OffsetDateTime.now());
        m.setContent(msg);
        m.setUserId(user_id);
        m.setEmail(Session.getInstance(getApplicationContext()).getString("email"));
        Activity activity = ComponentProvider.getServiceComponent().getActivityService().get(activity_id);
        m.setActivityServiceId(activity.getActivityServiceId());

        ComponentProvider.getServiceComponent().getMessageService().store(m);

        return m;
    }

    private void sndMsg(View v)
    {
        try
        {
            txt.clearFocus();
            InputMethodManager in = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(txt.getWindowToken(), 0);

            String msg = txt.getText().toString();
            Message message = store(msg);
            client.send(message.toJson());
            messages.add(message);
            txt.setText("");
            txt.clearFocus();
        }
        catch (Exception e)
        {
            com.gaj2l.eventtus.lib.Message.show(v.getContext(), R.string.error_send_message);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}