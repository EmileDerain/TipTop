package etu.toptip.activities;

import static etu.toptip.activities.NotificationActivity.CHANNEL_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import etu.toptip.R;
import etu.toptip.fragments.CameraFragment;
import etu.toptip.model.BonPlan;
import etu.toptip.model.ListPlaces;
import etu.toptip.model.factory.FactoryManager;

public class AddBonPlanActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;
    private int notifID = 0;
    ListPlaces listPlaces = new ListPlaces();
    ArrayList<String> infos = new ArrayList<>();
    ImageView imageView;
    CameraFragment cameraFragment;
    ImageView IVPreviewImage;


    public AddBonPlanActivity() throws Throwable {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bon_plan);

        Button addBP = (Button) findViewById(R.id.BtnAjouterBP);
        addBP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                EditText description = (EditText) findViewById(R.id.description);
                String descriptionText = description.getText().toString();

                EditText expiration = (EditText) findViewById(R.id.description);
                String expirationText = expiration.getText().toString();

                BonPlan plan = new BonPlan(expirationText,null,0,0,descriptionText);
                //a ajouter le lieu et à regler l'image
                Log.d("test",plan.getDescription());
                Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(myIntent);
            }
        });

        Button cam = (Button) findViewById(R.id.BCamera);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 101);
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
            if (requestCode == 101) {
                Bitmap bitmap = (Bitmap) (data != null ? data.getExtras().get("data") : null);
                IVPreviewImage.setImageBitmap(bitmap);
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
