package etu.toptip.activities;

import androidx.appcompat.app.AppCompatActivity;

import etu.toptip.R;
import etu.toptip.controller.ILoginController;
import etu.toptip.controller.LoginController;
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

import etu.toptip.view.ILoginView;


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

import android.widget.Toast;


public class LoginActivity extends AppCompatActivity implements ILoginView {
    EditText email, password;
    TextView titre;
    ILoginController loginPresenter;
    OkHttpClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.mdp);
        titre = findViewById(R.id.idErreur);

        loginPresenter = new LoginController(this);
        client = new OkHttpClient();

        Button login = findViewById(R.id.idBtnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                int log = loginPresenter.OnLogin(email.getText().toString().trim(), password.getText().toString().trim());
                if (log == 1) {

                    RequestBody requestBody = new FormBody.Builder()
                            .add("email", email.getText().toString())
                            .add("password", password.getText().toString())
                            .build();

                    Request request = new Request.Builder()
                            .url("http://90.8.217.30:3000/api/auth/login")
                            .post(requestBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("Emile", "ERROR login");
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
//                                Log.d("Emile", "login");
                            try (ResponseBody responseBody = response.body()) {
//                                    Log.d("Emile", "JSP");
                                JSONObject jsonObj = new JSONObject(responseBody.string());
//                                    Log.d("Emile", "responseBody: " + jsonObj.getString("error"));
                                titre.setText(jsonObj.getString("error"));
//                                    iLoginView.OnLoginError(jsonObj.getString("error"));
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

    @Override
    public void OnLoginSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}