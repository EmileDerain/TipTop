package etu.toptip.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;


public class Place implements Parcelable {

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

    protected Place(Parcel in) {
        name = in.readString();
        type = in.readString();
        if (in.readByte() == 0) {
            image = null;
        } else {
            image = in.readInt();
        }
        localisation = in.readString();
        description = in.readString();
        positiongps = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(type);
        if (image == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(image);
        }
        parcel.writeString(localisation);
        parcel.writeString(description);
        parcel.writeParcelable(positiongps, i);
    }
}
