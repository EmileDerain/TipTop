package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class Grocery extends Place {
    public Grocery(String name, String image, String ville, String codeP, String adresse) {
        super(name, 3,image,ville, codeP, adresse);
    }

    protected Grocery(Parcel in) {
        super(in);
    }

    @Override
    public String toString() {
        return "epecerie";
    }
}
