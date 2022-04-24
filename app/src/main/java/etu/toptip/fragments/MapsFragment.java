package etu.toptip.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import etu.toptip.R;
import etu.toptip.models.ListPlaces;
import etu.toptip.models.Place;


public class MapsFragment extends Fragment  implements OnMapReadyCallback , LocationListener,
        GoogleMap.OnMarkerClickListener  {
    GoogleMap map;

    private LocationManager lm;
    private static final int PERMS_CALL_ID = 1234;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private LatLng googleLocation;
    private Geocoder coder;
    private boolean first =true;
    SearchView searchView;
    private boolean sumbitText =false;

    public ListPlaces places = new ListPlaces();

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.6154778, 7.0722062), 12.0f));

            int i=0;
            for(Place place : places.getPlaces()){
                try {
                    Address location = coder.getFromLocationName(place.getLocalisation(), 5).get(0);
                    LatLng l = new LatLng(location.getLatitude(), location.getLongitude() );
                    googleMap.addMarker(new MarkerOptions().position(l).title(place.getName()).zIndex(i));
                    i++;
                }
                catch (Exception e){
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        coder = new Geocoder(getActivity(), Locale.getDefault());

        searchView = view.findViewById(R.id.idSearchView);


        mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));


        checkPermission();
        mapFragment.getMapAsync(this);


        View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 40, 300);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                sumbitText = true;
                List<Address> addressList = null;
                if (location != null || location.equals("")) {
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                        System.out.println(addressList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                    } else {
                        Toast.makeText(getActivity(), "l'adresse est introuvable ", Toast.LENGTH_SHORT).show();  // Check whether the fields are not blank
                    }
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view ;
    }


        @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.PERMS_CALL_ID) {
            checkPermission();
        }
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
            }, this.PERMS_CALL_ID);
            return;
        }
        lm =(LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            lm.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 10000, 0, this);
        }
        if (lm.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            lm.requestLocationUpdates(
                    LocationManager.PASSIVE_PROVIDER, 10000, 0, this);
        }
        if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            lm.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 10000, 0, this);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        if (googleMap != null) {
            googleLocation = new LatLng(latitude, longitude);
            googleMap.setOnMarkerClickListener( this);
            if(sumbitText==false && first==true ){
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(googleLocation, 15));
            }
            first =false;
        }
    }

    @Override
    public void on
}