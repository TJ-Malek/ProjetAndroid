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
    //private static String API_URL="http://api.androidhive.info/Vol/";
    private static String API_URL="http://192.168.1.137:80/api_Aerosoft/api/crudVol/read.php";
    private static final int MENU_ITEM_EDIT = 111;
    private static final int MENU_ITEM_DELETE = 222;
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

        registerForContextMenu(listView);
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
                           /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Log.i("message", "test.");

                                    Vol vol =  Vol.get(position);
                                    //session exemple
                                    sharedpreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();


                                    editor.putString("numero Vol", vol.getNumVol());

                                    editor.commit();
                                    //
                                    Intent i = new Intent(listeVol.this, EditVol.class);
                                    i.putExtra("NumVol", vol.getNumVol());

                                    startActivity(i);

                                } });
*/
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);


        menu.setHeaderTitle("Select The Action");
        menu.setHeaderTitle("Menu de sélection");

        menu.add(0, MENU_ITEM_EDIT , 0, "Modifier");
        menu.add(0, MENU_ITEM_DELETE, 1, "Supprimer");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Vol selectedVol = (Vol) this.listView.getItemAtPosition(info.position);
        if(item.getItemId()==MENU_ITEM_EDIT){
            //session exemple
            sharedpreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();


            editor.putString("numero Vol", selectedVol.getNumVol());

            editor.commit();
            //
            Intent i = new Intent(listeVol.this, EditVol.class);
            i.putExtra("NumVol", selectedVol.getNumVol());

            startActivity(i);


        }
        else if(item.getItemId()==MENU_ITEM_DELETE){
            new AlertDialog.Builder(this)
                    .setMessage("Êtes vous sur de vouloir supprimer le vol " +selectedVol.getNumVol()+" ?")
                    .setCancelable(false)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                RequestQueue requestQueue = Volley.newRequestQueue(listeVol.this);
                                String URL = "http://192.168.1.137:80/api_Aerosoft/api/crudVol/delete.php";
                                JSONObject jsonBody = new JSONObject();
                                String NumVol = String.valueOf(selectedVol.getNumVol());

                                jsonBody.put("NumVol", NumVol);

                                final String requestBody = jsonBody.toString();

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("VOLLEY", response);
                                        Toast toast = Toast.makeText(getApplicationContext(), "Succès : Le vol a été supprimé.", Toast.LENGTH_SHORT);

                                        toast.show();
                                        recreate();
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
                    })
                    .setNegativeButton("Non", null)
                    .show();
        }else{
            return false;
        }
        return true;
    }
}
