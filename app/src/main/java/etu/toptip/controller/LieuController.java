package etu.toptip.controller;

import etu.toptip.fragments.AddPlaceFragment;
import etu.toptip.model.LieuModel;

public class LieuController {
    private LieuModel lieuModel;
    private AddPlaceFragment lieuActivity;  //View  ???

    public LieuController(AddPlaceFragment lieuActivity) {
        this.lieuModel = new LieuModel(this);
        this.lieuActivity = lieuActivity;
    }

    public void OnLieu(String nomLieu, String adresse, String ville, String codePostal, int type) {     //Envoie model
        this.lieuModel.LieuModel2(nomLieu, adresse, ville, codePostal, type);
    }

    public void OnLieuError2(String error,Boolean connect) {   //Recois du model et envoie au view
        this.lieuActivity.showError(error, connect);
    }



}
