package etu.toptip.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import etu.toptip.R;
import etu.toptip.fragments.navActivity;


public class MainActivity extends AppCompatActivity  {
    private Button mBoutonDeguster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        mBoutonDeguster = findViewById(R.id.main_deguster);


        mBoutonDeguster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameActivityIntent = new Intent(getApplicationContext(), navActivity.class);
                startActivity(gameActivityIntent);
            }
        });



    }

   /* public void foodtruck(View view){
        Intent gameActivityIntent = new Intent(MainActivity.this, ConnexionActivity.class);
        startActivity(gameActivityIntent);
    }*/
}