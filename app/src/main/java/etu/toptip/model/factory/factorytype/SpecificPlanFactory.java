package etu.toptip.model.factory.factorytype;

import etu.toptip.model.Place;
import etu.toptip.model.factory.PlaceFactory;
import etu.toptip.model.factory.placetype.Bakery;
import etu.toptip.model.factory.placetype.Butchery;
import etu.toptip.model.factory.placetype.FishShop;
import etu.toptip.model.factory.placetype.Grocery;


public class SpecificPlanFactory  extends PlaceFactory {

    public static final int GROCERY = 3;
    public static final int BUTECHERY =4 ;
    public static final int BAKERY = 5 ;
    public static final int FISHSHOP = 6 ;


    @Override
    public Place build(String name, int type,  String image, String localisation, String description) throws Throwable {

            switch (type){
                case GROCERY: return new Grocery(name,image,localisation,description) ;
                case BUTECHERY: return new Butchery(name,image,localisation,description) ;
                case BAKERY: return new Bakery(name,image,localisation,description) ;
                case FISHSHOP: return new FishShop(name,image,localisation,description) ;
                default: throw new Throwable("not made");
            }
        }
}
