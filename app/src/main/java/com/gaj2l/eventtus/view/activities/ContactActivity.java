package com.gaj2l.eventtus.view.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gaj2l.eventtus.MyApplication;
import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.services.web.EmailWebService;

public class ContactActivity extends AppCompatActivity {

    private Button btnSend;
    private EditText fieldSubject;
    private EditText fieldMessage;
    private String to_name;
    private String to_email;
    private String from_name;
    private String from_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_contact);
        setContentView(R.layout.activity_contact);

        from_email = Session.getInstance(getApplicationContext()).getString("email");
        from_name  = Session.getInstance(getApplicationContext()).getString("username");
        to_email   = (getIntent().getStringExtra("to")!=null)?   getIntent().getStringExtra("to")   : MyApplication.EMAIL_APPLICATION;
        to_name    = (getIntent().getStringExtra("name")!=null)? getIntent().getStringExtra("name") : MyApplication.EMAIL_NAME;

        this.fieldSubject = (EditText) findViewById(R.id.txtSubject);
        this.fieldSubject.requestFocus();
        this.fieldMessage = (EditText) findViewById(R.id.txtMessage);
        this.btnSend = (Button) findViewById(R.id.btnSend);

        this.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(v);
            }
        });
    }

    private void send( final View v )
    {
        String subject = fieldSubject.getText().toString();
        String message = fieldMessage.getText().toString();

        if (!subject.equals("") && !message.equals("")) {
            EmailWebService.send(message, subject, from_name, from_email, to_name, to_email, new EmailWebService.ActionEvent() {
                @Override
                public void afterSend(String status, String message) {
                    Snackbar.make(v,message,Snackbar.LENGTH_LONG).show();
                }
            });
            finish();
        } else {
            Snackbar.make(v, R.string.validate_fields_message, Snackbar.LENGTH_LONG).show();
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

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
