package etu.toptip.controller;

import android.util.Log;

import etu.toptip.activities.LoginActivity;
import etu.toptip.activities.SignUpActivity;
import etu.toptip.model.LoginModel;
import etu.toptip.model.User;
import etu.toptip.view.ILoginView;

public class LoginController {

    private LoginModel loginModel;
    private LoginActivity loginActivity;  //View

    public LoginController(LoginActivity loginActivity) {
        this.loginModel = new LoginModel(this);
        this.loginActivity = loginActivity;
    }

    public void OnLogin(String email, String password) {     //Envoie model
        this.loginModel.LoginModel2(email, password);
    }

    public void OnLoginError2(String error,Boolean connect) {   //Recois du model et envoie au view
        this.loginActivity.showError(error, connect);
    }


}
