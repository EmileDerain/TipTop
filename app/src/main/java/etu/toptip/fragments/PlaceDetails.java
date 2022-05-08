package etu.toptip.fragments;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;

import etu.toptip.IListner;
import etu.toptip.R;
import etu.toptip.activities.AddBonPlanActivity;
import etu.toptip.helper.Infologin;
import etu.toptip.helper.ListPlacesThread;
import etu.toptip.model.Place;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaceDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaceDetails extends Fragment implements IListner, FragmentChangeListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    OkHttpClient client;

    public PlaceDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaceDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaceDetails newInstance(String param1, String param2) {
        PlaceDetails fragment = new PlaceDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_details, container, false);

        Button add = (Button) view.findViewById(R.id.BAjouterBP);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(container.getContext(), AddBonPlanActivity.class);
                startActivity(myIntent);
            }
        });

        Bundle bundle = this.getArguments();

        Place model = bundle.getParcelable("place");

        TextView nameView = view.findViewById(R.id.name);
        nameView.setText(model.getName());

        TextView detailsView = view.findViewById(R.id.description);
        detailsView.setText(model.getVille());

        ImageView imageView = view.findViewById(R.id.icon);
        Picasso.get().load(model.getImage()).into(imageView);


        Button addToFavs = (Button) view.findViewById(R.id.BAjouterAuxFavs);
        addToFavs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("idLieu", ListPlacesThread.getPlaceByName(nameView.getText().toString()).getId())
                        .add("idUser", Infologin.getIdUser())
                        .build();

                Request request = new Request.Builder()
//                        .url("http://192.168.1.14:3000/api/favori")
                        .url("http://90.8.217.30:3000/api/favori")
                        .post(requestBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            System.out.println("response PlaceDetail: " + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }

        });

        return view;
    }

    @Override
    public void OnClickPlace(Place place) {
    }

    @Override
    public void replaceFragment(Fragment fragment) {
    }
}