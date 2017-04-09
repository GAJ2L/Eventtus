package com.gaj2l.eventtus.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.lib.Session;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.InputStream;
import java.net.URL;

public class EventActivity
        extends
            AppCompatActivity
        implements
            NavigationView.OnNavigationItemSelectedListener
{
    public static class Events
    {
        public static final int ON_ADD_EVENT = 0;
    }

    private static int ID_CAMERA_REQUEST = 1;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event);

        initComponents();
    }

    private void initComponents()
    {
        try
        {
            //EVENTOS
            Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if( hasPermission() )
                    {
                        registerEvent();
                    }
                    else
                    {
                        requestPermission();
                    }
                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar,
                                                                       R.string.navigation_drawer_open, R.string.navigation_drawer_close );
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            //PERFIL
            NavigationView navigationView = NavigationView.class.cast( findViewById(R.id.nav_view) );

            navigationView.setNavigationItemSelectedListener(this);

            TextView lblName  = (TextView)navigationView.getHeaderView(0).findViewById( R.id.lblName );
            ImageView imgUser = (ImageView)navigationView.getHeaderView(0).findViewById( R.id.imgUser );

            lblName.setText( Session.getInstance(getApplicationContext()).getString("username") );

            if ( android.os.Build.VERSION.SDK_INT > 9 )
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy( policy );
            }

            URL f = new URL(Session.getInstance(getApplicationContext()).getString("image"));
            InputStream i = f.openConnection().getInputStream();
            Bitmap b = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(i), 150, 150, false);
            imgUser.setImageBitmap(b);
        }

        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    private boolean hasPermission()
    {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, ID_CAMERA_REQUEST);
    }

    private void registerEvent()
    {
        Intent event = new Intent(EventActivity.this, CreateEventActivity.class);
        startActivity(event);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if ( drawer.isDrawerOpen(GravityCompat.START) )
        {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate(R.menu.event, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        int id = item.getItemId();

        switch ( id )
        {
            case R.id.action_settings:
            {

            }
            break;

        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected( MenuItem item )
    {
        int id = item.getItemId();

        switch ( id )
        {
            case R.id.nav_new_event:
            {
                if( hasPermission() )
                {
                    registerEvent();
                }
                else
                {
                    requestPermission();
                }
            }
            break;

            case R.id.nav_logout:
            {
                Intent login = new Intent(EventActivity.this,LoginActivity.class);
                login.putExtra("logout",true);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);
            }
            break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        if( ID_CAMERA_REQUEST == requestCode )
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                registerEvent();
            }
        }
    }
}
