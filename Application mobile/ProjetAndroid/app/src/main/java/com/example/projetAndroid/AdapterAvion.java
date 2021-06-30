package com.example.projetAndroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterAvion extends ArrayAdapter<Avion> {
    private List<Avion> avions;
    private Context mCtx;

    public AdapterAvion(List<Avion> Avions, Context mCtx){
        super(mCtx, R.layout.table_avion);
        this.avions = Avions;
        this.mCtx = mCtx;
    }
    //this method returns the list view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.table_avion, null, true);
        listViewItem.setFocusable(false);
        //getting text views
        TextView  NumAvion = listViewItem.findViewById(R.id.NumAvion);
        TextView TypeAvion  = listViewItem.findViewById(R.id.TypeAvion);
        TextView BaseAeroport  = listViewItem.findViewById(R.id.BaseAeroport);
        //getting the circle for the specific possition
        Avion avion = avions.get(position);

        //setting the textview values
        NumAvion.setText(avion.getNumAvion());
        TypeAvion.setText(avion.getTypeAvion());
        BaseAeroport.setText(avion.getBaseAeroport());

        // tvDesc.setText(vol.getEmail());
        return listViewItem;
    }
}
