package com.gaj2l.eventtus.view.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.view.fragments.EventFragment;


public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static int ID_CAMERA_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getFragmentManager().getBackStackEntryCount() <= 0) {
            getFragmentManager().beginTransaction().replace(R.id.fragment, new EventFragment()).addToBackStack("EventFragment").commit();
        }
        setContentView(R.layout.activity_base);

        initComponents();
    }

    private void initComponents() {
        try {
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
                imgUser.setImageBitmap(Util.base642bitmap(Session.getInstance(getApplicationContext()).getString("image")));
            else
                imgUser.setImageDrawable(getResources().getDrawable(R.drawable.person,null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasPermission() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, ID_CAMERA_REQUEST);
    }

    public void registerEvent() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_credits: {
                Intent credits = new Intent(BaseActivity.this, CreditsActivity.class);
                startActivity(credits);
            }
            break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_new_event) {
            if (hasPermission()) {
                registerEvent();
            } else {
                requestPermission();
            }
        } else if (id == R.id.nav_my_events) {
            getFragmentManager().beginTransaction().replace(R.id.fragment, new EventFragment()).commit();
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
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (ID_CAMERA_REQUEST == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                registerEvent();
            }
        }
    }
}
