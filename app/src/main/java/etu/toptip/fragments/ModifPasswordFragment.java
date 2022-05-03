package etu.toptip.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import etu.toptip.R;
import etu.toptip.activities.LoginActivity;
import etu.toptip.controller.ModifPasswordController;
import etu.toptip.controller.SignUpController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModifPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModifPasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView titre;
    EditText oldPass, newPass;
    ModifPasswordController modifPasswordController;

    public ModifPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModifPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModifPasswordFragment newInstance(String param1, String param2) {
        ModifPasswordFragment fragment = new ModifPasswordFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modif_pwd, container, false);
        Log.d("Emile", "onClick(View v1");
        TextView test = view.findViewById(R.id.idInfoHeader);
        test.setTextSize(50);
        Log.d("NADIM", test.getText().toString());
        test.setText("ok");

        this.titre = view.findViewById(R.id.idErreurModifPassword);
        this.oldPass = view.findViewById(R.id.oldPassword);
        this.newPass = view.findViewById(R.id.newPassword);
        Log.d("Emile", "onClick(View v2");
        this.modifPasswordController = new ModifPasswordController(this);
        Log.d("Emile", "onClick(View v3");
        Button modifMdp2 = view.findViewById(R.id.ok);
        this.titre.setText("efhyjtutyhgrfeasrht");
        modifMdp2.setText("JE SUIS UN TEST2");

        Log.d("Emile", "onClick(View v3bis");
        modifMdp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Emile", "onClick(View v4");
//                modifPasswordController.OnModifPassword(oldPass.getText().toString(), newPass.getText().toString());
            }
        });
        Log.d("Emile", "onClick(View v3bis2");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modif_password, container, false);
    }

    public void showError(String error, Boolean create) {
//        Toast toast = Toast.makeText(this, error, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.TOP | Gravity.CENTER, 20, 30);
//        toast.show();
        if (create) {
            this.titre.setTextColor(getResources().getColor(R.color.greenAuth));
        }
        this.titre.setText(error);
        if (create) {
            //Changer de fragment
        }
    }

}