package com.example.projetAndroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class EditVol extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    String API_URL;
    TextView NumVolBD;
    EditText HDepartBD, HArriveeBD;
    Spinner AeroportArrBD, AeroportDeptBD;
    Button Modifier, Retour;
    //String[] aeroprtsArray;
    ArrayList<String> aeroprtsArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_vol);
        // Session exemple

        // Récupération de la session "Session"
        SharedPreferences sharedpreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);

        // Récupération de la variable de session
        // valeur "not Found" si aucune valeur n'a été récupérée
        String sessionNumVol = sharedpreferences.getString("numero Vol", "not Found");
        Toast toast = Toast.makeText(getApplicationContext(), "NumVol = " + sessionNumVol, Toast.LENGTH_SHORT);
        toast.show();
        //
        Intent i = getIntent();
        String NumVol = i.getStringExtra("NumVol");


        API_URL = "http://10.75.25.176:8080/api_Aerosoft/api/crudVol/single_read.php?NumVol=" + NumVol;
        Log.i("message : url = ", API_URL);

        NumVolBD = (TextView) findViewById(R.id.NumVolBD);
        AeroportDeptBD = (Spinner) findViewById(R.id.AeroportDeptBD);
        HDepartBD = (EditText) findViewById(R.id.HDepartBD);
        AeroportArrBD = (Spinner) findViewById(R.id.AeroportArrBD);
        HArriveeBD = (EditText) findViewById(R.id.HArriveeBD);
        Retour = (Button) findViewById(R.id.Retour);

        // Bouton Retour
        Retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditVol.this, listeVol.class);


                startActivity(i);
            }
        });

        extractAeroports();
        // Get Vol
       // extractVol();

        // Bouton Modifier crée sous condition ( gestion des roles)
        if (!sessionNumVol.equals("not Found")) {

            // Création bouton Modifier
            Modifier = new Button(this);
            Modifier.setText("Modifier");

            // Layout dans lequel le bouton est mis
            RelativeLayout editLayout = (RelativeLayout) findViewById(R.id.editLayout);


            // Width et height du bouton
            RelativeLayout.LayoutParams editLayoutP = new RelativeLayout.LayoutParams(300, 150);
            // Mettre le bouton au dessous de l'EditText "HArriveeBD"
            editLayoutP.addRule(RelativeLayout.BELOW, R.id.HArriveeBD);
            // Mettre le bouton à droite du bouton "Retour"
            editLayoutP.addRule(RelativeLayout.RIGHT_OF, R.id.Retour);
            // Margins du bouton
            editLayoutP.setMargins(50, 50, 50, 50);
            // Paddings du bouton
            Modifier.setPadding(20, 20, 20, 20);
            // Border radius
            GradientDrawable shape = new GradientDrawable();
            shape.setShape(GradientDrawable.RECTANGLE);
            shape.setCornerRadius(10);
            // Couleur background
            shape.setColor(Color.rgb(98, 0, 238));
            Modifier.setBackground(shape);

            // Taille texte
            Modifier.setTextSize(18);

            // Couleur texte
            Modifier.setTextColor(Color.WHITE);

            // Ajout du bouton au layout avec ses paramétres
            editLayout.addView(Modifier, editLayoutP);

            Modifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateVol();
                }
            });
        }
    }

    private void UpdateVol() {
        try {
            // Requette POST
            RequestQueue requestQueue = Volley.newRequestQueue(EditVol.this);
            String URL = "http://10.75.25.176:8080/api_Aerosoft/api/crudVol/update.php";
            // Données à envoiyées
            JSONObject jsonBody = new JSONObject();
            String NumVol = String.valueOf(NumVolBD.getText());
            String AeroportDept = String.valueOf(AeroportDeptBD.getSelectedItem());
            String HDepart = String.valueOf(HDepartBD.getText() + ":00");
            String AeroportArr = String.valueOf(AeroportArrBD.getSelectedItem());
            String HArrivee = String.valueOf(HArriveeBD.getText() + ":00");
            Log.i("*******************dept selected = ",String.valueOf(AeroportDeptBD.getSelectedItem()));
            jsonBody.put("NumVol", NumVol);
            jsonBody.put("AeroportDept", AeroportDept);
            jsonBody.put("HDepart", HDepart);
            jsonBody.put("AeroportArr", AeroportArr);
            jsonBody.put("HArrivee", HArrivee);
            final String requestBody = jsonBody.toString();
            //

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                // Succès
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    Toast toast = Toast.makeText(getApplicationContext(), "Succès : Le vol a été modifié.", Toast.LENGTH_SHORT);

                    toast.show();
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

    private void extractVol() {

        // GET Aeroports
        //extractAeroports();
        // Requette GET
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {




                            // Le Vol récupéré
                            JSONObject obj = new JSONObject(response);
                            // Affichage du vol
                            NumVolBD.setText(obj.getString("NumVol"));
                            Log.i("aeroprtsArray val 5 = ",aeroprtsArray.get(5));

                            Log.i("AeroportDeptBD val 5 = "," "+AeroportDeptBD.getItemAtPosition(5));
                            AeroportDeptBD.setSelection(aeroprtsArray.indexOf(obj.getString("AeroportDept")));
                            Log.i("*************selectionner = ","posiotion= " +aeroprtsArray.indexOf(obj.getString("AeroportDept")));
                            Log.i("*************aeroprtsArray.get(0)= ","= " +aeroprtsArray.size());

                            Log.i("*******************dept= ",String.valueOf(AeroportDeptBD.getSelectedItem()));
                            // substring(0,5) pour améliorer l'affichage des heures
                            HDepartBD.setText(obj.getString("HDepart").substring(0, 5));
                            AeroportArrBD.setSelection(aeroprtsArray.indexOf(obj.getString("AeroportArr")));
                            HArriveeBD.setText(obj.getString("HArrivee").substring(0, 5));

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
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                aeroprtsArray);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        AeroportDeptBD.setAdapter(ad);
        AeroportArrBD.setAdapter(ad);
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    private void extractAeroports() {

        // Requette GET
        String URL = "http://10.75.25.176:8080/api_Aerosoft/api/crudAeroport/read.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray AeroportArray = obj.getJSONArray("aeroport");

                            // Pour chaque vol dans l'array récupéré
                            for (int i = 0; i < AeroportArray.length(); i++) {
                                JSONObject aeroportObject = AeroportArray.getJSONObject(i);
                                aeroprtsArray.add(aeroportObject.getString("IdAeroport"));
                            }
                            extractVol();
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
    @Override
    public void onItemSelected(AdapterView adapter, View v, int i, long lng) {

        if(v.hasFocus() ){
            Toast.makeText(getApplicationContext(), (CharSequence) AeroportDeptBD.getSelectedItem(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

