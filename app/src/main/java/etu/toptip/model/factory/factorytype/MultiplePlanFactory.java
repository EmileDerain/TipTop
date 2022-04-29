package etu.toptip.model.factory.factorytype;

import etu.toptip.model.Place;
import etu.toptip.model.factory.PlaceFactory;
import etu.toptip.model.factory.placetype.Hypermarket;
import etu.toptip.model.factory.placetype.Restaurant;
import etu.toptip.model.factory.placetype.Supermarket;

public class MultiplePlanFactory extends PlaceFactory {
    public static final int SUPERMARKET =0 ;
    public static final int HYPERMARKETS =1 ;
    public static final int RESTAURANT = 2 ;


    @Override
    public Place build(String name, int type, String date, String image, String localisation, String description) throws Throwable {
        switch (type){
            case RESTAURANT: return new Restaurant(name,date,image,localisation,description) ;
            case SUPERMARKET: return new Supermarket(name,date,image,localisation,description) ;
            case HYPERMARKETS: return new Hypermarket(name,date,image,localisation,description) ;
            default: throw new Throwable("not made");
        }
    }
}
