package etu.toptip.controller;

import etu.toptip.activities.LoginActivity;
import etu.toptip.model.LoginModel;
import etu.toptip.model.ModifPasswordModel;

public class ModifPasswordController {

    private ModifPasswordModel modifPasswordModel;
//    private ModifPasswordActivity modifPasswordActivity;  //View  ???

    public ModifPasswordController(LoginActivity loginActivity) {
        this.modifPasswordModel = new ModifPasswordModel(this);
//        this.modifPasswordActivity = loginActivity;
    }

    public void OnModifPassword(String oldPassword, String newPassword) {     //Envoie model
        this.modifPasswordModel.ModifPasswordModel2(oldPassword, newPassword);
    }

    public void OnModifPasswordError2(String error,Boolean connect) {   //Recois du model et envoie au view
//        this.loginActivity.showError(error, connect);
    }


}
