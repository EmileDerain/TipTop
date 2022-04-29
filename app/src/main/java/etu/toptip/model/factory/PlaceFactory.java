package etu.toptip.model.factory;

import etu.toptip.model.Place;

public abstract class PlaceFactory {

    public abstract Place build(String name, int type, String image, String localisation, String description) throws Throwable ;
}
