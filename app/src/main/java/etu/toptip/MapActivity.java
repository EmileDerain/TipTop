package etu.toptip;

import androidx.annotation.NonNull;

import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import etu.toptip.models.ListPlaces;
import etu.toptip.models.Place;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;

    public ListPlaces places = new ListPlaces();

    public MapActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment)  getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.6154778, 7.0722062), 12.0f));

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title("Ajouter un lieu ici").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin2)));
                marker.showInfoWindow();
            }
        });
        int i=0;
        for(Place place : places.getPlaces()){
            googleMap.addMarker(new MarkerOptions().position(place.getPositiongps()).title(place.getName()).zIndex(i));
            i++;
        }
    }





}