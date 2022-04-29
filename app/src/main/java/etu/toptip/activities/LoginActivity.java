package etu.toptip.activities;

import androidx.appcompat.app.AppCompatActivity;

import etu.toptip.R;
import etu.toptip.controller.LoginController;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {
    public EditText email, password;
    TextView titre;
    LoginController loginController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.loginController = new LoginController(this);

        this.email = findViewById(R.id.email);
        this.password = findViewById(R.id.mdp);
        this.titre = findViewById(R.id.idErreur);

        Button login = findViewById(R.id.idBtnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("Emile", "onClickLogin");
                loginController.OnLogin(email.getText().toString(), password.getText().toString());
//
//                int log = loginPresenter.OnLogin(email.getText().toString().trim(), password.getText().toString().trim());
//                if (log == 1) {
//
//                    RequestBody requestBody = new FormBody.Builder()
//                            .add("email", email.getText().toString())
//                            .add("password", password.getText().toString())
//                            .build();
//
//                    Request request = new Request.Builder()
//                            .url("http://90.8.217.30:3000/api/auth/login")
//                            .post(requestBody)
//                            .build();
//
//                    client.newCall(request).enqueue(new Callback() {
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                            Log.d("Emile", "ERROR login");
//                            e.printStackTrace();
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
////                                Log.d("Emile", "login");
//                            try (ResponseBody responseBody = response.body()) {
////                                    Log.d("Emile", "JSP");
//                                JSONObject jsonObj = new JSONObject(responseBody.string());
////                                    Log.d("Emile", "responseBody: " + jsonObj.getString("error"));
//                                titre.setText(jsonObj.getString("error"));
////                                    iLoginView.OnLoginError(jsonObj.getString("error"));
//                                if (jsonObj.getString("correct").equals("true")) {
//                                    Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
//                                    startActivity(myIntent);
//                                }
//                            } catch (JSONException e) {
//                                Log.d("Emile", "Fail 2.0: " + e.getMessage());
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
            }
        });

        Button goRegister = (Button) findViewById(R.id.idBtnGoRegister);
        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(myIntent);
            }
        });
    }

    public void showError(String error, Boolean connect) {
//        Toast toast = Toast.makeText(this, error, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.TOP | Gravity.CENTER, 20, 30);
//        toast.show();
        if (connect) {
            this.titre.setTextColor(getResources().getColor(R.color.greenAuth));
        }
        this.titre.setText(error);
        if (connect) {
            Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(myIntent);
        }

    }
}