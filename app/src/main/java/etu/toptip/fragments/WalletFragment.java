package etu.toptip.fragments;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import etu.toptip.R;
import etu.toptip.activities.AddWalletActivity;
import etu.toptip.model.ListWallet;
import etu.toptip.model.WalletAdapter;

public class WalletFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public WalletFragment() {
    }

    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
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
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        // Inflate the layout for this fragment
        ListWallet wallet = new ListWallet();

        GridView listView = view.findViewById(R.id.wallet);
        WalletAdapter adap = new WalletAdapter(container.getContext(), wallet.getWallet());
        listView.setAdapter(adap);
        Button addBP = (Button) view.findViewById(R.id.add);
        addBP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(container.getContext(), AddWalletActivity.class);
                startActivity(myIntent);
            }

        });
        return view;
    }
}
