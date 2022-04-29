package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class Hypermarket extends Place {
    public Hypermarket(String name, String image, String localisation, String description) {
        super(name, 1, image, localisation, description);
    }

    protected Hypermarket(Parcel in) {
        super(in);
    }
}