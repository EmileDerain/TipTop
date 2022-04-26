package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class Hypermarket extends Place {
    public Hypermarket(String name, String date, Integer image, String localisation, String description) {
        super(name, 1, date, image, localisation, description);
    }

    protected Hypermarket(Parcel in) {
        super(in);
    }
}