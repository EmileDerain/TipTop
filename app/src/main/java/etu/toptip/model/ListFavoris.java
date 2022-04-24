package etu.toptip.model;

import java.util.ArrayList;

import etu.toptip.model.Place;


public class ListFavoris {

    public static ArrayList<Place> listFavoris = new ArrayList<>();

    public ListFavoris() {
    }

    public ArrayList<Place> getFavoris(){
        return listFavoris ;
    }
}
