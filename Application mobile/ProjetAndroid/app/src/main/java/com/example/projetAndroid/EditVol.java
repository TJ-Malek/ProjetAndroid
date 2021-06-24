package com.example.projetAndroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
import org.json.JSONObject;

import org.json.JSONException;

public class EditVol extends AppCompatActivity {
    String API_URL;
    TextView NumVolBD;
    EditText AeroportDeptBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_vol);
        Intent i = getIntent();
        String NumVol = i.getStringExtra("NumVol").toString();


        API_URL = "http://10.75.25.250:8080/api_Aerosoft/api/crudVol/single_read.php?NumVol="+NumVol;
        Log.i("message : url = ", API_URL);

        NumVolBD = (TextView) findViewById(R.id.NumVolBD);
        AeroportDeptBD = (EditText) findViewById(R.id.AeroportDeptBD);
        extractVol();
    }
    private void extractVol() {





        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {




                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.i("json === ", obj.toString());

                            Log.i("message AeroportDept === ", obj.getString("AeroportDept"));
                            NumVolBD.setText(obj.getString("NumVol"));
                            AeroportDeptBD.setText(obj.getString("AeroportDept"));


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