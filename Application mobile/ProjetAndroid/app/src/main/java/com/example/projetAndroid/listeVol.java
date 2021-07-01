package com.example.projetAndroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class listeVol extends AppCompatActivity {

    ListView listView;
    SharedPreferences  sharedpreferences;
    List<Vol> Vol;
    FloatingActionButton Ajouter;
    private  String API_URL;
    private static final int MENU_ITEM_EDIT = 111;
    private static final int MENU_ITEM_DELETE = 222;
   // Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_vol);
        API_URL=getString(R.string.api_link)+"/api_Aerosoft/api/crudVol/read.php";
        Log.i("message", "create.");
        listView = (ListView) findViewById(R.id.listView);
        Vol= new ArrayList<>();

        // Affichage liste des vols
        extractVol();
// Création bouton ajouter

        Ajouter = new FloatingActionButton(this);
        // Layout dans lequel le bouton est mis
        RelativeLayout editLayout = (RelativeLayout) findViewById(R.id.listeVol);


        // Width et height du bouton
        RelativeLayout.LayoutParams editLayoutA = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //Ajouter.setBackgroundDrawable();
        Ajouter.setImageDrawable(getResources().getDrawable(R.drawable.ic_input_add,getApplicationContext().getTheme()));
        Ajouter.setBackgroundTintList(getResources().getColorStateList(R.color.purple_500,getApplicationContext().getTheme()));
        editLayoutA.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        editLayoutA.addRule(RelativeLayout.ALIGN_PARENT_END);
        editLayoutA.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        editLayoutA.setMargins(16,16,16,16);
       /* editLayoutA.addRule(Gravity.END);
        editLayoutA.addRule(Gravity.BOTTOM);*/
        editLayout.addView(Ajouter, editLayoutA);
        // Enregistrement du menu contextuelle dans la vue listView
        registerForContextMenu(listView);
    }

    private void extractVol() {

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

                            JSONArray volArray = obj.getJSONArray("vol");

                            // Pour chaque vol dans l'array récupéré
                            for (int i = 0; i < volArray.length(); i++) {
                                JSONObject volObject = volArray.getJSONObject(i);

                                Vol vol = new Vol(
                                        volObject.getString("NumVol"),
                                        volObject.getString("AeroportDept"),
                                        volObject.getString("HDepart"),
                                        volObject.getString("AeroportArr"),
                                        volObject.getString("HArrivee")


                                );

                                Vol.add(vol);

                            }
                            // Ajouter les vol à listView
                            Adapter adapter = new Adapter(Vol, getApplicationContext());

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


        menu.setHeaderTitle("Select The Action");
        menu.setHeaderTitle("Menu de sélection");

        menu.add(0, MENU_ITEM_EDIT , 0, "Modifier");
        menu.add(0, MENU_ITEM_DELETE, 1, "Supprimer");
    }
    // Selection dans le menu contextuelle
    @Override
    public boolean onContextItemSelected(MenuItem item){
        // position de l'élément vol selectionné
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Vol selectedVol = (Vol) this.listView.getItemAtPosition(info.position);
        //

        // Modification
        if(item.getItemId()==MENU_ITEM_EDIT){
            // Session exemple

            // Nom de la session "Session"
            sharedpreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);

            // Editeur de session
            SharedPreferences.Editor editor = sharedpreferences.edit();

            // Création de variable de session
            // editor.putString("clé", valeur);
            editor.putString("numero Vol", selectedVol.getNumVol());

            editor.commit();
            //

            // Redirection vers EditVol
            Intent i = new Intent(listeVol.this, EditVol.class);
            i.putExtra("NumVol", selectedVol.getNumVol());

            startActivity(i);


        }

        // Suppression
        else if(item.getItemId()==MENU_ITEM_DELETE){
            new AlertDialog.Builder(this)
                    .setMessage("Êtes-vous sûr de vouloir supprimer le vol " +selectedVol.getNumVol()+" ?")
                    .setCancelable(false)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DeleteVol(selectedVol);
                        }
                    })
                    .setNegativeButton("Non", null)
                    .show();
        }else{
            return false;
        }
        return true;
    }

    private void DeleteVol(Vol selectedVol){

        // Requette POST
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(listeVol.this);
            String URL = "http://10.75.25.176:8080/api_Aerosoft/api/crudVol/delete.php";
            JSONObject jsonBody = new JSONObject();
            String NumVol = String.valueOf(selectedVol.getNumVol());

            // Données à envoyées
            jsonBody.put("NumVol", NumVol);
            //

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                // Succès
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    Toast toast = Toast.makeText(getApplicationContext(), "Succès : Le vol a été supprimé.", Toast.LENGTH_SHORT);

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
