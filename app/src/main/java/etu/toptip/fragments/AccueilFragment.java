package etu.toptip.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import org.json.JSONObject;

import java.util.ArrayList;

import etu.toptip.IListner;
import etu.toptip.R;
import etu.toptip.activities.AddPlaceActivity;

import etu.toptip.model.ListPlaces;
import etu.toptip.model.Place;
import etu.toptip.helper.ListPlacesThread;
import etu.toptip.model.PlaceAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccueilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccueilFragment extends Fragment implements IListner, FragmentChangeListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ListView listView;

    private int selectedFilter = -1;
    private String currentSearchText = "";
    private SearchView searchView;
    ListPlacesThread places ;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccueilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccueilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccueilFragment newInstance(String param1, String param2) {
        AccueilFragment fragment = new AccueilFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            places = new ListPlacesThread();
            AsyncTask<String, Integer, JSONObject> execute = places.execute("http://90.8.217.30:3000/api/lieu");
            System.out.println("ok"+places.getPlaces());

            //            Log.d("Emile", execute.get().toString());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        View view = inflater.inflate(R.layout.fragment_accueil, container, false);
        listView = view.findViewById(R.id.place_list_view);
        PlaceAdapter adap = new PlaceAdapter(container.getContext(), places.getPlaces());
        listView.setAdapter(adap);
        adap.addListner(this);
        initSearchWidgets(places,view);

        //supermarchés

        Button buttonS = (Button) view.findViewById(R.id.supermarchés);
        buttonS.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                filterList(0);
                selectedFilter = 0;
            }
        });

        Button buttonR = (Button) view.findViewById(R.id.resturants);
        buttonR.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                filterList(1);
                selectedFilter = 1 ;
            }
        });

        Button buttonA = (Button) view.findViewById(R.id.lieux);
        buttonA.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                allFilter();
            }
        });


        Button addBP = (Button) view.findViewById(R.id.BAjouterBP);
        addBP.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent myIntent = new Intent(container.getContext(), AddPlaceActivity.class);
                startActivity(myIntent);
            }

        });

        return view;
    }

    @Override
    public void OnClickPlace(Place place) {
        Fragment placeDetails = new PlaceDetails();
        Bundle bundle = new Bundle();
        bundle.putParcelable("place", (Parcelable) place);
        placeDetails.setArguments(bundle);
        replaceFragment(placeDetails);
    }


    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    public void initSearchWidgets(ListPlacesThread places, View view){
        searchView = view.findViewById(R.id.idSearchView2);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                currentSearchText = s;
                ArrayList<Place> filtredPlaces = new ArrayList<>();
                for(Place place :places.getPlaces() ){
                    if(place.getVille().toLowerCase().contains(s.toLowerCase())){
                        if(selectedFilter ==-1)  filtredPlaces.add(place);
                        else if(place.getType() == selectedFilter) filtredPlaces.add(place);
                    }
                }
                PlaceAdapter p = new PlaceAdapter(getContext().getApplicationContext(),filtredPlaces);
                listView.setAdapter(p);
                p.addListner(AccueilFragment.this);

                return false;
            }
        });
    }

    private void filterList(int status){
        selectedFilter = status ;
        ArrayList<Place> filtredPlaces = new ArrayList<>();
        for(Place place : places.getPlaces() ){
            if(place.getType() == status){
                if(currentSearchText.equals("")){
                    filtredPlaces.add(place);
                }
                else if(place.getVille().toLowerCase().contains(currentSearchText.toLowerCase())){
                    filtredPlaces.add(place);
                }
            }
        }
        PlaceAdapter placeAdapter = new PlaceAdapter(getContext().getApplicationContext(),filtredPlaces);
        listView.setAdapter(placeAdapter);
        placeAdapter.addListner(this);

    }

    public void allFilter(){
        searchView.setQuery("", false);
        searchView.clearFocus();
        selectedFilter = -1;
        PlaceAdapter placeAdapter = new PlaceAdapter(getContext().getApplicationContext(),places.getPlaces());
        listView.setAdapter(placeAdapter);
        placeAdapter.addListner(this);

    }


}