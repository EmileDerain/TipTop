package etu.toptip.controller;

import etu.toptip.model.User;
import etu.toptip.view.ILoginView;

public class LoginController implements ILoginController {
    ILoginView loginView;
    public LoginController(ILoginView loginView) {
        this.loginView = loginView;
    }
    @Override
    public int OnLogin(String email, String password) {
        User user = new User(email,password);
        int loginCode = user.isValid();
        if(loginCode == 0)
        {
            loginView.OnLoginError("Veuillez rentrer un e-mail");
        }else  if (loginCode == 1){
            loginView.OnLoginError("Veuillez rentrer un e-mail valide (avec un @)");
        } else  if (loginCode == 2)
        {
            loginView.OnLoginError("Veuillez rentrer un mot de passe d'une longueur > 6");
        }else  if(loginCode == 3){
            loginView.OnLoginError("Veuillez rentrer un mot de passe d'une longueur > 6");
        }
        else {
//            loginView.OnLoginSuccess("Ok");
            return 1;
        }
        return -1;
    }
}
