package etu.toptip.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import etu.toptip.R;
import etu.toptip.controller.NotificationsController;
import etu.toptip.fragments.CameraFragment;
import etu.toptip.fragments.ICameraPermission;
import etu.toptip.model.BonPlan;
import etu.toptip.helper.ListPlacesThread;


public class AddBonPlanActivity extends AppCompatActivity implements ICameraPermission {

    int SELECT_PICTURE = 200;
    private int notifID = 0;
    ListPlacesThread listPlacesThread = new ListPlacesThread();
    ArrayList<String> infos = new ArrayList<>();
    ImageView IVPreviewImage;
    ImageView image;
    private Bitmap picture;
    NotificationsController notificationsController;


    public AddBonPlanActivity() throws Throwable {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bon_plan);
        image = findViewById(R.id.ImageCamera);
        //notificationsController = new NotificationsController(new NotificationsView());


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
                if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(AddBonPlanActivity.this, new String[]{Manifest.permission.CAMERA}, ICameraPermission.REQUEST_CAMERA);
                }else{
                    takePicture();
                }
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
            if (requestCode==REQUEST_CAMERA){
                if (resultCode == RESULT_OK) {
                    picture = (Bitmap) data.getExtras().get("data");
                    setImage(picture);
                }else if (resultCode == RESULT_CANCELED) {
                    Toast toast = Toast.makeText(getApplicationContext(),"picture canceled", Toast.LENGTH_LONG );
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"action failed", Toast.LENGTH_LONG );
                    toast.show();
                }
            }
        }
    }

    public ListPlacesThread getListPlaces(){
        return listPlacesThread;
    }

    /**private void sendNotificationChannel(String title, String message, String channelId, int priority, Bitmap bitmap) {
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
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "CAMERA authorization granted", Toast.LENGTH_LONG);
                    toast.show();
                    this.takePicture();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "CAMERA authorization not granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    }

    public void setImage(Bitmap bitmap){IVPreviewImage.setImageBitmap(bitmap);}

    public void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }
}
