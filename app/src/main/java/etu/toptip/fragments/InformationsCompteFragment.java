package etu.toptip.fragments;

import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import etu.toptip.R;
import etu.toptip.helper.Infologin;
import etu.toptip.helper.InfoCompteThread;

public class InformationsCompteFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TextView email, pseudo;

    public InformationsCompteFragment() {
    }

    public static InformationsCompteFragment newInstance(String param1, String param2) {
        InformationsCompteFragment fragment = new InformationsCompteFragment();
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
        View view = inflater.inflate(R.layout.fragment_infocompte, container, false);

        try {
            String url = "http://90.8.217.30:3000/api/user/" + Infologin.getIdUser();

            InfoCompteThread infoCompteThread = new InfoCompteThread();
            AsyncTask<String, Integer, JSONObject> execute;
            execute = infoCompteThread.execute(url);

            this.email = view.findViewById(R.id.emailInfoCompteET);
            this.email.setText(execute.get().getString("email"));
            this.pseudo = view.findViewById(R.id.pseudoInfoCompteET);
            this.pseudo.setText(execute.get().getString("userName"));
        }catch (InterruptedException | ExecutionException | JSONException e) {
            Log.d("Emile", "EROR: " + e.toString());
            e.printStackTrace();
        }
        return view;
    }
}
