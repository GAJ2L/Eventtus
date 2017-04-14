package com.gaj2l.eventtus.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    public void handleResult( Result rawResult )
    {
        mScannerView.resumeCameraPreview( this );
    }
}
