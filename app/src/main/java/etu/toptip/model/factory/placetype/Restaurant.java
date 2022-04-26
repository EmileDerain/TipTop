package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class Restaurant extends Place {
    public Restaurant(String name, String date, Integer image, String localisation, String description) {
        super(name,2, date, image, localisation, description);
    }

    protected Restaurant(Parcel in) {
        super(in);
    }
}
