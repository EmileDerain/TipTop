package etu.toptip.activities;

import static etu.toptip.activities.NotificationActivity.CHANNEL_ID;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import etu.toptip.R;
import etu.toptip.fragments.CameraFragment;
import etu.toptip.model.Place;
import etu.toptip.model.ListPlaces;
import etu.toptip.model.factory.PlaceFactory;


public class AddBPActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;
    private int notifID = 0;
    ListPlaces listPlaces = new ListPlaces();
    ArrayList<String> infos = new ArrayList<>();
    ImageView imageView;
    CameraFragment cameraFragment;

    public AddBPActivity() throws Throwable {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbp);

        /**imageView = findViewById(R.id.click_imageBP);
        imageView.setImageBitmap(cameraFragment.getBitmap());

        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();*/

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

                sendNotificationChannel(nameText,descriptionText,CHANNEL_ID,NotificationCompat.PRIORITY_DEFAULT, null);

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

    private void sendNotificationChannel(String title, String message, String channelId, int priority, Bitmap bitmap) {
        NotificationCompat.Builder notif = new NotificationCompat.Builder(getApplicationContext(), channelId) //création de la notif
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(bitmap)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(priority)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null));
        NotificationActivity.getNotificationManager().notify(notifID, notif.build());
    }
}
