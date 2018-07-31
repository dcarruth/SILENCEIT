package com.example.carruth.silenceit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Locations locations;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Location object that is created initially in the main activity. Passed here to
        //get location information from the map if necessary.

        locations = new Locations();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    /**
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Add a marker in Sydney and move the camera
        LatLng omaha = new LatLng(41.257160, -95.995102);
        mMap.addMarker(new MarkerOptions().position(omaha).title("Omaha, Nebraska"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(omaha));


        //Listen for the user to click on the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            //When the user clicks a location, set the marker there and update information.
            @Override
            public void onMapClick(LatLng latLng) {
                //Set the lat and long in the location object
               locations.setLatitude((float) latLng.latitude);
               locations.setLongitude((float) latLng.longitude);

               MarkerOptions markerOptions = new MarkerOptions();
               markerOptions.position(latLng);
               markerOptions.title(latLng.latitude + ":" + latLng.longitude);
               mMap.clear();
               mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
               mMap.addMarker(markerOptions);
            }
        });
    }
}
