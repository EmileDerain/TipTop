package etu.toptip.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import etu.toptip.R;
import etu.toptip.models.ListPlaces;
import etu.toptip.models.Place;

public class AddBPActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;
    ListPlaces listPlaces = new ListPlaces();
    ArrayList<String> infos = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbp);

        Button addBP = (Button) findViewById(R.id.BtnAjouterBP);
        addBP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText name = (EditText) findViewById(R.id.nameResto);
                String nameText = name.getText().toString();
                infos.add(nameText);

                EditText adresse = (EditText) findViewById(R.id.AdresseResto);
                String adresseText = name.getText().toString();
                infos.add(adresseText);


                EditText description = (EditText) findViewById(R.id.DescriptionResto);
                String descriptionText = name.getText().toString();
                infos.add(descriptionText);

                EditText expiration = (EditText) findViewById(R.id.ExpirationResto);
                String expirationText = name.getText().toString();
                infos.add(expirationText);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                Spinner type = (Spinner) findViewById(R.id.typeResto);
                String typeText = type.getSelectedItem().toString();
                infos.add(typeText);

                try{
                    listPlaces.getPlaces().add(new Place(infos.get(0),infos.get(4),format.parse("2022-03-26"),1,infos.get(1),infos.get(2),null));

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(myIntent);
            }

        });

        Button cam = (Button) findViewById(R.id.BCamera);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), CameraActivity.class);
                startActivity(myIntent);
            }
        });

        Button BSelectImage = (Button) findViewById(R.id.BSelectImage);
        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }

    void imageChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityIfNeeded(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public ListPlaces getListPlaces(){
        return listPlaces ;
    }
}
