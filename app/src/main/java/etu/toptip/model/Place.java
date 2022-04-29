package etu.toptip.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class Place implements Parcelable {

    private String name;
    private int type;
    private String image;
    private String localisation;
    private String description;

    public Place(String name, int type, String image, String localisation, String description) {
        this.name = name;
        this.image = image;
        this.type = type;
        this.localisation = localisation;
        this.description = description;
    }

    protected Place(Parcel in) {
        name = in.readString();
        type = in.readInt();
        if (in.readByte() == 0) {
            image = null;
        } else {
            image = in.readString();
        }
        localisation = in.readString();
        description = in.readString();
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

    public int getType() {
        return type;
    }


    public String getImage() {
        return image;
    }

    public String getLocalisation() {
        return localisation;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(type);
        if (image == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeString(image);
        }
        parcel.writeString(localisation);
        parcel.writeString(description);
    }
}
