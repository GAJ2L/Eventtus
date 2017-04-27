package com.gaj2l.eventtus.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gaj2l.eventtus.R;

public class ContactActivity extends AppCompatActivity {

    private Button btnSend;
    private EditText fieldSubject;
    private EditText fieldMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_contact);
        setContentView(R.layout.activity_contact);

        this.fieldSubject = (EditText) findViewById(R.id.txtSubject);
        this.fieldMessage = (EditText) findViewById(R.id.txtMessage);
        this.btnSend = (Button) findViewById(R.id.btnSend);

        this.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = fieldSubject.getText().toString();
                String message = fieldMessage.getText().toString();

                if (!subject.equals("") && !message.equals("")) {
                    Toast.makeText(ContactActivity.this, R.string.send_message, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(ContactActivity.this, R.string.validate_fields_message, Toast.LENGTH_LONG).show();
                }
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

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
