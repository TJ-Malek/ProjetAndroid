package com.example.projetAndroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class listeVol extends AppCompatActivity {

    ListView listView;

    List<Vol> Vol;
    //private static String API_URL="http://api.androidhive.info/Vol/";
    private static String API_URL="http://10.75.25.250:8080/api_Aerosoft/api/crudVol/read.php";

   // Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_vol);
        Log.i("message", "create.");
      listView = (ListView) findViewById(R.id.listView);
       /*TextView textView = new TextView(context);
        textView.setText("Hello. I'm a header view");

        listView.addHeaderView(textView);*/
        Vol= new ArrayList<>();

        extractVol();


    }

    private void extractVol() {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);


                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray volArray = obj.getJSONArray("vol");
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
                            Adapter adapter = new Adapter(Vol, getApplicationContext());
                            /*adapter.notifyDataSetChanged();
                            listView.invalidateViews();*/
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Log.i("message", "test.");
                                    Vol vol =  Vol.get(position);
                                    Intent i = new Intent(listeVol.this, EditVol.class);
                                    i.putExtra("id", vol.getNumVol());

                                    startActivity(i);

                                } });

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
