package etu.toptip.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import etu.toptip.R;
import etu.toptip.model.ListPlaces;
import etu.toptip.model.Place;


public class MapsFragment extends Fragment  implements OnMapReadyCallback , LocationListener,
        GoogleMap.OnMarkerClickListener , FragmentChangeListener {

    GoogleMap map;
    private LocationManager locationManager;
    private static final int PERMS_CALL_ID = 1234;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private LatLng googleLocation;
    private Geocoder codergeo;
    private boolean first =true;
    SearchView searchView;
    private boolean textSubmitted =false;

    public ListPlaces places = new ListPlaces();

    public MapsFragment() throws Throwable {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        codergeo = new Geocoder(getActivity(), Locale.getDefault());

        searchView = view.findViewById(R.id.idSearchView);

        mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));

        permissionGPSCheck();
        mapFragment.getMapAsync(this);

        View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 40, 300);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                String locationLieu = searchView.getQuery().toString();
                textSubmitted = true;
                List<Address> addressList = null;
                if (locationLieu != null || locationLieu.equals("")) {
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        addressList = geocoder.getFromLocationName(locationLieu, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                    } else {
                        Toast.makeText(getActivity(), "L'adresse entrée est introuvable  ", Toast.LENGTH_SHORT).show();  // Check whether the fields are not blank
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
            mapFragment.getMapAsync(this);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        map = googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        int i=0;
        for(Place place : places.getPlaces()){
            try {
                Address location = codergeo.getFromLocationName(place.getLocalisation(), 5).get(0);
                LatLng l = new LatLng(location.getLatitude(), location.getLongitude() );
                MarkerOptions m = new MarkerOptions().position(l).title(place.getName()).zIndex(i);
                googleMap.addMarker(m);
                i++;
            }
            catch (Exception e){
            }
        }
    }

    /**
     * gérer les permissions du gps
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.PERMS_CALL_ID) {
            permissionGPSCheck();
        }
    }
    private void permissionGPSCheck() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
            }, this.PERMS_CALL_ID);
            return;
        }
        locationManager =(LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 10000, 0, this);
        }
        if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            locationManager.requestLocationUpdates(
                    LocationManager.PASSIVE_PROVIDER, 10000, 0, this);
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 10000, 0, this);
        }
    }

    /**
     * fonction permettant de se diriger vers votre location
     * @param location
     */
    @Override
    public void onLocationChanged(@NonNull Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        if (googleMap != null) {
            googleLocation = new LatLng(latitude, longitude);
            googleMap.setOnMarkerClickListener( this);
            if(textSubmitted ==false && first==true ){
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(googleLocation, 11.0f));
            }
            first =false;
        }
    }


    /**
     * fonction permettant si on clique sur un marker on affiche le nom du bon plan
     * et on cliquant sur le nom on se dirige vers le détails
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        marker.showInfoWindow();
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Fragment placeDetails = new PlaceDetails();
                Bundle bundle = new Bundle();
                Place place = places.getPlaces().get((int) marker.getZIndex());
                bundle.putParcelable("place", (Parcelable)place);
                placeDetails.setArguments(bundle);
                replaceFragment(placeDetails);
            }
        });
        return true;
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

}