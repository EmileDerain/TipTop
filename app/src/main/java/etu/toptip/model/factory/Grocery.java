package etu.toptip.model.factory;

import android.os.Parcel;

import java.util.Date;

import etu.toptip.model.Place;

public class Grocery extends Place {
    public Grocery(String name, String date, Integer image, String localisation, String description) {
        super(name, 2, date, image, localisation, description);
    }

    protected Grocery(Parcel in) {
        super(in);
    }
}
