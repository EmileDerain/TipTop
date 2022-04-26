package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class Grocery extends Place {
    public Grocery(String name, String date, Integer image, String localisation, String description) {
        super(name, 3, date, image, localisation, description);
    }

    protected Grocery(Parcel in) {
        super(in);
    }
}
