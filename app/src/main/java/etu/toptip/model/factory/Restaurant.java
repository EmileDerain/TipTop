package etu.toptip.model.factory;

import android.os.Parcel;

import java.util.Date;

import etu.toptip.model.Place;

public class Restaurant extends Place {
    public Restaurant(String name, String date, Integer image, String localisation, String description) {
        super(name,0, date, image, localisation, description);
    }

    protected Restaurant(Parcel in) {
        super(in);
    }
}
