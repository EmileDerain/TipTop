package etu.toptip.controller;

import etu.toptip.model.User;
import etu.toptip.view.ILoginView;

public class SignUpController implements ISignUpController{
    ILoginView loginView;
    public SignUpController(ILoginView loginView) {
        this.loginView = loginView;
    }
    @Override
    public int OnSignUp(String username, String email, String password, String confirmPassword) {
        User user = new User(username,email,password,confirmPassword);
        int signUpCode = user.isValidSignUp();
        switch (signUpCode) {
            case 0:
                loginView.OnLoginError("Veuillez rentrer un e-mail");
                break;
            case 1:
                loginView.OnLoginError("Veuillez rentrer un e-mail valide");
                break;
            case 2:
                loginView.OnLoginError("Veuillez rentrer un mot de passe");
                break;
            case 3:
                loginView.OnLoginError("Veuillez rentrer un mot de passe d'une longueur > 6");
                break;
            case 4:
                loginView.OnLoginError("Veuillez rentrer un nom d'utilisateur");
                break;
            case 5:
                loginView.OnLoginError("Veuillez confirmer le mot de passe");
                break;
            case 6:
                loginView.OnLoginError("Les deux mots de passes ne sont pas identiques");
                break;
            default:
//            loginView.OnLoginSuccess("Ok");
                return 1;
        }
        return -1;
    }
}
