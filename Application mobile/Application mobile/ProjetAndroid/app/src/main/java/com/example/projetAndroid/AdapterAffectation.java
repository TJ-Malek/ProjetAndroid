package com.example.projetAndroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterAffectation extends ArrayAdapter<Affectation> {



    private List<Affectation> Affectation;
    private Context mCtx;


    public AdapterAffectation(List<Affectation> Affectation, Context mCtx){
        super(mCtx, R.layout.table_affectation,Affectation);
        this.Affectation = Affectation;
        this.mCtx = mCtx;

    }

    //this method returns the list view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.table_affectation, null, true);
        listViewItem.setFocusable(false);
        //getting text views
        TextView IdAffectation = listViewItem.findViewById(R.id.IdAffectation);
        TextView NumVol  = listViewItem.findViewById(R.id.NumVol);
        TextView DateVol  = listViewItem.findViewById(R.id.DateVol);
        TextView NumAvion  = listViewItem.findViewById(R.id.NumAvion);
        TextView IdPilote   = listViewItem.findViewById(R.id.IdPilote);
        //getting the circle for the specific possition
        Affectation affectation = Affectation.get(position);

        //setting the textview values
        IdAffectation.setText(affectation.getIdAffectation());
        NumVol.setText(affectation.getNumVol());
        DateVol.setText(affectation.getDateVol());
        NumAvion.setText(affectation.getNumAvion());
        IdPilote.setText(affectation.getIdPilote());
        // tvDesc.setText(affectation.getEmail());


        return listViewItem;

    }

}


