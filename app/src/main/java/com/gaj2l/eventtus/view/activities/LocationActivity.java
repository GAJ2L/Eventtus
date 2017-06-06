package com.gaj2l.eventtus.view.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.busines.maps.DirectionFinder;
import com.gaj2l.eventtus.busines.maps.DirectionFinderListener;
import com.gaj2l.eventtus.busines.maps.Route;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Internet;
import com.gaj2l.eventtus.lib.Preload;
import com.gaj2l.eventtus.models.Activity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback, DirectionFinderListener {
    public int ID_ACCESS_FINE_LOCATION = 3;

    private GoogleMap mMap;
    private Activity activity;
    private LocationManager mLocationManager;
    private Preload preload;
    private Location myLocation;

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.title_activity_location);

        if (!hasPermission()) {
            requestPermission();
        } else {
            initComponents();
        }
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


    private void initComponents()
    {
        preload = new Preload(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        activity = ComponentProvider.getServiceComponent().getActivityService().get(getIntent().getExtras().getLong("activity"));

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        myLocation = mLocationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER);

        if ( myLocation == null ) myLocation = mLocationManager.getLastKnownLocation( LocationManager.NETWORK_PROVIDER );
        if ( myLocation == null ) myLocation = mLocationManager.getLastKnownLocation( LocationManager.PASSIVE_PROVIDER);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {return;}

        mLocationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 50, this );
    }


    private boolean hasPermission() {
        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ID_ACCESS_FINE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (ID_ACCESS_FINE_LOCATION == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initComponents();
            } else {
                finish();
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String[] geolocation = activity.getLocalGeolocation().split(",");

        if (geolocation.length > 1) {
            LatLng location = new LatLng(Double.parseDouble(geolocation[0]), Double.parseDouble(geolocation[1]));

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            mMap.setMyLocationEnabled(true);

            mMap.addMarker(new MarkerOptions()
                            .position( location )
                            .title( activity.getName() ));

            mMap.setIndoorEnabled( true );

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));

            if ( myLocation != null )
                sendRequest( myLocation.getLatitude() + "," + myLocation.getLongitude(), activity.getLocalGeolocation() );
        }
    }


    private void sendRequest( String origin, String destination )
    {
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try
        {
            if(Internet.isConnect(getApplicationContext()))
            {
                new DirectionFinder(this, origin, destination).execute();
            }
            else
            {
                Snackbar.make(getWindow().findViewById(R.id.map), R.string.err_conection_maps, Snackbar.LENGTH_LONG).show();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDirectionFinderStart()
    {
        try
        {
            preload.show();
            if (originMarkers != null) {
                for (Marker marker : originMarkers) {
                    marker.remove();
                }
            }

            if (destinationMarkers != null) {
                for (Marker marker : destinationMarkers) {
                    marker.remove();
                }
            }

            if (polylinePaths != null) {
                for (Polyline polyline : polylinePaths) {
                    polyline.remove();
                }
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        try
        {
            preload.dismiss();

            polylinePaths = new ArrayList<>();
            originMarkers = new ArrayList<>();
            destinationMarkers = new ArrayList<>();

            for (Route route : routes) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 18));

                PolylineOptions polylineOptions = new PolylineOptions().
                        geodesic(true).
                        color( getResources().getColor( R.color.colorPrimaryDark, null ) ).
                        width(18);

                for (int i = 0; i < route.points.size(); i++) {
                    polylineOptions.add(route.points.get(i));
                }

                polylinePaths.add(mMap.addPolyline(polylineOptions));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(final Location location)
    {
        myLocation = location;

        onMapReady( mMap );
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
}

