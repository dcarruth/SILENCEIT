package com.example.carruth.silenceit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location location;
    private Locations locations;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Initialize local objects
        locations = new Locations();
        geocoder = new Geocoder(MapActivity.this.getApplicationContext(), Locale.getDefault());

        //Ask for permission to use current location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
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

        //Ask for permission again
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        }

        //Only show the user's location with permission granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);
            //Set Marker on current location
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (lm != null) {
                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                LatLng user = new LatLng(location.getLatitude(), location.getLongitude());

                //Set object lat and long
                locations.setLatitude((float) location.getLatitude());
                locations.setLongitude((float) location.getLongitude());

                //***************************************************************************************************//
                //Get street address from pin on map
                //https://stackoverflow.com/questions/32115968/how-to-get-address-from-latitude-and-longitude-android-google-map-v2
                try {
                    List <Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
                    if (addresses.size() > 0) {
                        //Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea()
                          //      + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                mMap.addMarker(new MarkerOptions().position(user).title(""));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(user));
            }
        }
        //Default location on map is Omaha, Nebraska
        else {
            //Add a marker in Sydney and move the camera
            LatLng omaha = new LatLng(41.257160, -95.995102);
            mMap.addMarker(new MarkerOptions().position(omaha).title("Omaha, Nebraska"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(omaha));
        }

        //Listen for the user to click on the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            //When the user clicks a location, set the marker there and update information.
            @Override
            public void onMapClick(LatLng latLng) {
                //Set the lat and long in the location object
                locations.setLatitude((float) latLng.latitude);
                locations.setLongitude((float) latLng.longitude);

                //******************************************************************************************************//
                //Get street address from pin on map
                //https://stackoverflow.com/questions/32115968/how-to-get-address-from-latitude-and-longitude-android-google-map-v2
                try {
                    List <Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude,1);
                    if (addresses.size() > 0) {
                        Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea()
                         + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                    }
                    } catch (IOException e1) {
                    e1.printStackTrace();
                }

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
