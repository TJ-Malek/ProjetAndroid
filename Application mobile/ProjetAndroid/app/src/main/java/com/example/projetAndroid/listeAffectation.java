package com.example.projetAndroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class listeAffectation extends AppCompatActivity { ListView listView;
    SharedPreferences sharedpreferences;
    List<Affectation> Affectation;

    private String API_URL;
    private static final int MENU_ITEM_EDIT = 111;
    private static final int MENU_ITEM_DELETE = 222;
    // Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_affectation);
        API_URL=getString(R.string.api_link)+"/api_Aerosoft/api/crudAffectation/read.php";

        listView = (ListView) findViewById(R.id.listViewAffectation);
        Affectation= new ArrayList<>();

        // Affichage liste des affectations
        extractAffectation();


        Log.i("message", API_URL);

    }

    private void extractAffectation() {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        // Requette GET
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);


                        try {
                            // Récupération du résultat
                            JSONObject obj = new JSONObject(response);

                            JSONArray affectationArray = obj.getJSONArray("affectation");

                            // Pour chaque affectation dans l'array récupéré
                            for (int i = 0; i < affectationArray.length(); i++) {
                                JSONObject affectationObject = affectationArray.getJSONObject(i);

                                Affectation affectation = new Affectation(
                                        affectationObject.getString("IdAffectation"),
                                        affectationObject.getString("NumVol"),
                                        affectationObject.getString("DateVol"),
                                        affectationObject.getString("NumAvion"),
                                        affectationObject.getString("IdPilote")


                                );

                                Affectation.add(affectation);

                            }
                            // Ajouter les affectation à listView
                            AdapterAffectation adapter = new AdapterAffectation(Affectation, getApplicationContext());

                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }




}
