package com.example.earlybro_daeguen.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Earlybro_DaeGuen on 2017-11-06.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityManager am = ActivityManager.getInstance();
    private GoogleMap mMap;
    String shopAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        am.addActivity(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        shopAddress = intent.getStringExtra("Adress");

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Geocoder geocoder = new Geocoder(this);

        try {

            List<Address> listAddress = geocoder.getFromLocationName(shopAddress,5);

            if(listAddress.size()>0){
                Address address = listAddress.get(0);
                double lat = address.getLatitude();
                double lng = address.getLongitude();
                LatLng position = new LatLng(lat, lng);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(position);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,18));
                mMap.addMarker(markerOptions);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}