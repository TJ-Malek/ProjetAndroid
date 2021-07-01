package com.example.projetAndroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterPilote extends ArrayAdapter<Pilote> {



    private List<Pilote> Pilote;
    private Context mCtx;


    public AdapterPilote(List<com.example.projetAndroid.Pilote> Pilote, Context mCtx){
        super(mCtx, R.layout.table_pilote, Pilote);
        this.Pilote = Pilote;
        this.mCtx = mCtx;

    }

    //this method returns the list view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.table_pilote, null, true);
        listViewItem.setFocusable(false);
        //getting text views
        TextView PrenomPilote = listViewItem.findViewById(R.id.PrenomPilote);
        TextView NomPilote  = listViewItem.findViewById(R.id.NomPilote);
        TextView Matricule  = listViewItem.findViewById(R.id.Matricule);

        Pilote pilote = Pilote.get(position);

        //setting the textview values
        PrenomPilote.setText(pilote.getNomPilote());
        NomPilote.setText(pilote.getPrenomPilote());
        Matricule.setText(pilote.getMatricule());

        return listViewItem;

    }

}


