package etu.toptip.controller;

import etu.toptip.IListner;
import etu.toptip.model.ListPlaces;
import etu.toptip.model.Place;
;


import etu.toptip.view.AddBPView;

public class AddBPController implements IListner {
    private AddBPView view;
    private Place model;
    public ListPlaces listPlaces;

    public AddBPController(AddBPView view, Place model, ListPlaces list) {

    }


    @Override
    public void OnClickPlace(Place place) {

    }
}
