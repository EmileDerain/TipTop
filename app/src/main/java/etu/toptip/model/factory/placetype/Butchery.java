package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class Butchery extends Place {
    public Butchery(String name,   String image, String localisation, String description) {
        super(name, 4, image, localisation, description);
    }

    protected Butchery(Parcel in) {
        super(in);
    }
}
