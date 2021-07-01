package com.example.projetAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ListActivateUser extends AppCompatActivity {

    ListView listView;
    SharedPreferences sharedpreferences;
    List<Utilisateur> Utilisateur;
    TextView msgEmpty;
    private  String API_URL;
    private static final int MENU_ITEM_ACTIVATE = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_user);

        API_URL=getString(R.string.api_link)+"/api_Aerosoft/api/crudUsers/inactiveUser.php";
        Log.i("message", "create.");
        listView = (ListView) findViewById(R.id.listView);

        msgEmpty = (TextView) findViewById(R.id.emptyElement);

        Utilisateur= new ArrayList<>();

        // Affichage liste des utilisateurs
        extractUsers();

        registerForContextMenu(listView);
    }
    private void extractUsers() {

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

                            // Verification du contenu de la liste
                            if (obj.getString("utilisateurs").equals("No record found.")){
                                msgEmpty.setVisibility(View.VISIBLE);
                            }
                            Log.i("msg",obj.toString());

                            JSONArray usersToActivateArray = obj.getJSONArray("utilisateurs");
                            Log.i("msg",usersToActivateArray.toString());

                            // Pour chaque Utilisateur dans l'array récupéré
                            for (int i = 0; i < usersToActivateArray.length(); i++) {
                                JSONObject usersToActivateObject = usersToActivateArray.getJSONObject(i);

                                Utilisateur utilisateur = new Utilisateur(
                                        usersToActivateObject.getString("IdUtilisateur"),
                                        usersToActivateObject.getString("Nom"),
                                        usersToActivateObject.getString("Prenom"),
                                        usersToActivateObject.getString("Mail"),
                                        usersToActivateObject.getString("MotDePasse"),
                                        usersToActivateObject.getString("Statut"),
                                        usersToActivateObject.getString("IdRole")


                                );

                                Utilisateur.add(utilisateur);

                            }
                            // Ajouter les utilisateur à listView
                            AdapterUtilisateurActivate adapter = new AdapterUtilisateurActivate(Utilisateur, getApplicationContext());

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
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Menu de sélection");

        menu.add(0, MENU_ITEM_ACTIVATE , 0, "Activer");
    }
    // Selection dans le menu contextuelle
    @Override
    public boolean onContextItemSelected(MenuItem item){
        // position de l'élément User selectionné
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Utilisateur selectedUser = (Utilisateur) this.listView.getItemAtPosition(info.position);

        if(item.getItemId()==MENU_ITEM_ACTIVATE){
            ActiveUser(selectedUser);
        }

        return true;
    }
    private void ActiveUser(Utilisateur selectedUser){

        // Requette POST
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(ListActivateUser.this);
            String URL = getString(R.string.api_link)+"/api_Aerosoft/api/crudUsers/activateUser.php";
            JSONObject jsonBody = new JSONObject();
            String IdUtilisateur = String.valueOf(selectedUser.getIdUtilisateur());

            // Données à envoyées
            jsonBody.put("IdUtilisateur", IdUtilisateur);
            //

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                // Succès
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    Toast toast = Toast.makeText(getApplicationContext(), "Succès : L'utilisateur a été activée.", Toast.LENGTH_SHORT);

                    toast.show();

                    // Relancer l'activité
                    recreate();

                }
                //

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}