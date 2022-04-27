package etu.toptip.model;

import android.text.TextUtils;
import android.util.Patterns;

public class User implements IUser {
    private String username, email, password, confirmPassword;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public int isValid() {
        if (TextUtils.isEmpty(getEmail()))
            return 0;
        else if (!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches())
            return 1;
        else if (TextUtils.isEmpty(getPassword()))
            return 2;
        else if (getPassword().length() <= 6)
            return 3;
        else
            return -1;
    }

    @Override
    public int isValidSignUp() {
        if (TextUtils.isEmpty(getEmail()))
            return 0;
        else if (!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches())
            return 1;
        else if (TextUtils.isEmpty(getPassword()))
            return 2;
        else if (getPassword().length() <= 6)
            return 3;
        else if (TextUtils.isEmpty(getUsername()))
            return 4;
        else if (TextUtils.isEmpty(getConfirmPassword()))
            return 5;
        else if (!TextUtils.equals(getPassword(),getConfirmPassword()))
            return 6;
        else
            return -1;
    }


}
