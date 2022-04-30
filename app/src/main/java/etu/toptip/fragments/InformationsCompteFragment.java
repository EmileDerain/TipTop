package etu.toptip.fragments;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.io.IOException;

import etu.toptip.R;
import etu.toptip.activities.Infologin;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class InformationsCompteFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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

        OkHttpClient client = new OkHttpClient();

        String url = "http://90.8.217.30:3000/api/user/" + Infologin.getIdUser();
//        String url = "http://192.168.1.14:3000/api/user/" + Infologin.getIdUser();

        Request request = new Request.Builder()
//                    .url("http://192.168.1.14:3000/api/user/auth/signup")
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {

                TextView mail = view.findViewById(R.id.emailInfoCompteET);
                TextView pseuso = view.findViewById(R.id.pseudoInfoCompteET);

                try (ResponseBody responseBody = response.body()) {

                    JSONObject jsonObj = new JSONObject(responseBody.string());

                    mail.setText(jsonObj.getString("email"));
                    pseuso.setText(jsonObj.getString("userName "));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
}
