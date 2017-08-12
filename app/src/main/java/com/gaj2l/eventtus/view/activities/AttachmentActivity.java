package com.gaj2l.eventtus.view.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.models.Attachment;
import com.gaj2l.eventtus.view.adapters.AttachmentAdapter;

import java.util.List;

/**
 * Created by lucas on 29/04/17.
 */

public class AttachmentActivity extends AppCompatActivity
{
    public int ID_WRITE_EXTERNAL_STORAGE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_attachments_activity);
        setContentView(R.layout.attachment_activity);

        if( !hasPermission() )
        {
            requestPermission();
        }
        else
        {
            initComponents();
        }
    }

    private void initComponents()
    {
        RecyclerView recyclerView         = (RecyclerView) findViewById(R.id.attachments_list);
        List<Attachment> attachments      = ComponentProvider.getServiceComponent().getAttachmentService().getAttachmentsByActivity(getIntent().getExtras().getLong("activity"));
        AttachmentAdapter adapter         = new AttachmentAdapter(attachments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
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


    private boolean hasPermission()
    {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},ID_WRITE_EXTERNAL_STORAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        if (ID_WRITE_EXTERNAL_STORAGE_REQUEST== requestCode)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                initComponents();
            }
            else
            {
                finish();
            }
        }
    }
}