package etu.toptip.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import etu.toptip.R;
import etu.toptip.fragments.AccueilFragment;
import etu.toptip.fragments.CameraFragment;
import etu.toptip.fragments.ICameraPermission;
import etu.toptip.fragments.IStorageActivity;
import etu.toptip.fragments.StorageFragment;
import etu.toptip.fragments.WalletFragment;
import etu.toptip.helper.Infologin;
import etu.toptip.helper.ListPlacesThread;
import etu.toptip.model.ListWallet;
import etu.toptip.model.Place;
import etu.toptip.model.Wallet;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddWalletActivity extends AppCompatActivity implements ICameraPermission, IStorageActivity {
    int SELECT_PICTURE = 200;
    private int notifID = 0;

    ListWallet listWallet = new ListWallet();
    ArrayList<String> infos = new ArrayList<>();
    ImageView IVPreviewImage;
    ImageView image;
    private Bitmap picture;
    private CameraFragment cameraFragment;
    private StorageFragment storageFragment;

    Uri uri;

    public AddWalletActivity() throws Throwable {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (Place l : ListPlacesThread.getPlaces()) {
            System.out.println("ok");
            System.out.println(l.getName());
        }
        setContentView(R.layout.activity_add_wallet);

        ImageButton retour = findViewById(R.id.retour);
        retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(myIntent);
            }
        });

        cameraFragment = (CameraFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentCamera);
        if (cameraFragment == null) cameraFragment = new CameraFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentCamera, cameraFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        storageFragment = (StorageFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentStorage);
        if (storageFragment == null) storageFragment = new StorageFragment(this);
        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction2.replace(R.id.fragmentStorage, storageFragment);
        fragmentTransaction2.addToBackStack(null);
        fragmentTransaction2.commit();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ListPlacesThread.getNames());
        Spinner name = findViewById(R.id.nameW);
        name.setAdapter(spinnerAdapter);


        Button addBP = (Button) findViewById(R.id.ajout);
        addBP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nomLieu = name.getSelectedItem().toString();
                System.out.println("Lieu: " + nomLieu);

                String sot = uploadImage(nomLieu);

                System.out.println("INNNNNFFFFFFFFFFFOOOOOO: " + sot);

                if (sot.equals("true")) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(myIntent);
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

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityIfNeeded(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                uri = data.getData();
                if (null != uri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(uri);
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


    private String uriToFilename(Uri uri) {
        String path = null;

        if ((Build.VERSION.SDK_INT < 19) && (Build.VERSION.SDK_INT > 11)) {
            path = getRealPathFromURI_API11to18(this, uri);
        } else {
            path = getFilePath(this, uri);
        }

        return path;
    }

    public String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }


    public String getFilePath(Context context, Uri uri) {
        //Log.e("uri", uri.getPath());
        String filePath = "";
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String wholeID = DocumentsContract.getDocumentId(uri);
            //Log.e("wholeID", wholeID);
            // Split at colon, use second item in the array
            String[] splits = wholeID.split(":");
            if (splits.length == 2) {
                String id = splits[1];

                String[] column = {MediaStore.Images.Media.DATA};
                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";

                Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);
                int columnIndex = cursor.getColumnIndex(column[0]);
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
            }
        } else {
            filePath = uri.getPath();
        }

        return filePath;
    }

    public String uploadImage(String nomLieu) {
        File imgFile2 = null;

        if (TextUtils.isEmpty(nomLieu))
            return "Veuillez choisir un lieu";
        else if (picture != null) {
            File f = new File(Environment.getExternalStorageDirectory().toString() + "/photoCarte.png");

            try {
                f.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            picture.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            imgFile2 = f;

        } else if (picture == null) {
            try {
                imgFile2 = new File(uriToFilename(uri));
            } catch (NullPointerException e) {
                return "Veuillez selectionner une image";
            }
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        System.out.println("MimeTypeMap.getFileExtensionFromUrl: " + MimeTypeMap.getFileExtensionFromUrl(imgFile2.getAbsolutePath()));
        System.out.println(imgFile2.getAbsolutePath());

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", imgFile2.getName(),
                        RequestBody.create(MediaType.parse("image/png"), imgFile2))
                .addFormDataPart("idUser", Infologin.getIdUser())
                .addFormDataPart("idLieu", ListPlacesThread.getPlaceByName(nomLieu).getId())
                .build();

        Request request = new Request.Builder()
//                    .url("http://192.168.1.14:3000/api/carte/")
                .url("http://90.8.217.30:3000/api/carte/")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("MB");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("GG");
            }
        });

        return "true";
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

    @Override
    public void onPictureLoad(Bitmap bitmap) {
        cameraFragment.setImage(bitmap);
    }

    @Override
    public Bitmap getPictureToSave() {
        return picture;
    }
}