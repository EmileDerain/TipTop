package etu.toptip.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;


public class Place {

    private String name;
    private String type;
    private Date date;
    private Integer image;
    private String localisation;
    private String description;
    private LatLng positiongps;




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
