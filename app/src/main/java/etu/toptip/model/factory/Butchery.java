package etu.toptip.model.factory;

import android.os.Parcel;

import java.util.Date;

import etu.toptip.model.Place;

public class Butchery extends Place {
    public Butchery(String name,  String date, Integer image, String localisation, String description) {
        super(name, 3, date, image, localisation, description);
    }

    protected Butchery(Parcel in) {
        super(in);
    }
}
