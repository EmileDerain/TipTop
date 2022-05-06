package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class FishShop  extends Place {
    public FishShop (String name, String image, String ville, String codeP, String adresse) {
        super(name, 6, image, ville, codeP, adresse);
    }
    protected FishShop (Parcel in) {
        super(in);
    }

    @Override
    public String toString() {
        return "poissonerie";
    }
}