package etu.toptip.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import etu.toptip.R;
import etu.toptip.fragments.CameraFragment;
import etu.toptip.fragments.ICameraPermission;

public class CameraActivity extends AppCompatActivity implements ICameraPermission {

    private Bitmap picture;
    private CameraFragment cameraFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraFragment = (CameraFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentCamera);
        if (cameraFragment==null) cameraFragment = new CameraFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentCamera, cameraFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CAMERA){
            if (resultCode == RESULT_OK) {
                picture = (Bitmap) data.getExtras().get("data");
                cameraFragment.setImage(picture);
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
