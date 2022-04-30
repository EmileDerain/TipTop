package etu.toptip.activities;

import static etu.toptip.activities.NotificationActivity.CHANNEL_ID;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import etu.toptip.R;
import etu.toptip.fragments.CameraFragment;
import etu.toptip.model.Place;
import etu.toptip.model.ListPlaces;
import etu.toptip.model.factory.FactoryManager;
import etu.toptip.model.factory.PlaceFactory;


public class AddPlaceActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;
    private int notifID = 0;
    ListPlaces listPlaces = new ListPlaces();
    ArrayList<String> infos = new ArrayList<>();
    ImageView imageView;
    CameraFragment cameraFragment;
    ImageView IVPreviewImage;

    public AddPlaceActivity() throws Throwable {
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

                EditText ville = (EditText) findViewById(R.id.VilleResto);
                String villeText = ville.getText().toString();

                EditText code = (EditText) findViewById(R.id.CodeP);
                String codeText = ville.getText().toString();


                EditText adresse = (EditText) findViewById(R.id.AdresseResto);
                String adresseText = adresse.getText().toString();


                Spinner typeSpinner = (Spinner) findViewById(R.id.typeResto);
                int type = typeSpinner.getSelectedItemPosition();

                Place place = new Place(nameText,type,"",villeText,codeText,adresseText);
          //      sendNotificationChannel(nameText,"",CHANNEL_ID,NotificationCompat.PRIORITY_DEFAULT, null);

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
        IVPreviewImage = findViewById(R.id.IVPreviewImage);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                    System.out.println("j'ai rentré");

                }
            }
        }
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
