package com.example.projetAndroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirstMenu extends AppCompatActivity {
    Button Aerosoft,Reseau;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_menu);
        Aerosoft = (Button) findViewById(R.id.Aerosoft);
        Reseau = (Button) findViewById(R.id.Reseau);

        Aerosoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
            }
        });
        Reseau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), listeUser.class));
            }
        });
    }
}
