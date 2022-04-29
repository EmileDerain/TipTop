package etu.toptip.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

import etu.toptip.R;
import etu.toptip.controller.SignUpController;

public class SignUpActivity extends AppCompatActivity {
    EditText email, password, userName, secondPassword;
    SignUpController signUpController;
    TextView titre;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.signUpController = new SignUpController(this);

        this.userName = findViewById(R.id.idEdtUserName);
        this.secondPassword = findViewById(R.id.ConfirmPassword);
        this.titre = findViewById(R.id.idTVHeaderErreur);
        this.email = findViewById(R.id.Email);
        this.password = findViewById(R.id.idEdtPassword);
        Button next = findViewById(R.id.idBtnRegister);

        this.signUpController = new SignUpController(this);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("Emile", "onClickSignUp");
                signUpController.OnSignUp(email.getText().toString(), userName.getText().toString(), password.getText().toString(), secondPassword.getText().toString());
//
//                int log = signUpController.OnSignUp(userName.getText().toString().trim(),email.getText().toString().trim(), password.getText().toString().trim(), secondPassword.getText().toString().trim());
//                if (log == 1) {
//                    /**Log.d("Emile", "onClick: " + password.getText().toString());
//
//                    if (userName.getText().toString().equals(""))
//                        titre.setText("Entrer un nom d'utilisateur");
////                else if (email.getText().toString().equals(""))
////                    titre.setText("Entrer un email");
////                else if (password.getText().toString().equals(""))
////                    titre.setText("Entrer un mot de passe");
////                else if (password.getText().toString().length() <= 5)
////                    titre.setText("Mot de passe trop court");
//                    else if (!(password.getText().toString().equals(secondPassword.getText().toString())))
//                        titre.setText("Les mots de passe de correspondent pas");
//
//                    if (!(userName.getText().toString().equals("")) &&
//                            (password.getText().toString().equals(secondPassword.getText().toString())))*/ {
//
//                        RequestBody requestBody = new FormBody.Builder()
//                                .add("email", email.getText().toString())
//                                .add("userName", userName.getText().toString())
//                                .add("password", password.getText().toString())
//                                .build();
//
//                        Request request = new Request.Builder()
//                                .url("http://90.8.217.30:3000/api/auth/signup")
//                                .post(requestBody)
//                                .build();
//
//                        client.newCall(request).enqueue(new Callback() {
//                            @Override
//                            public void onFailure(Call call, IOException e) {
//                                Log.d("Emile", "ERROR signup");
//                                titre.setText("Veuiller verifier votre connexion internet");
////                            Log.d("Emile", "ERROR signup: " + e.getMessage());
//                                e.printStackTrace();
//                            }
//
//                            @Override
//                            public void onResponse(Call call, Response response) throws IOException {
//                                try (ResponseBody responseBody = response.body()) {
//                                    if (!response.isSuccessful()) {
//                                        JSONObject jsonObj = new JSONObject(responseBody.string());
//                                        try {
//                                            if (jsonObj.getJSONObject("error").getString("message").substring(0, 68)
//                                                    .equals("User validation failed: email: Error, expected `email` to be unique.")) {
//                                                titre.setText("Adresse mail déjà utilisée");
//                                            }
//                                        } catch (Exception e) {
//                                            Log.d("Emile", "Fail: " + response);
//                                            titre.setText(jsonObj.getJSONObject("error").getString("message"));
//                                            throw new IOException("Unexpected code -> " + response);
//                                        }
//                                    } else {
//                                        Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
//                                        startActivity(myIntent);
//                                    }
//
//                                } catch (Exception e) {
//                                    Log.d("Emile", "Fail 2.0: " + e.getMessage());
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                    }
//                }
            }
        });

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
            Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(myIntent);
        }
    }
}