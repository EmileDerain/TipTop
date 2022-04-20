package etu.toptip.fragments;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import etu.toptip.IListner;
import etu.toptip.R;
import etu.toptip.models.ListFavoris;
import etu.toptip.models.ListPlaces;
import etu.toptip.models.Place;
import etu.toptip.models.PlaceAdapter;

public class ListFavorisFragment extends Fragment implements IListner {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ListFavorisFragment() {
    }

    public static ListFavorisFragment newInstance(String param1, String param2) {
        ListFavorisFragment fragment = new ListFavorisFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listefavoris, container, false);

        ListFavoris favoris = new ListFavoris();
        ListView listView = view.findViewById(R.id.place_fav_list_view);
        PlaceAdapter adap = new PlaceAdapter(container.getContext(),favoris.getFavoris());
        listView.setAdapter(adap);
        adap.addListner(this);


        return view;
    }

    @Override
    public void OnClickPlace(Place place) {
    }
}
