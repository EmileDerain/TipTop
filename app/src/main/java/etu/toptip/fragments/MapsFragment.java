package etu.toptip.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import etu.toptip.R;
import etu.toptip.models.ListPlaces;
import etu.toptip.models.Place;


public class MapsFragment extends Fragment implements IGPS{
    private GPSFragment gpsFragment ;

    GoogleMap map;

    public ListPlaces places = new ListPlaces();

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

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
            int i=0;
            for(Place place : places.getPlaces()){
                googleMap.addMarker(new MarkerOptions().position(place.getPositiongps()).title(place.getName()).zIndex(i));
                i++;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
            gpsFragment = (GPSFragment) getParentFragmentManager().findFragmentById(R.id.map);
            if(gpsFragment==null){
                gpsFragment = new GPSFragment(this);
                FragmentTransaction gpsTransaction =getParentFragmentManager().beginTransaction();
                gpsTransaction.replace(R.id.map,gpsFragment);
                gpsTransaction.addToBackStack(null);
                gpsTransaction.commit();
            }

            NavigationFragment  navigationFragment =  (NavigationFragment) getParentFragmentManager().findFragmentById(R.id.navigation_maps);
            if(navigationFragment==null){
                navigationFragment = new NavigationFragment();
                FragmentTransaction nvgTransaction =getParentFragmentManager().beginTransaction();
                nvgTransaction.replace(R.id.navigation_maps,navigationFragment);
                nvgTransaction.addToBackStack(null);
                nvgTransaction.commit();
            }
        }

    }

    @Override
    public void moveCamera() {
        try{
            gpsFragment.setPLaceName("ville "+gpsFragment.getPlaceName());
        }catch (IOException e){
            gpsFragment.setPLaceName("Ville inconnue ");
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(gpsFragment.getPosition(),15f));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode , String[] permissions , int[] grantedResults){


    }
}