package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class Supermarket extends Place {
    public Supermarket(String name, String date, String image, String localisation, String description) {
        super(name, 0,date, image, localisation, description);
    }
    protected Supermarket(Parcel in) {
        super(in);
    }
}
