package etu.toptip.activities;

import androidx.appcompat.app.AppCompatActivity;
import etu.toptip.R;
import etu.toptip.controller.ILoginController;
import etu.toptip.controller.LoginController;
import etu.toptip.fragments.ListFavorisFragment;
import etu.toptip.view.ILoginView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    EditText email,password;
    Button loginb;
    ILoginController loginPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText)findViewById(R.id.mdp);

        loginPresenter = new LoginController(this);

        Button login = (Button) findViewById(R.id.idBtnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int log = loginPresenter.OnLogin(email.getText().toString().trim(),password.getText().toString().trim());
                if (log==1) {Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(myIntent);}
            }

        });

        Button goRegister = (Button) findViewById(R.id.idBtnGoRegister);
        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), SignupActivity.class);
                startActivity(myIntent);
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