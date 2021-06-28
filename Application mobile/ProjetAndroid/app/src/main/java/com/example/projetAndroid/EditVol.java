package com.example.projetAndroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

public class EditVol extends AppCompatActivity {
    String API_URL;
    TextView NumVolBD;
    EditText AeroportDeptBD,HDepartBD,AeroportArrBD,HArriveeBD;
    Button Modifier,Retour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_vol);
        //session exemple
        SharedPreferences sharedpreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        String sessionNumVol = sharedpreferences.getString("numero Vol", "not Found");
        Toast toast = Toast.makeText(getApplicationContext(), "NumVol = " + sessionNumVol, Toast.LENGTH_SHORT);
        toast.show();
        //
        Intent i = getIntent();
        String NumVol = i.getStringExtra("NumVol");


        API_URL = "http://192.168.1.137:80/api_Aerosoft/api/crudVol/single_read.php?NumVol=" + NumVol;
        Log.i("message : url = ", API_URL);

        NumVolBD = (TextView) findViewById(R.id.NumVolBD);
        AeroportDeptBD = (EditText) findViewById(R.id.AeroportDeptBD);
        HDepartBD = (EditText) findViewById(R.id.HDepartBD);
        AeroportArrBD = (EditText) findViewById(R.id.AeroportArrBD);
        HArriveeBD = (EditText) findViewById(R.id.HArriveeBD);
        Retour = (Button) findViewById(R.id.Retour);
        Retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditVol.this,listeVol.class);


                startActivity(i);
            }
        });

        extractVol();
        if (!sessionNumVol.equals("not Found")) {
            // Modifier = (Button) findViewById(R.id.Modifier);
            Modifier = new Button(this);
            Modifier.setText("Modifier");

            //Modifier.setid
            RelativeLayout editLayout = (RelativeLayout) findViewById(R.id.editLayout);

            RelativeLayout.LayoutParams editLayoutP = new RelativeLayout.LayoutParams(300, 150);
            editLayoutP.addRule(RelativeLayout.BELOW, R.id.HArriveeBD);
            editLayoutP.addRule(RelativeLayout.RIGHT_OF, R.id.Retour);
            editLayoutP.setMargins(50, 50, 50, 50);
            Modifier.setPadding(20, 20, 20, 20);
            //border
            GradientDrawable shape = new GradientDrawable();
            shape.setShape(GradientDrawable.RECTANGLE);
            shape.setCornerRadius(10);
            shape.setColor(Color.rgb(98, 0, 238));
            Modifier.setBackground(shape);
            //
           //Modifier.setBackgroundResource(R.color.purple_500);
            Modifier.setTextSize(18);

            Modifier.setTextColor(Color.WHITE);




            editLayout.addView(Modifier, editLayoutP);
            // editLayout.setPadding(80, 50, 50, 50);

       /* ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) editLayout.getLayoutParams();
        params.width = 200; params.leftMargin = 100; params.topMargin = 200;
        Modifier.setLayoutParams(params);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(30, 10, 10, 10);
        Modifier.setLayoutParams(params);*/

            //Modifier.setTextColor(R.color.purple_500);
            //Modifier.setWidth(100);
            Modifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // url to post our data

                    try {
                        RequestQueue requestQueue = Volley.newRequestQueue(EditVol.this);
                        String URL = "http://192.168.1.137:80/api_Aerosoft/api/crudVol/update.php";
                        JSONObject jsonBody = new JSONObject();
                        String NumVol = String.valueOf(NumVolBD.getText());
                        String AeroportDept = String.valueOf(AeroportDeptBD.getText());
                        String HDepart = String.valueOf(HDepartBD.getText());/*+":00"*/
                        String AeroportArr = String.valueOf(AeroportArrBD.getText());
                        String HArrivee = String.valueOf(HArriveeBD.getText());/*+":00"*/
                        jsonBody.put("NumVol", NumVol);
                        jsonBody.put("AeroportDept", AeroportDept);
                        jsonBody.put("HDepart", HDepart);
                        jsonBody.put("AeroportArr", AeroportArr);
                        jsonBody.put("HArrivee", HArrivee);
                        final String requestBody = jsonBody.toString();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("VOLLEY", response);
                                Toast toast = Toast.makeText(getApplicationContext(), "Succès : Le vol a été modifié.", Toast.LENGTH_SHORT);

                                toast.show();
                            }
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
            });
        }
    }
   /* private Map<String, String> checkjsonBody(Map<String, String> map){
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
            if(pairs.getValue()==null){
                map.put(pairs.getKey(), "");
            }
        }
        return map;
    }*/
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
                            HDepartBD.setText(obj.getString("HDepart")/*.substring(0,5)*/);
                            AeroportArrBD.setText(obj.getString("AeroportArr"));
                            HArriveeBD.setText(obj.getString("HArrivee")/*.substring(0,5)*/);
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