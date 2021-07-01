package com.example.projetAndroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Inscription extends AppCompatActivity {
    String API_URL;
   // TextView Nom,Prenom,Mail;
    EditText NomBD, PrenomBD,MailBD,MotDePasseBD;
    Button Enregistrer,Retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);
        API_URL = getString(R.string.api_link)+"/api_Aerosoft/api/crudUsers/create.php";
        NomBD = (EditText) findViewById(R.id.NomBD);
        PrenomBD = (EditText) findViewById(R.id.PrenomBD);
        MailBD = (EditText) findViewById(R.id.MailBD);
        MotDePasseBD = (EditText) findViewById(R.id.MotDePasseBD);
        Enregistrer = (Button) findViewById(R.id.Enregistrer);
        Retour = (Button) findViewById(R.id.Retour);
        Retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Inscription.this, LoginActivity.class);
                startActivity(i);
            }
        });
        Enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateUser();
            }
        });
    }
    private void CreateUser(){
        try {
            // Requette POST
            RequestQueue requestQueue = Volley.newRequestQueue(Inscription.this);

            // Données à envoiyées
            JSONObject jsonBody = new JSONObject();
            String Nom = String.valueOf(NomBD.getText());
            String Prenom = String.valueOf(PrenomBD.getText());
            String Mail = String.valueOf(MailBD.getText());
            String MotDePasse = String.valueOf(MotDePasseBD.getText());

            jsonBody.put("Nom", Nom);
            jsonBody.put("Prenom", Prenom);
            jsonBody.put("Mail", Mail);
            jsonBody.put("MotDePasse", MotDePasse);

            final String requestBody = jsonBody.toString();
            //

            StringRequest stringRequest = new StringRequest(Request.Method.POST,  API_URL, new Response.Listener<String>() {
                // Succès
                @Override
                public void onResponse(String response) {
                    Log.i("response=========== ",response);
                    Toast toast;
                    if(response.equals("201")) {
                        toast = Toast.makeText(getApplicationContext(), "Succès : L'utilisateur a été enregistré.", Toast.LENGTH_SHORT);

                    } else {
                        toast = Toast.makeText(getApplicationContext(), "Erreur : existe déjà dans la base.", Toast.LENGTH_SHORT);

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
}
