package com.gaj2l.eventtus.view.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.busines.maps.DirectionFinder;
import com.gaj2l.eventtus.busines.maps.DirectionFinderListener;
import com.gaj2l.eventtus.busines.maps.Route;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Event;
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

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener
{
    public int ID_ACCESS_FINE_LOCATION = 3;

    private GoogleMap mMap;
    private Activity activity;
    private LocationManager mLocationManager;
    private ProgressDialog progressDialog;

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        if (!hasPermission()) {
            requestPermission();
        } else {
            initComponents();
        }

    }

    private void initComponents() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        activity = ComponentProvider.getServiceComponent().getActivityService().get(getIntent().getExtras().getLong("activity"));

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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

            Location myLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            mMap.addMarker(new MarkerOptions()
                            .position( location )
                            .title( activity.getName() ));

            mMap.setIndoorEnabled( true );

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));

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

        try {
            new DirectionFinder(this, origin, destination ).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDirectionFinderStart()
    {
        progressDialog = ProgressDialog.show( this, getResources().getString( R.string.find_routes ),
                getResources().getString( R.string.find_routes ), true);

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
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
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
}

