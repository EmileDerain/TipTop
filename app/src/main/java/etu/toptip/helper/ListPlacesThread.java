package etu.toptip.helper;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import etu.toptip.model.Place;
import etu.toptip.model.factory.FactoryManager;

public class ListPlacesThread extends AsyncTask<String, Integer, JSONObject> {

    public static ArrayList<Place> listPlaces = new ArrayList<>();

    public ListPlacesThread() throws Throwable {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        listPlaces.clear();
//        listPlaces.add(FactoryManager.build("Picare", 0, "https://www.pagesjaunes.fr/media/agc/a7/8c/4d/00/00/43/c5/1d/0a/c0/5fa1a78c4d000043c51d0ac0/5fa1a78c4d000043c51d0ac1.jpg", "Antibes", "06600", "1770 Rte de Grasse"));
        //
//        try {
//            OkHttpClient client = new OkHttpClient();
//
//            Request request = new Request.Builder()
////                    .url("http://192.168.1.14:3000/api/lieu")
//                    .url("http://90.8.217.30:3000/api/lieu")
//                    .build();
//
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) {
//                    String adresse, codepostal, ville, nomDuLieu, imageUrl;
//                    int typeBonPlan;
//
//                    try (ResponseBody responseBody = response.body()) {
//                        JSONArray jsonarray = new JSONArray(responseBody.string());
//
//                        for (int i = 0; i < jsonarray.length(); i++) {
//                            adresse = ((JSONObject) jsonarray.get(i)).getString("nomDuLieu");
//                            codepostal = ((JSONObject) jsonarray.get(i)).getString("codepostal");
//                            typeBonPlan = Integer.parseInt(((JSONObject) jsonarray.get(i)).getString("typeBonPlan"));
//                            ville = ((JSONObject) jsonarray.get(i)).getString("ville");
//                            nomDuLieu = ((JSONObject) jsonarray.get(i)).getString("nomDuLieu");
//                            imageUrl = ((JSONObject) jsonarray.get(i)).getString("imageUrl");
//
//                            listPlaces.add(FactoryManager.build(nomDuLieu, typeBonPlan, imageUrl, ville, codepostal, adresse));
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } catch (Throwable throwable) {
//                        throwable.printStackTrace();
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        String adresse, codepostal, ville, nomDuLieu, imageUrl;
        int typeBonPlan;

        String apiResponse = "";
        JSONArray jsonarray = null;

        try {
            URL url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int status = conn.getResponseCode();
            if (status == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                apiResponse = sb.toString();
            } else {
                Log.d("Emile", "BUUUUUUUG code 400, je sais pas trop");
            }
            conn.disconnect();

            jsonarray = new JSONArray(apiResponse);

            for (int i = 0; i < jsonarray.length(); i++) {
                imageUrl = ((JSONObject) jsonarray.get(i)).getString("imageUrl");
                adresse = ((JSONObject) jsonarray.get(i)).getString("nomDuLieu");
                codepostal = ((JSONObject) jsonarray.get(i)).getString("codepostal");
                typeBonPlan = Integer.parseInt(((JSONObject) jsonarray.get(i)).getString("typeBonPlan"));
                ville = ((JSONObject) jsonarray.get(i)).getString("ville");
                nomDuLieu = ((JSONObject) jsonarray.get(i)).getString("nomDuLieu");
                System.out.println(imageUrl);
                listPlaces.add(FactoryManager.build(nomDuLieu, typeBonPlan, imageUrl, ville, codepostal, adresse));
            }

        } catch (Exception e) {
            Log.d("Emile", "Error: " + e.toString());
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public ArrayList<Place> getPlaces() {
        return listPlaces;
    }

    public Place getPlaceByName(String name) {
        for (Place place : listPlaces
        ) {
            if (place.getName() == name) return place;
        }
        return null;
    }


    public ArrayList<String> getNames(){
        ArrayList<String> set = new ArrayList<>();
        set.add("Lidl");
        set.add("Carrefour");
        set.add("Casino");
        for (Place place : listPlaces) {
            set.add(place.getName());
        }

        return set;
    }
}
