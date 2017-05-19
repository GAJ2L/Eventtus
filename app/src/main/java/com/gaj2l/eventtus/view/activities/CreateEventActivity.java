package com.gaj2l.eventtus.view.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.lib.Internet;
import com.gaj2l.eventtus.lib.Preload;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.lib.WebService;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.services.web.EventWebService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.Arrays;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Artur Tomasi on 05/04/17.
 */
public class CreateEventActivity
    extends
        AppCompatActivity
    implements
        ZXingScannerView.ResultHandler
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        if( Internet.isConnect(CreateEventActivity.this) ) {
            mScannerView.stopCamera();
            final Preload p = new Preload(CreateEventActivity.this);
            p.show();
            EventWebService.getEvent(Session.getInstance(getApplicationContext()).getString("email"), rawResult.getText(), new EventWebService.ActionEvent() {
                @Override
                public void onEvent(String status) {
                    p.dismiss();
                    redirect();
                    int msg = (status.equalsIgnoreCase("success")) ? R.string.add_event_success : R.string.add_event_error;
                    Snackbar.make(mScannerView , msg, Snackbar.LENGTH_LONG).show();
                }
            });
        } else {
            Snackbar.make( mScannerView, R.string.err_conection, Snackbar.LENGTH_LONG).show();
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
