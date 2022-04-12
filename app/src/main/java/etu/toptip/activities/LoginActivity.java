package etu.toptip.activities;

import androidx.appcompat.app.AppCompatActivity;
import etu.toptip.R;
import etu.toptip.fragments.ListFavorisFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button) findViewById(R.id.idBtnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(myIntent);
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
}