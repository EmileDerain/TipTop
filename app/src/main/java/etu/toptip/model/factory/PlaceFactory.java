package etu.toptip.model.factory;

import etu.toptip.model.Place;

public class PlaceFactory {
    public static final int RESTAURANT =0 ;
    public static final int SUPERMARKET =1 ;
    public static final int GROCERY =2 ;
    public static final int BUTCHERY =3 ;

    public static Place build(String name, int type, String date, Integer image, String localisation, String description) throws Throwable {
            switch (type){
                case RESTAURANT: return new Restaurant(name,date,image,localisation,description) ;
                case SUPERMARKET: return new Supermarket(name,date,image,localisation,description) ;
                case GROCERY: return new Grocery(name,date,image,localisation,description) ;
                case BUTCHERY: return new Butchery(name,date,image,localisation,description) ;
                default: throw new Throwable("not made");
            }
    }

}
