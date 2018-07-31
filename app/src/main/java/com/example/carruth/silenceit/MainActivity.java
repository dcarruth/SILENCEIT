package com.example.carruth.silenceit;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("location",location);
        startActivity(intent);
    }
}
