package etu.toptip.fragments;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

import androidx.fragment.app.Fragment;

import etu.toptip.R;

public class navActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         getSupportActionBar().hide();
        setContentView(R.layout.fragement_navigation);

        bottomNavigationView= findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body,new GPSFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.map);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch(item.getItemId()){

                    case R.id.map:
                        fragment = new GPSFragment();
                        break;


                }
                assert fragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.body,fragment).commit();
                return true;
            }
        });

    }


}