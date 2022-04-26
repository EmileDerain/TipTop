package etu.toptip.model.factory;

import etu.toptip.model.Place;
import etu.toptip.model.factory.factorytype.MultiplePlanFactory;
import etu.toptip.model.factory.factorytype.SpecificPlanFactory;

public class FactoryManager {

    public static Place build(String name, int type, String date, Integer image, String localisation, String description) throws Throwable {
        if(type<=3){
            MultiplePlanFactory multiplePlanFactory = new MultiplePlanFactory();
            return multiplePlanFactory.build(name,type,date,image,localisation,description);
        }
        else {
            SpecificPlanFactory specificPlanFactory = new SpecificPlanFactory();
            return specificPlanFactory.build(name,type,date,image,localisation,description);
        }
    }
}
