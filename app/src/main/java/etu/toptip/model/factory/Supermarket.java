package etu.toptip.model.factory;

import android.os.Parcel;

import java.util.Date;

import etu.toptip.model.Place;

public class Supermarket extends Place {
    public Supermarket(String name, String date, Integer image, String localisation, String description) {
        super(name, 1, date, image, localisation, description);
    }

    protected Supermarket(Parcel in) {
        super(in);
    }
}
