package com.example.projetAndroid;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class AjoutVol extends AppCompatActivity  {


    EditText HDepartBD, HArriveeBD;
    Spinner AeroportArrBD, AeroportDeptBD;
    Button Enregistrer, Retour,Menu;
    Button Vol,Avion,Pilote,Affectation,Utlisateur,Deconnexion;

    //String[] aeroprtsArray;
    ArrayList<String> aeroprtsArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_vol);
        //CreationMenu();
        AeroportDeptBD = (Spinner) findViewById(R.id.AeroportDeptBD);
        HDepartBD = (EditText) findViewById(R.id.HDepartBD);
        AeroportArrBD = (Spinner) findViewById(R.id.AeroportArrBD);
        HArriveeBD = (EditText) findViewById(R.id.HArriveeBD);
        Retour = (Button) findViewById(R.id.Retour);
        Enregistrer = (Button) findViewById(R.id.Enregistrer);
        Menu = (Button) findViewById(R.id.Menu);
        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MenuBar.class));

            }
        });


        // Bouton Retour
        Retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AjoutVol.this, listeVol.class);


                startActivity(i);
            }
        });







            Enregistrer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreateVol();
                }
            });
        extractAeroports();
        AeroportDeptBD.setSelection(0);
        AeroportArrBD.setSelection(0);
        //Log.i("*******AeroportDeptBD val 1 = "," "+AeroportDeptBD.getItemAtPosition(0));
        }


    private void CreateVol() {
        try {

            // Requette POST
            RequestQueue requestQueue = Volley.newRequestQueue(AjoutVol.this);
            String URL = getString(R.string.api_link)+"/api_Aerosoft/api/crudVol/create.php";
            // Données à envoiyées
            JSONObject jsonBody = new JSONObject();

            String AeroportDept = String.valueOf(AeroportDeptBD.getSelectedItem());
            String HDepart = String.valueOf(HDepartBD.getText() + ":00");
            String AeroportArr = String.valueOf(AeroportArrBD.getSelectedItem());
            String HArrivee = String.valueOf(HArriveeBD.getText() + ":00");
            Log.i("*******************dept selected = ",AeroportDept);

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
                    Toast toast;
                    if(response.equals("201")) {
                        toast = Toast.makeText(getApplicationContext(), "Succès : Le vol a été crée.", Toast.LENGTH_SHORT);

                    }
                    else {
                        toast = Toast.makeText(getApplicationContext(), "Error : Le vol existe déjà dans la base.", Toast.LENGTH_SHORT);

                    }
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


    private void extractAeroports() {

        // Requette GET
        String URL = getString(R.string.api_link)+"/api_Aerosoft/api/crudAeroport/read.php";
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
                            AeroportDeptBD.setSelection(0);
                            AeroportArrBD.setSelection(0);

                            Log.i("*******AeroportDeptBD selected = "," "+AeroportDeptBD.getSelectedItem());
                            adapterSpinner();
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
private void adapterSpinner(){

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
}
    private void CreationMenu() {
        SharedPreferences sharedpreferences = getSharedPreferences("login_session", Context.MODE_PRIVATE);
        String sessionIdRole = sharedpreferences.getString("IdRole", "not Found");
        // Création bouton Vol
        Vol = new Button(this);
        Vol.setText("Vol");

        // Layout dans lequel le bouton est mis
        RelativeLayout editLayout = (RelativeLayout) findViewById(R.id.addVolLayout);


        // Width et height du bouton
        RelativeLayout.LayoutParams editLayoutP = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        // Mettre le bouton au dessous de l'EditText "HArriveeBD"

        // Margins du bouton
        // editLayoutP.setMargins(50, 50, 50, 50);
        // Paddings du bouton
        Vol.setPadding(20, 20, 20, 20);
        // Border radius
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        //shape.setCornerRadius(10);
        // Couleur background
        shape.setColor(Color.rgb(98, 0, 238));
        Vol.setBackground(shape);

        // Taille texte
        Vol.setTextSize(8);

        // Couleur texte
        Vol.setTextColor(Color.WHITE);

        // Ajout du bouton au layout avec ses paramétres
        editLayout.addView(Vol, editLayoutP);
        Vol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), listeVol.class));
            }
        });
        if (sessionIdRole.equals("11111")) {
            //Creation Avion

            Avion = new Button(this);
            Avion.setText("Avion");

            // Layout dans lequel le bouton est mis
            RelativeLayout editLayoutAvion = (RelativeLayout) findViewById(R.id.MenuBar);


            // Width et height du bouton
            RelativeLayout.LayoutParams editLayoutPAvion = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            // Mettre le bouton au dessous de l'EditText "HArriveeBD"


            // Paddings du bouton
            Avion.setPadding(20, 20, 20, 20);
            // Border radius
            GradientDrawable shapeAvion = new GradientDrawable();
            shapeAvion.setShape(GradientDrawable.RECTANGLE);
            //shape.setCornerRadius(10);
            // Couleur background
            shapeAvion.setColor(Color.rgb(98, 0, 238));
            Avion.setBackground(shapeAvion);

            // Taille texte
            Avion.setTextSize(8);

            // Couleur texte
            Avion.setTextColor(Color.WHITE);

            // Ajout du bouton au layout avec ses paramétres
            editLayoutAvion.addView(Avion, editLayoutPAvion);
            Avion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(), listeAvion.class));
                }
            });
        }
        //Creation Pilote

        Pilote = new Button(this);
        Pilote.setText("Pilote");

        // Layout dans lequel le bouton est mis
        RelativeLayout editLayoutPilote = (RelativeLayout) findViewById(R.id.MenuBar);


        // Width et height du bouton
        RelativeLayout.LayoutParams editLayoutPPilote = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        // Mettre le bouton au dessous de l'EditText "HArriveeBD"


        // Paddings du bouton
        Pilote.setPadding(20, 20, 20, 20);
        // Border radius
        GradientDrawable shapePilote = new GradientDrawable();
        shapePilote.setShape(GradientDrawable.RECTANGLE);
        //shape.setCornerRadius(10);
        // Couleur background
        shapePilote.setColor(Color.rgb(98, 0, 238));
        Pilote.setBackground(shapePilote);

        // Taille texte
        Pilote.setTextSize(8);

        // Couleur texte
        Pilote.setTextColor(Color.WHITE);
        // Ajout du bouton au layout avec ses paramétres
        editLayoutPilote.addView(Pilote, editLayoutPPilote);
        //Creation Affectation
        Pilote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), listePilote.class));
            }
        });
        if (sessionIdRole.equals("11111")) {
            Affectation = new Button(this);
            Affectation.setText("Affectation");

            // Layout dans lequel le bouton est mis
            RelativeLayout editLayoutAffectation = (RelativeLayout) findViewById(R.id.MenuBar);


            // Width et height du bouton
            RelativeLayout.LayoutParams editLayoutPAffectation = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            // Mettre le bouton au dessous de l'EditText "HArriveeBD"


            // Paddings du bouton
            Affectation.setPadding(20, 20, 20, 20);
            // Border radius
            GradientDrawable shapeAffectation = new GradientDrawable();
            shapeAffectation.setShape(GradientDrawable.RECTANGLE);
            //shape.setCornerRadius(10);
            // Couleur background
            shapeAffectation.setColor(Color.rgb(98, 0, 238));
            Affectation.setBackground(shapeAffectation);

            // Taille texte
            Affectation.setTextSize(8);

            // Couleur texte
            Affectation.setTextColor(Color.WHITE);
            // Ajout du bouton au layout avec ses paramétres
            editLayoutAffectation.addView(Affectation, editLayoutPAffectation);
            Affectation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(), listeAffectation.class));
                }
            });
        }
        if (sessionIdRole.equals("55555")) {
            //Creation Utlisateur

            Utlisateur = new Button(this);
            Utlisateur.setText("Utlisateur");

            // Layout dans lequel le bouton est mis
            RelativeLayout editLayoutUtlisateur = (RelativeLayout) findViewById(R.id.MenuBar);


            // Width et height du bouton
            RelativeLayout.LayoutParams editLayoutPUtlisateur = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            // Mettre le bouton au dessous de l'EditText "HArriveeBD"


            // Paddings du bouton
            Utlisateur.setPadding(20, 20, 20, 20);
            // Border radius
            GradientDrawable shapeUtlisateur = new GradientDrawable();
            shapeUtlisateur.setShape(GradientDrawable.RECTANGLE);
            //shape.setCornerRadius(10);
            // Couleur background
            shapeUtlisateur.setColor(Color.rgb(98, 0, 238));
            Utlisateur.setBackground(shapeUtlisateur);

            // Taille texte
            Utlisateur.setTextSize(7);

            // Couleur texte
            Utlisateur.setTextColor(Color.WHITE);
            // Ajout du bouton au layout avec ses paramétres
            editLayoutUtlisateur.addView(Utlisateur, editLayoutPUtlisateur);
            Utlisateur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(), ListActivateUser.class));

                }
            });
        }
        // Création bouton Deconnexion
        Deconnexion = new Button(this);
        Deconnexion.setText("Déconnexion");

        // Layout dans lequel le bouton est mis
        RelativeLayout editLayoutDeconnexion = (RelativeLayout) findViewById(R.id.MenuBar);


        // Width et height du bouton
        RelativeLayout.LayoutParams editLayoutPDeconnexion = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        // Mettre le bouton au dessous de l'EditText "HArriveeBD"

        // Margins du bouton
        // editLayoutP.setMargins(50, 50, 50, 50);
        // Paddings du bouton
        Deconnexion.setPadding(20, 20, 20, 20);
        // Border radius
        GradientDrawable shapeDeconnexion = new GradientDrawable();
        shapeDeconnexion.setShape(GradientDrawable.RECTANGLE);
        //shape.setCornerRadius(10);
        // Couleur background
        shapeDeconnexion.setColor(Color.rgb(98, 0, 238));
        Deconnexion.setBackground(shape);

        // Taille texte
        Deconnexion.setTextSize(5);

        // Couleur texte
        Deconnexion.setTextColor(Color.WHITE);

        // Ajout du bouton au layout avec ses paramétres
        editLayoutDeconnexion.addView(Deconnexion, editLayoutPDeconnexion);
        Deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences =getSharedPreferences("llogin_session",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getBaseContext(),LoginActivity.class));
            }
        });
    }
}

