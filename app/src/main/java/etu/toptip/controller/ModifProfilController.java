package etu.toptip.controller;

import etu.toptip.activities.LoginActivity;
import etu.toptip.model.ModifPasswordModel;
import etu.toptip.model.ModifProfilModel;

public class ModifProfilController {

    private ModifProfilModel ModifProfilModel;
//    private ModifPasswordActivity modifPasswordActivity;  //View  ???

    public ModifProfilController(LoginActivity loginActivity) {
        this.ModifProfilModel = new ModifProfilModel(this);
//        this.modifPasswordActivity = loginActivity;
    }

    public void OnModifProfil(String userName) {     //Envoie model
        this.ModifProfilModel.ModifPasswordModel2(userName);
    }

    public void OnModifProfilError2(String error,Boolean connect) {   //Recois du model et envoie au view
//        this.loginActivity.showError(error, connect);
    }



}
