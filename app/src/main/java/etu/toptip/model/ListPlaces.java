package etu.toptip.model;

import static java.lang.Integer.parseInt;

import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import etu.toptip.R;
import etu.toptip.model.Place;
import etu.toptip.model.factory.FactoryManager;
import etu.toptip.model.factory.PlaceFactory;


public class ListPlaces {

    public static ArrayList<Place> listPlaces = new ArrayList<>();

    public ListPlaces() throws Throwable {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        listPlaces.clear();
        try {
            listPlaces.add(FactoryManager.build("Casino", 0,  "https://static.apidae-tourisme.com/filestore/objets-touristiques/images/182/165/1549750-diaporama.jpg","valbonne","06560", "2255 route des Dolines"));
            listPlaces.add(FactoryManager.build("Carrefour", 0,  "https://upload.wikimedia.org/wikipedia/fr/thumb/3/3b/Logo_Carrefour.svg/1200px-Logo_Carrefour.svg.png", "Nice", "",""));
            listPlaces.add(FactoryManager.build("Lidl", 0,  "https://www.lsa-conso.fr/mediatheque/0/1/2/000162210_5.jpg", "Cannes", "",""));
            listPlaces.add(FactoryManager.build("Boucherie", 1, "https://www.gastronomiac.com/wp/wp-content/uploads/2021/07/Boucherie-charcuterie.jpg", "Antibes", "",""));
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
