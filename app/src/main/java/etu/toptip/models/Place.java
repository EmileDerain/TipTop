package etu.demoihm.maps.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.Observable;

import etu.demoihm.maps.R;

public class Place {

    private String name;
    private Integer quantiy;
    private String type;
    private Date date;
    private Integer image;
    private String localisation; // pour les produits de la Boutique
    private String description;
    private LatLng positiongps;


    public Place() {
        image = R.drawable.img;
    }

    // constructeur pour la Listvente, ListBoutique, ListReservation

    public Place(String name, String type, Date date, Integer image, String localisation, String description, LatLng positiongps) {
        this.name = name;
        this.date = date;
        this.image = image;
        this.type = type ;
        this.localisation = localisation;
        this.description = description;
        this.positiongps=positiongps;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantiy() {
        return quantiy;
    }

    public String getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public Integer getImage() {
        return image;
    }

    public String getLocalisation() {
        return localisation;
    }

    public String getDescription() {
        return description;
    }

    public LatLng getPositiongps() {
        return positiongps;
    }
}
