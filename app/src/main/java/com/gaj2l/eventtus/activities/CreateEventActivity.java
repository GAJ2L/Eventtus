package com.gaj2l.eventtus.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gaj2l.eventtus.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

/**
 * Created by Artur Tomasi on 05/04/17.
 */

public class CreateEventActivity
    extends
        AppCompatActivity
{
    private SurfaceView cameraView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        initComponents();
    }

    private void initComponents()
    {
        cameraView = (SurfaceView) findViewById(R.id.camera_view);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600, 1024)
                .build();

        cameraView.getHolder().addCallback( new SurfaceHolder.Callback()
        {
            @Override
            public void surfaceCreated(SurfaceHolder holder)
            {
                try
                {
                    if ( ActivityCompat.checkSelfPermission( CreateEventActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        return;
                    }

                    cameraSource.start(cameraView.getHolder());
                }

                catch ( IOException ie )
                {
                    ie.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged( SurfaceHolder holder, int format, int width, int height ) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder)
            {
                cameraSource.stop();
            }
        } );

        barcodeDetector.setProcessor( new Detector.Processor<Barcode>()
        {
            @Override
            public void release(){}

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections)
            {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if ( barcodes.size() != 0 )
                {
                    EditText.class.cast( R.id.qr_code_display_layout_input ).setText( barcodes.get(0).displayValue );
                }
            }
        } );

        FloatingActionButton btnSendCode = FloatingActionButton.class.cast( findViewById( R.id.send_qr_code) );

        btnSendCode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                redirect();
            }
        } );
    }

    private void redirect()
    {
        Intent event = new Intent( CreateEventActivity.this, EventActivity.class);
        event.putExtra( "Event", EditText.class.cast( findViewById( R.id.qr_code_display_layout_input ) ).getText().toString() );

        startActivity( event);
    }
}
