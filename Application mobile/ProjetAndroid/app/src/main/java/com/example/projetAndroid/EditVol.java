package com.example.projetAndroid;

import android.app.ActionBar;
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

public class EditVol extends AppCompatActivity  {
    String API_URL;
    TextView NumVolBD;
    EditText HDepartBD, HArriveeBD;
    Spinner AeroportArrBD, AeroportDeptBD;
    Button Modifier, Retour,Menu;
    Button Vol,Avion,Pilote,Affectation,Utlisateur,Deconnexion;
    //String[] aeroprtsArray;
    ArrayList<String> aeroprtsArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_vol);
        Menu = (Button) findViewById(R.id.Menu);
        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MenuBar.class));

            }
        });
       // CreationMenu();
        // Session exemple

        // Récupération de la session "Session"
        SharedPreferences sharedpreferences = getSharedPreferences("login_session", Context.MODE_PRIVATE);
        String sessionIdRole= sharedpreferences.getString("IdRole", "not Found");

        //
        Intent i = getIntent();
        String NumVol = i.getStringExtra("NumVol");


        API_URL = getString(R.string.api_link)+"/api_Aerosoft/api/crudVol/single_read.php?NumVol=" + NumVol;
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
        if (sessionIdRole.equals("55555")) {

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
            String URL = getString(R.string.api_link)+"/api_Aerosoft/api/crudVol/update.php";
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

    private void CreationMenu() {
        SharedPreferences sharedpreferences = getSharedPreferences("login_session", Context.MODE_PRIVATE);
        String sessionIdRole = sharedpreferences.getString("IdRole", "not Found");
        // Création bouton Vol
        Vol = new Button(this);
        Vol.setText("Vol");

        // Layout dans lequel le bouton est mis
        LinearLayout editLayout = (LinearLayout) findViewById(R.id.MenuBar);


        // Width et height du bouton
        LinearLayout.LayoutParams editLayoutP = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
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
            LinearLayout editLayoutAvion = (LinearLayout) findViewById(R.id.MenuBar);


            // Width et height du bouton
            LinearLayout.LayoutParams editLayoutPAvion = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
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
        LinearLayout editLayoutPilote = (LinearLayout) findViewById(R.id.MenuBar);


        // Width et height du bouton
        LinearLayout.LayoutParams editLayoutPPilote = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
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
            LinearLayout editLayoutAffectation = (LinearLayout) findViewById(R.id.MenuBar);


            // Width et height du bouton
            LinearLayout.LayoutParams editLayoutPAffectation = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
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
            LinearLayout editLayoutUtlisateur = (LinearLayout) findViewById(R.id.MenuBar);


            // Width et height du bouton
            LinearLayout.LayoutParams editLayoutPUtlisateur = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
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
        LinearLayout editLayoutDeconnexion = (LinearLayout) findViewById(R.id.MenuBar);


        // Width et height du bouton
        LinearLayout.LayoutParams editLayoutPDeconnexion = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
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

