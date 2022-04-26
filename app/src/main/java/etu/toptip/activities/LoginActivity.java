package etu.toptip.activities;

import androidx.appcompat.app.AppCompatActivity;

import etu.toptip.R;
import etu.toptip.fragments.ListFavorisFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button) findViewById(R.id.idBtnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final OkHttpClient client = new OkHttpClient();

                EditText email = findViewById(R.id.email);
                EditText password = findViewById(R.id.mdp);
                TextView titre = findViewById(R.id.idErreur);

                if (email.getText().toString().equals(""))
                    titre.setText("Entrer un email");
                else if (password.getText().toString().equals(""))
                    titre.setText("Entrer un mot de passe");

                RequestBody requestBody = new FormBody.Builder()
                        .add("email", email.getText().toString())
                        .add("password", password.getText().toString())
                        .build();

                Request request = new Request.Builder()
                        .url("http://192.168.1.14:3000/api/auth/login")
                        .post(requestBody)
                        .build();

                if (!(email.getText().toString().equals("")) &&
                        !(password.getText().toString().equals(""))) {


                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("Emile", "ERROR login");
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.d("Emile", "login");
                            try (ResponseBody responseBody = response.body()) {
                                Log.d("Emile", "JSP");
                                JSONObject jsonObj = new JSONObject(responseBody.string());
                                Log.d("Emile", "responseBody: " + jsonObj.getString("error"));
                                titre.setText(jsonObj.getString("error"));
                                if (jsonObj.getString("correct").equals("true")) {
                                    Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                                    startActivity(myIntent);
                                }
                            } catch (JSONException e) {
                                Log.d("Emile", "Fail 2.0: " + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

        });


        Button goRegister = (Button) findViewById(R.id.idBtnGoRegister);
        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), SignupActivity.class);
                startActivity(myIntent);
            }
        });
    }
}