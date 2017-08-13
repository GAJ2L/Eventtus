package com.gaj2l.eventtus.view.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.lib.Message;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.view.controllers.EventListener;
import com.gaj2l.eventtus.view.controllers.ViewController;


public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private static int ID_CAMERA_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try {
            if (getFragmentManager().getBackStackEntryCount() <= 0)
            {
                ViewController.redirectEvents( getFragmentManager(), getApplicationContext() );
            }

            setContentView(R.layout.activity_base);

            initComponents();
        }

        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    private void initComponents()
    {
        try
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            //PERFIL
            NavigationView navigationView = NavigationView.class.cast(findViewById(R.id.nav_view));

            navigationView.setNavigationItemSelectedListener(this);

            TextView lblName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.lblName);
            TextView lblEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.lblEmail);
            ImageView imgUser = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imgUser);

            lblName.setText(Session.getInstance(getApplicationContext()).getString("username"));
            lblEmail.setText(Session.getInstance(getApplicationContext()).getString("email"));

            if( !Session.getInstance(getApplicationContext()).getString("image").equalsIgnoreCase("") )
            {
                imgUser.setImageBitmap(Util.base642bitmap(Session.getInstance(getApplicationContext()).getString("image")));
            }
            else
            {
                imgUser.setImageDrawable(getResources().getDrawable(R.drawable.person, null));
            }
        }
        catch (Exception e)
        {
            Message.error(getApplicationContext(),e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean hasPermission()
    {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    public void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, ID_CAMERA_REQUEST);
    }

    public void registerEvent()
    {
        Intent event = new Intent(BaseActivity.this, CreateEventActivity.class);
        startActivity(event);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                finish();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        try {
            int id = item.getItemId();

            if (id == R.id.nav_new_event)
            {
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BaseActivity.this, android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add( getResources().getString( R.string.qrcode ) );
                arrayAdapter.add( getResources().getString( R.string.code ) );

                AlertDialog.Builder alertDialog = new AlertDialog.Builder( BaseActivity.this );
                alertDialog.setAdapter( arrayAdapter, new AlertDialog.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            if (hasPermission()) {
                                registerEvent();
                            } else {
                                requestPermission();
                            }
                        } else {
                            ViewController.showInputCode(BaseActivity.this, new EventListener() {
                                @Override
                                public void onEvent(Object source) throws Exception{
                                    ViewController.redirectEvents(getFragmentManager(), getApplicationContext());
                                }
                            } );
                        }
                    }
                } );

                alertDialog.show();
            }

            else if (id == R.id.nav_my_events) {
                ViewController.redirectEvents(getFragmentManager(), getApplicationContext());
            } else if (id == R.id.nav_info) {
                Intent intent = new Intent(BaseActivity.this, CreditsActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_talk_with_us) {
                Intent intent = new Intent(BaseActivity.this, ContactActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_logout) {
                Intent login = new Intent(BaseActivity.this, LoginActivity.class);
                login.putExtra("logout", true);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

        catch ( Exception e )
        {
            e.printStackTrace();
            Message.error(getApplicationContext(), R.string.error);

            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        if (ID_CAMERA_REQUEST == requestCode)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                registerEvent();
            }
        }
    }
}