package etu.toptip.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import etu.toptip.R;
import etu.toptip.controller.ILoginController;
import etu.toptip.controller.LoginController;
import etu.toptip.view.ILoginView;

public class SignupActivity extends AppCompatActivity implements ILoginView {
    EditText email,password;
    Button loginb;
    ILoginController loginPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = (EditText) findViewById(R.id.Email);
        password = (EditText)findViewById(R.id.idEdtPassword);

        loginPresenter = new LoginController(this);

        Button next = (Button) findViewById(R.id.idBtnRegister);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int log = loginPresenter.OnLogin(email.getText().toString().trim(),password.getText().toString().trim());
                if (log==1) {Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(myIntent);}
            }

        });

    }

    @Override
    public void OnLoginSuccess(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void OnLoginError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}