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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class listeUser extends AppCompatActivity {
    ListView listView;
    SharedPreferences sharedpreferences;
    List<User> User;
    TextView msgEmpty;
    Button Vol,Avion,UserB,Affectation,Utlisateur,Deconnexion;

    private String API_URL;
    private static final int MENU_ITEM_EDIT = 111;
    private static final int MENU_ITEM_DELETE = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_user);

        //CreationMenu();
        API_URL=getString(R.string.api_link)+"/api_Reseau_social/api/crudUsers/read.php";
        Log.i("message", "create.");
        listView = (ListView) findViewById(R.id.listView);

        User= new ArrayList<>();

        msgEmpty = (TextView) findViewById(R.id.emptyElement);

        // Affichage liste des vols
        extractUser();

        // Enregistrement du menu contextuelle dans la vue listView
        registerForContextMenu(listView);
    }
    private void extractUser() {

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
                            if (obj.getString("utilisateur").equals("No record found.")){
                                msgEmpty.setVisibility(View.VISIBLE);
                            }

                            JSONArray userArray = obj.getJSONArray("utilisateur");
                            //JSONArray userMsg = obj.getJSONArray("message");
                            Log.i("msg",userArray.toString());

                            // Pour chaque user dans l'array récupéré
                            for (int i = 0; i < userArray.length(); i++) {
                                JSONObject userObject = userArray.getJSONObject(i);
                                User user = new User(
                                        userObject.getString("Role"),
                                        userObject.getString("NomUtilisateur"),
                                        userObject.getString("Mail")

                                );

                                User.add(user);

                            }
                            // Ajouter les users à listView
                            adapterUser adapterUser = new adapterUser(User, getApplicationContext());

                            listView.setAdapter(adapterUser);

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
        //Creation User

        UserB = new Button(this);
        UserB.setText("User");

        // Layout dans lequel le bouton est mis
        LinearLayout editLayoutUser = (LinearLayout) findViewById(R.id.MenuBar);


        // Width et height du bouton
        LinearLayout.LayoutParams editLayoutPUser = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        // Mettre le bouton au dessous de l'EditText "HArriveeBD"


        // Paddings du bouton
        UserB.setPadding(20, 20, 20, 20);
        // Border radius
        GradientDrawable shapeUser = new GradientDrawable();
        shapeUser.setShape(GradientDrawable.RECTANGLE);
        //shape.setCornerRadius(10);
        // Couleur background
        shapeUser.setColor(Color.rgb(98, 0, 238));
        UserB.setBackground(shapeUser);

        // Taille texte
        UserB.setTextSize(8);

        // Couleur texte
        UserB.setTextColor(Color.WHITE);
        // Ajout du bouton au layout avec ses paramétres
        editLayoutUser.addView(UserB, editLayoutPUser);
        //Creation Affectation
        UserB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), listeUser.class));
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