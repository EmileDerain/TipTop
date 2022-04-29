package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class Bakery  extends Place {
    public Bakery (String name, String image, String localisation, String description) {
        super(name, 5,image, localisation, description);
    }
    protected Bakery (Parcel in) {
        super(in);
    }
}
