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

public class listeAvion extends AppCompatActivity{

        ListView listView;
        SharedPreferences sharedpreferences;
        List<Avion> avions;

        private String API_URL;
        // Adapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.liste_avion);
            API_URL=getString(R.string.api_link)+"/api_Aerosoft/api/crudAvion/read.php";

            listView = (ListView) findViewById(R.id.listView);
            avions= new ArrayList<>();

            // Affichage liste des avions
            extractAvions();

            // Enregistrement du menu contextuelle dans la vue listView
           // registerForContextMenu(listView);
            Log.i("message link", API_URL);
        }

        private void extractAvions() {

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
                                // nom de table "avion" dans l'api qui le json
                                JSONArray volArray = obj.getJSONArray("avion");

                                // Pour chaque avion dans l'array récupéré
                                for (int i = 0; i < volArray.length(); i++) {
                                    JSONObject volObject = volArray.getJSONObject(i);

                                    Log.i("message type",volObject.getString("TypeAvion"));

                                    Avion avion = new Avion(
                                            volObject.getString("NumAvion"),
                                            volObject.getString("TypeAvion"),
                                            volObject.getString("BaseAeroport")
                                    );
                                    avions.add(avion);
                                }
                                // Ajouter les vol à listView
                                AdapterAvion adapter = new AdapterAvion(avions, getApplicationContext());
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
        // Création du menu contextuelle
}
