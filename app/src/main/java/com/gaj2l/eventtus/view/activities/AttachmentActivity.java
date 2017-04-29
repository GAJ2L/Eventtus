package com.gaj2l.eventtus.view.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.models.Attachment;
import com.gaj2l.eventtus.view.adapters.AttachmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 29/04/17.
 */

public class AttachmentActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_attachments_activity);
        setContentView(R.layout.attachment_activity);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.attachments_list);
        getIntent().getExtras().getInt("activity");
        List<Attachment> attachments = new ArrayList();
        Attachment a = new Attachment();
        a.setName("Slide da Palestra");
        a.setSize("20 mb");
        a.setType(Attachment.TYPE_PRESENTATION);
        attachments.add(a);
        Attachment a1 = new Attachment();
        a1.setName("Doc da Palestra");
        a1.setSize("200 mb");
        a1.setType(Attachment.TYPE_DOC);
        attachments.add(a1);
        Attachment a2 = new Attachment();
        a2.setName("Link show de bola");
        a2.setSize("0 b");
        a2.setType(Attachment.TYPE_LINK);
        attachments.add(a2);

        AttachmentAdapter adapter = new AttachmentAdapter(attachments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
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
