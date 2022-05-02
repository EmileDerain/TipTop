package etu.toptip.fragments;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import etu.toptip.IListner;
import etu.toptip.R;
import etu.toptip.activities.AddBonPlanActivity;
import etu.toptip.model.ListFavoris;
import etu.toptip.helper.ListPlacesThread;
import etu.toptip.model.Place;

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
                ListPlacesThread places = null;
                try {
                    places = new ListPlacesThread();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                ListFavoris listFavoris = new ListFavoris();
                listFavoris.getFavoris().add(places.getPlaceByName(nameView.getText().toString()));
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