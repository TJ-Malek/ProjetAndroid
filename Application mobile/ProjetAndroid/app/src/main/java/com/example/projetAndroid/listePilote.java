package com.example.projetAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class listePilote extends AppCompatActivity {
    ListView listView;
    SharedPreferences sharedpreferences;
    List<Pilote> Pilote;

    private static String API_URL="http://10.75.25.176:8080/api_Aerosoft/api/crudPilote/read.php";
    private static final int MENU_ITEM_EDIT = 111;
    private static final int MENU_ITEM_DELETE = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_pilote);

        Log.i("message", "create.");
        listView = (ListView) findViewById(R.id.listView);
        Pilote= new ArrayList<>();

        // Affichage liste des vols
        extractPilote();

        // Enregistrement du menu contextuelle dans la vue listView
        registerForContextMenu(listView);
    }
    private void extractPilote() {

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

                            JSONArray piloteArray = obj.getJSONArray("pilote");

                            // Pour chaque pilote dans l'array récupéré
                            for (int i = 0; i < piloteArray.length(); i++) {
                                JSONObject piloteObject = piloteArray.getJSONObject(i);

                                Pilote pilote = new Pilote(
                                        piloteObject.getString("IdPilote"),
                                        piloteObject.getString("NomPilote"),
                                        piloteObject.getString("PrenomPilote"),
                                        piloteObject.getString("Matricule")

                                );

                                Pilote.add(pilote);

                            }
                            // Ajouter les pilotes à listView
                            AdapterPilote adapter = new AdapterPilote(Pilote, getApplicationContext());

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