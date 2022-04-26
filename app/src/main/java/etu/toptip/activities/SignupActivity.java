package etu.toptip.activities;

import androidx.appcompat.app.AppCompatActivity;

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

import etu.toptip.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import android.widget.Toast;

import etu.toptip.R;
import etu.toptip.controller.ILoginController;
import etu.toptip.controller.LoginController;
import etu.toptip.view.ILoginView;


public class SignupActivity extends AppCompatActivity implements ILoginView {
    EditText email, password, userName, secondPassword;
    Button loginb;
    ILoginController loginPresenter;
    OkHttpClient client;
    TextView titre;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        userName = findViewById(R.id.idEdtUserName);
        secondPassword = findViewById(R.id.ConfirmPassword);
        titre = findViewById(R.id.idTVHeaderErreur);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.idEdtPassword);

        loginPresenter = new LoginController(this);
        client = new OkHttpClient();

        Button next = findViewById(R.id.idBtnRegister);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                int log = loginPresenter.OnLogin(email.getText().toString().trim(), password.getText().toString().trim());
                if (log == 1) {

                    Log.d("Emile", "onClick: " + password.getText().toString());

                    if (userName.getText().toString().equals(""))
                        titre.setText("Entrer un nom d'utilisateur");
//                else if (email.getText().toString().equals(""))
//                    titre.setText("Entrer un email");
//                else if (password.getText().toString().equals(""))
//                    titre.setText("Entrer un mot de passe");
//                else if (password.getText().toString().length() <= 5)
//                    titre.setText("Mot de passe trop court");
                    else if (!(password.getText().toString().equals(secondPassword.getText().toString())))
                        titre.setText("Les mots de passe de correspondent pas");

                    if (!(userName.getText().toString().equals("")) &&
                            (password.getText().toString().equals(secondPassword.getText().toString()))) {

                        RequestBody requestBody = new FormBody.Builder()
                                .add("email", email.getText().toString())
                                .add("userName", userName.getText().toString())
                                .add("password", password.getText().toString())
                                .build();

                        Request request = new Request.Builder()
                                .url("http://192.168.1.14:3000/api/auth/signup")
                                .post(requestBody)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d("Emile", "ERROR signup");
                                titre.setText("Veuiller verifier votre connexion internet");
//                            Log.d("Emile", "ERROR signup: " + e.getMessage());
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                try (ResponseBody responseBody = response.body()) {
                                    if (!response.isSuccessful()) {
                                        JSONObject jsonObj = new JSONObject(responseBody.string());
                                        try {
                                            if (jsonObj.getJSONObject("error").getString("message").substring(0, 68)
                                                    .equals("User validation failed: email: Error, expected `email` to be unique.")) {
                                                titre.setText("Adresse mail déjà utilisée");
                                            }
                                        } catch (Exception e) {
                                            Log.d("Emile", "Fail: " + response);
                                            titre.setText(jsonObj.getJSONObject("error").getString("message"));
                                            throw new IOException("Unexpected code -> " + response);
                                        }
                                    } else {
                                        Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
                                        startActivity(myIntent);
                                    }

                                } catch (Exception e) {
                                    Log.d("Emile", "Fail 2.0: " + e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
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