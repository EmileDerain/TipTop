package etu.toptip.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import etu.toptip.R;
import etu.toptip.fragments.CameraFragment;
import etu.toptip.fragments.ICameraPermission;
import etu.toptip.fragments.IStorageActivity;
import etu.toptip.fragments.StorageFragment;
import etu.toptip.helper.ListPlacesThread;
import etu.toptip.model.ListWallet;
import etu.toptip.model.Place;
import etu.toptip.model.Wallet;

public class AddWalletActivity extends AppCompatActivity implements ICameraPermission, IStorageActivity {
    int SELECT_PICTURE = 200;
    private int notifID = 0;

    ListPlacesThread places = new ListPlacesThread();
    ListWallet listWallet = new ListWallet();
    ArrayList<String> infos = new ArrayList<>();
    ImageView IVPreviewImage;
    ImageView image;
    private Bitmap picture;
    private CameraFragment cameraFragment;
    private StorageFragment storageFragment;


    public AddWalletActivity()  throws Throwable  {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(Place l : places.getPlaces()){
            System.out.println("ok");
            System.out.println(l.getName());
        }
        setContentView(R.layout.activity_add_wallet);

        cameraFragment = (CameraFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentCamera);
        if (cameraFragment==null) cameraFragment = new CameraFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentCamera, cameraFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        storageFragment = (StorageFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentStorage);
        if (storageFragment==null) storageFragment = new StorageFragment(this);
        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction2.replace(R.id.fragmentStorage, storageFragment);
        fragmentTransaction2.addToBackStack(null);
        fragmentTransaction2.commit();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, places.getNames());
        Spinner name =  findViewById(R.id.nameW);
        name.setAdapter(spinnerAdapter);


        Button addBP = (Button) findViewById(R.id.ajout);
        addBP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                listWallet.getWallet().add(new Wallet("nadim", "https://www.pagesjaunes.fr/media/agc/a7/8c/4d/00/00/43/c5/1d/0a/c0/5fa1a78c4d000043c51d0ac0/5fa1a78c4d000043c51d0ac1.jpg"));

                Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "CAMERA authorization granted", Toast.LENGTH_LONG);
                    toast.show();
                    cameraFragment.takePicture();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "CAMERA authorization not granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            case REQUEST_MEDIA_WRITE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    storageFragment.saveToInternalStorage(picture);
                    Toast toast = Toast.makeText(getApplicationContext(), "write authorization granted", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "write authorization not granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            case REQUEST_MEDIA_READ: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "read authorization granted", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "read authorization not granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            break;
        }
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
                    //System.out.println("j'ai rentré");
                }
            }
            if (requestCode == 101) {
                Bitmap bitmap = (Bitmap) (data != null ? data.getExtras().get("data") : null);
                IVPreviewImage.setImageBitmap(bitmap);
            }
            if (requestCode == REQUEST_CAMERA) {
                if (resultCode == RESULT_OK) {
                    picture = (Bitmap) data.getExtras().get("data");
                    cameraFragment.setImage(picture);
                    storageFragment.setEnabledSaveButton();
                } else if (resultCode == RESULT_CANCELED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "picture canceled", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "action failed", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    }

    @Override
    public void onPictureLoad(Bitmap bitmap) {
        cameraFragment.setImage(bitmap);
    }

    @Override
    public Bitmap getPictureToSave() {
        return picture;
    }
}