package com.gaj2l.eventtus.view.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.lib.Internet;
import com.gaj2l.eventtus.lib.Message;
import com.gaj2l.eventtus.lib.Preload;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.services.web.EventWebService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.Arrays;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Artur Tomasi on 05/04/17.
 */
public class CreateEventActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state)
    {
        super.onCreate(state);

        mScannerView = new ZXingScannerView(this);

        mScannerView.setFormats( Arrays.asList( BarcodeFormat.QR_CODE ) );
        mScannerView.setAutoFocus( true );

        setContentView(mScannerView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_create_event);

        String color = Session.getInstance(getApplicationContext()).getString("color");

        if( color != null && !color.equalsIgnoreCase("")  )
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                redirect();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult( final Result rawResult )
    {
        if( Internet.isConnect(CreateEventActivity.this) )
        {
            mScannerView.stopCamera();
            final Preload p = new Preload(CreateEventActivity.this);
            p.show();
            EventWebService.getEvent(Session.getInstance(getApplicationContext()).getString("email"), rawResult.getText(), new EventWebService.ActionEvent() {
                @Override
                public void onEvent(Event event) {
                    p.dismiss();
                    redirect();
                    int msg = (event != null ) ? R.string.add_event_success : R.string.add_event_error;
                    Message.show(mScannerView.getContext(), msg);
                }
            });
        }
        else
        {
            Message.show(mScannerView.getContext(), R.string.err_conection);
        }
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onBackPressed() {
        redirect();
    }

    private void redirect()
    {
        Intent events = new Intent(CreateEventActivity.this,BaseActivity.class);
        startActivity(events);
        finish();
    }
}