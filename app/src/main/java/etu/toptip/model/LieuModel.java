package etu.toptip.model;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import etu.toptip.controller.LieuController;
import etu.toptip.helper.Infologin;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LieuModel {
    OkHttpClient client;

    public LieuController lieuController;

    public LieuModel(LieuController controller) {
        this.lieuController = controller;
    }

    public void LieuModel2(String nomLieu, String adresse, String ville, String codePostal, int type) {

        if (TextUtils.isEmpty(nomLieu)) {
            lieuController.OnLieuError2("Veuillez rentrer un nom de lieu", false);
        } else if (TextUtils.isEmpty(adresse)) {
            lieuController.OnLieuError2("Veuillez rentrer une adresse", false);
        } else if (TextUtils.isEmpty(ville)) {
            lieuController.OnLieuError2("Veuillez rentrer une ville", false);
        } else if (TextUtils.isEmpty(codePostal)) {
            lieuController.OnLieuError2("Veuillez rentrer un codePostal", false);
//        } else if (TextUtils.isEmpty(type)) {
//            lieuController.OnLieuError2("Veuillez selectionner un type de lieu", false);
        } else {

            client = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("adresse", adresse)
                    .add("codepostal", codePostal)
                    .add("typeBonPlan", Integer.toString(type))
                    .add("ville", ville)
                    .add("nomDuLieu", nomLieu)
                    .add("imageUrl", "https://img-19.ccm2.net/uyhYf42aUv1KKLINIArsg13Q2V4=/325x/78f15ccb4e4742bebb11cdf5874baf8f/ccm-faq/LOUPE.jpg")
                    .build();

            String url = "http://90.8.217.30:3000/api/lieu/";

            Request request = new Request.Builder()
                    .url(url)
                    .put(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("Emile", "ERROR onFailure" + e.toString());
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    try (ResponseBody responseBody = response.body()) {

                        JSONObject jsonObj = new JSONObject(responseBody.string());

                        if (jsonObj.getString("correct").equals("true"))
                            lieuController.OnLieuError2(jsonObj.getString("message"), true);
                        else
                            lieuController.OnLieuError2(jsonObj.getString("message"), false);
                    } catch (JSONException e) {
                        Log.d("Emile", "ERROR onResponse" + e.toString());
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
