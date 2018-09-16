package com.androidtown.blackout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private MapFragment mapFrag;

    private ImageButton btnBack;

    private ArrayList<Double> listLat;
    private ArrayList<Double> listLng;

    PolylineOptions polyline;

    GpsInfo gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        btnBack = findViewById(R.id.btnBack);
        gps = ((ResultActivity)ResultActivity.mContext).gps;

        listLng = new ArrayList<>();
        listLat = new ArrayList<>();

        polyline = new PolylineOptions();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        setList();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng home = new LatLng(37.581072, 126.920668);
        MarkerOptions marker = new MarkerOptions();


        marker.position(home).title("Home");

        mMap.addMarker(marker);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 15));

        polyline.geodesic(true);

        for(int i = 0; i < listLng.size(); i++){
            polyline.add(new LatLng(listLat.get(i), listLng.get(i)));
        }
        mMap.addPolyline(polyline);

        Log.e("@@@@@", String.valueOf(listLat.size()));
        Toast.makeText(MapsActivity.this, "몇번" + String.valueOf(listLat.size()), Toast.LENGTH_SHORT).show();


        return;

    }

    public void setList(){
        listLat.clear();
        listLng.clear();

        listLat = gps.setLatList();
        listLng = gps.setLngList();
    }


}