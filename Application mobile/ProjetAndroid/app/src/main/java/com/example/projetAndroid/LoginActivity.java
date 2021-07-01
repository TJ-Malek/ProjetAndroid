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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView registerTV;
    EditText user, password;
    Button loginBtn;
    private RequestQueue rQueue;
    private SharedPrefrencesHelper sharedPrefrencesHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //registerTV = findViewById(R.id.registerTV);
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);

        sharedPrefrencesHelper = new SharedPrefrencesHelper(this);
        /*
        // XXXXX clic TextView
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), RegisterActivity.class));
            }
        });

         */
        // XXXXX clic Button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAction();
            }
        });

    }
    private void loginAction() {
        final String userr = user.getText().toString();
        final String pswd = password.getText().toString();
        if (userr.isEmpty()) {
            user.setError("Username or Email is required");
            user.requestFocus();
            return;
        }
        if (pswd.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        // API_URL = getString(R.string.api_link)+"/api_Aerosoft/api/crudUsers/create.php";
        // <string name="api_link">http://10.75.25.176:8080/login160php2</string>
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.api_link) + "/api_Aerosoft/api/crudUsers/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        rQueue.getCache().clear();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            Log.i("loginActivity ligne 85","je suis ici");
                            if (jsonObject.optString("success").equals("1")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("details");
                                sharedPrefrencesHelper.setIdUtilisateur(jsonObject1.getString("IdUtilisateur"));
                                sharedPrefrencesHelper.setIdRole(jsonObject1.getString("IdRole"));


                                Toast.makeText(LoginActivity.this, "Login Successfully! ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getBaseContext(), listeVol.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Mail", userr);
                params.put("MotDePasse", pswd);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(LoginActivity.this);
        rQueue.add(stringRequest);
    }
}