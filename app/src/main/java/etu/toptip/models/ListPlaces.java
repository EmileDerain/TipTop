package etu.toptip.models;

import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import etu.toptip.R;


public class ListPlaces {

    public static ArrayList<Place> listPlaces = new ArrayList<>();

    public ListPlaces() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        listPlaces.clear();
        try {
            listPlaces.add(new Place("Casino", "Supermarché", format.parse("2022-03-26"), R.drawable.img, "2255 route des dolines valbonne", "Super promo tout à 50 %", new LatLng(43.583096, 7.124432)));
            listPlaces.add(new Place("Carrefour", "Supermarché", format.parse("2022-03-26"), R.drawable.carrefour, "Nice", "Super promo tout à 50 %", new LatLng(43.580111, 7.083920)));
            listPlaces.add(new Place("Lidl", "Supermarché", format.parse("2022-03-26"), R.drawable.lidl, "Cannes", "Super promo tout à 50 %", new LatLng(43.569384, 7.082547)));
            listPlaces.add(new Place("Boucherie", "Boucherie", format.parse("2022-03-26"), R.drawable.bouch, "Antibes", "1kg de viande gratuit !!!", new LatLng(43.588567, 7.094391)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Place> getPlaces(){
        return listPlaces ;
    }

    public Place getPlaceByName(String name){
        for (Place place : listPlaces
             ) {
            if (place.getName()==name) return place;
        }
        return null;
    }

}
