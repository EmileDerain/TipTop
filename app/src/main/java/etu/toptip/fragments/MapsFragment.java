package etu.toptip.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;


import android.content.pm.PackageManager;
import android.os.Bundle;

import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.Objects;

import etu.toptip.R;
import etu.toptip.activities.MainActivity;
import etu.toptip.models.ListPlaces;
import etu.toptip.models.Place;


public class MapsFragment extends FragmentActivity implements OnMapReadyCallback,IGPS {

    GoogleMap map;
    private GPSFragment gpsFragment;


    public ListPlaces places = new ListPlaces();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

            gpsFragment = new GPSFragment(this);
            FragmentTransaction gpsTransaction = getSupportFragmentManager().beginTransaction();
            gpsTransaction.replace(R.id.map, gpsFragment);
            gpsTransaction.addToBackStack(null);
            gpsTransaction.commit();
        NavigationFragment navigationFragment = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_maps);
        if (navigationFragment == null) {
            navigationFragment = new NavigationFragment();
            FragmentTransaction nvgTransaction = getSupportFragmentManager().beginTransaction();
            nvgTransaction.replace(R.id.navigation_maps, navigationFragment);
            nvgTransaction.addToBackStack(null);
            nvgTransaction.commit();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
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
        int i = 0;
        for (Place place : places.getPlaces()) {
            googleMap.addMarker(new MarkerOptions().position(place.getPositiongps()).title(place.getName()).zIndex(i));
            i++;
        }
    }


    @Override
    public void moveCamera() {
        try {
            gpsFragment.setPLaceName("ville " + gpsFragment.getPlaceName());
        } catch (IOException e) {
            gpsFragment.setPLaceName("Ville inconnue ");
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(gpsFragment.getPosition(), 15f));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "FINE_LOCATION granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            break;
        }
    }
}