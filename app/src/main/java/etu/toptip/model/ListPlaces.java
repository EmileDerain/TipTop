package etu.toptip.model;

import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import etu.toptip.R;
import etu.toptip.model.Place;
import etu.toptip.model.factory.PlaceFactory;


public class ListPlaces {

    public static ArrayList<Place> listPlaces = new ArrayList<>();

    public ListPlaces() throws Throwable {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        listPlaces.clear();
        try {
            listPlaces.add(PlaceFactory.build("Casino", 0, "2022-03-26", R.drawable.img, "2255 route des dolines valbonne", "Super promo tout à 50 %"));
            listPlaces.add(PlaceFactory.build("Carrefour", 0, "2022-03-26", R.drawable.carrefour, "Nice", "Super promo tout à 50 %"));
            listPlaces.add(PlaceFactory.build("Lidl", 0, "2022-03-26", R.drawable.lidl, "Cannes", "Super promo tout à 50 %"));
            listPlaces.add(PlaceFactory.build("Boucherie", 1, "2022-03-26", R.drawable.bouch, "Antibes", "1kg de viande gratuit !!!"));
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
