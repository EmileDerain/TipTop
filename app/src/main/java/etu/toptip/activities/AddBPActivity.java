package etu.toptip.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import etu.toptip.R;
import etu.toptip.model.Place;
import etu.toptip.model.ListPlaces;
import etu.toptip.model.factory.PlaceFactory;


public class AddBPActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;
    ListPlaces listPlaces = new ListPlaces();
    ArrayList<String> infos = new ArrayList<>();

    public AddBPActivity() throws Throwable {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbp);

        Button addBP = (Button) findViewById(R.id.BtnAjouterBP);
        addBP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText name = (EditText) findViewById(R.id.nameResto);
                String nameText = name.getText().toString();

                EditText adresse = (EditText) findViewById(R.id.AdresseResto);
                String adresseText = adresse.getText().toString();

                EditText description = (EditText) findViewById(R.id.DescriptionResto);
                String descriptionText = description.getText().toString();

                EditText expiration = (EditText) findViewById(R.id.ExpirationResto);
                String expirationText = name.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                String date = sdf.format(c.getTime());

                Spinner typeSpinner = (Spinner) findViewById(R.id.typeResto);
                int type = typeSpinner.getSelectedItemPosition();


                try{
                    listPlaces.getPlaces().add(PlaceFactory.build(nameText,type,date,null,adresseText,descriptionText));

                } catch (Throwable e) {
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
