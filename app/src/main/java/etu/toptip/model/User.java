package etu.toptip.model;

import android.text.TextUtils;
import android.util.Patterns;

public class User implements IUser{
    private  String email,password;
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    @Override
    public String getEmail() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public int isValid() {
        // 0. Check for Email Empty
        // 1. Check for Email Match pattern
        // 2. Check for Password > 6
        if(TextUtils.isEmpty(getEmail()))
            return  0;
        else if(!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches())
            return  1;
        else if(TextUtils.isEmpty(getPassword()))
            return 2;
        else if(getPassword().length()<=6)
            return 3;
        else
            return -1;
    }
}
