package etu.toptip.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import etu.toptip.IListner;
import etu.toptip.R;
import etu.toptip.activities.AddBonPlanActivity;
import etu.toptip.activities.AddPlaceActivity;

import etu.toptip.model.Place;
import etu.toptip.model.ListPlaces;
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
        ListPlaces places = null;
        try {
            places = new ListPlaces();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        View view = inflater.inflate(R.layout.fragment_accueil, container, false);
        ListView listView = view.findViewById(R.id.place_list_view);
        PlaceAdapter adap = new PlaceAdapter(container.getContext(), places.getPlaces());
        listView.setAdapter(adap);
        adap.addListner(this);

        Button addBP = (Button) view.findViewById(R.id.BAjouterBP);
        addBP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(container.getContext(), AddPlaceActivity.class);
                startActivity(myIntent);
            }

        });

        return view ;
    }

    @Override
    public void OnClickPlace(Place place) {
        Fragment placeDetails = new PlaceDetails();
        Bundle bundle = new Bundle();
        bundle.putParcelable("place", (Parcelable)place );
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
}