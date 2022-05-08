package etu.toptip.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import etu.toptip.R;
import etu.toptip.activities.CameraActivity;
import etu.toptip.activities.LoginActivity;
import etu.toptip.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPlaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPlaceFragment extends Fragment {


    int SELECT_PICTURE = 200;
    private int notifID = 0;
    ImageView imageView;
    CameraFragment cameraFragment;
    ImageView IVPreviewImage;
    TextView titre;
    Uri uri;
    private static final int READ_REQUEST_CODE = 42;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPlaceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPlaceFragment newInstance(String param1, String param2) {
        AddPlaceFragment fragment = new AddPlaceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AddPlaceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_place, container, false);

        this.titre = view.findViewById(R.id.idErreurModifLieu);

        Button addBP = (Button) view.findViewById(R.id.BtnAjouterBP);
        addBP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText name = view.findViewById(R.id.nameResto);
                String nameText = name.getText().toString();


                EditText adresse = view.findViewById(R.id.AdresseResto);
                String adresseText = adresse.getText().toString();


                Spinner typeSpinner = view.findViewById(R.id.typeResto);
                int type = typeSpinner.getSelectedItemPosition();


                Intent myIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(myIntent);
            }
        });

        Button cam = view.findViewById(R.id.BCamera);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), CameraActivity.class);
                startActivity(myIntent);
            }
        });

        Button BSelectImage = view.findViewById(R.id.BSelectImage);
        BSelectImage.setText("JE SUIS MOI");

        IVPreviewImage = view.findViewById(R.id.IVPreviewImage);

        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("++++++++++++++++++++++++++++++++++++++");
                imageChooser();
            }
        });
        return view;
    }

    void imageChooser() {
        System.out.println("=============================================");
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        uri = i.getData();
        System.out.println("JESUILA: " + uri.toString());
        getActivity().startActivityIfNeeded(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

//    public void performFileSearch() {
//        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
//        // browser.
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
//        intent.setType("image/*");
//
//        startActivityForResult(intent, READ_REQUEST_CODE);
//    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        System.out.println("JJJJJJJJJJJJJJEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
//        Log.d("Emile", "ok");
//        if (resultCode == getActivity().RESULT_OK) {
//
//            // compare the resultCode with the
//            // SELECT_PICTURE constant
//            if (requestCode == SELECT_PICTURE) {
//                // Get the url of the image from data
//                uri = data.getData();
//                if (null != uri) {
//                    // update the preview image in the layout
//                    IVPreviewImage.setImageURI(uri);
//                    System.out.println("j'ai rentré");
//
//                }
//            }
//        }
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        System.out.println("JE VIENS DE PASSER");
//        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            uri = null;
//            if (data != null) {
//                uri = data.getData();
//                Log.i("Emile", "Uri: " + uri.toString());
//                IVPreviewImage.setImageURI(uri);
//            }
//        }
//    }

//    private String uriToFilename(Uri uri) {
//        String path = null;
//
//        if ((Build.VERSION.SDK_INT < 19) && (Build.VERSION.SDK_INT > 11)) {
//            path = getRealPathFromURI_API11to18(this, uri);
//            System.out.println("111111111111111111111111111: " + getRealPathFromURI_API11to18(this, uri));
//        } else {
//            System.out.println("22222222222222222222222");
//            path = getFilePath(this, uri);
//        }
//
//        return path;
//    }
//
//    public String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
//        String path = "";
//        if (getContentResolver() != null) {
//            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//            if (cursor != null) {
//                cursor.moveToFirst();
//                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//                path = cursor.getString(idx);
//                cursor.close();
//            }
//        }
//        return path;
//    }
//
//
//    public String getFilePath(Context context, Uri uri) {
//        //Log.e("uri", uri.getPath());
//        String filePath = "";
//        if (DocumentsContract.isDocumentUri(context, uri)) {
//            String wholeID = DocumentsContract.getDocumentId(uri);
//            //Log.e("wholeID", wholeID);
//            // Split at colon, use second item in the array
//            String[] splits = wholeID.split(":");
//            if (splits.length == 2) {
//                String id = splits[1];
//
//                String[] column = {MediaStore.Images.Media.DATA};
//                // where id is equal to
//                String sel = MediaStore.Images.Media._ID + "=?";
//                Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                        column, sel, new String[]{id}, null);
//                int columnIndex = cursor.getColumnIndex(column[0]);
//                if (cursor.moveToFirst()) {
//                    filePath = cursor.getString(columnIndex);
//                }
//                cursor.close();
//            }
//        } else {
//            filePath = uri.getPath();
//        }
//
//        return filePath;
//    }

    public void showError(String error, Boolean create) {
//        Toast toast = Toast.makeText(this, error, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.TOP | Gravity.CENTER, 20, 30);
//        toast.show();
        if (create) {
            this.titre.setTextColor(getResources().getColor(R.color.greenAuth));
        }
        this.titre.setText(error);
//        if (create) {
//            Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
//            startActivity(myIntent);
//        }
    }
}