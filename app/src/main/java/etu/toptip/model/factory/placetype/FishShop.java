package etu.toptip.model.factory.placetype;

import android.os.Parcel;

import etu.toptip.model.Place;

public class FishShop  extends Place {
    public FishShop (String name,  String image, String localisation, String description) {
        super(name, 6, image, localisation, description);
    }
    protected FishShop (Parcel in) {
        super(in);
    }
}