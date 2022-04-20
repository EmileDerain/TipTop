package etu.toptip.models;

import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import etu.toptip.R;


public class ListFavoris {

    public static ArrayList<Place> listFavoris = new ArrayList<>();

    public ListFavoris() {
    }

    public ArrayList<Place> getFavoris(){
        return listFavoris ;
    }
}
