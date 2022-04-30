package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class Butchery extends Place {
    public Butchery(String name, String image, String ville, String codeP, String adresse) {
        super(name, 4, image,ville, codeP, adresse);
    }

    protected Butchery(Parcel in) {
        super(in);
    }
}
