package com.example.projetAndroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter extends ArrayAdapter<Vol> {



    private List<Vol> Vol;
    private Context mCtx;


    public Adapter(List<Vol> Vol, Context mCtx){
        super(mCtx, R.layout.cutom_list_layout,Vol);
        this.Vol = Vol;
        this.mCtx = mCtx;

    }

    //this method returns the list view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.cutom_list_layout, null, true);

        //getting text views
        TextView tvName = listViewItem.findViewById(R.id.circleTitle);
        TextView tvDesc = listViewItem.findViewById(R.id.circledescription);

        //getting the circle for the specific possition
        Vol vol = Vol.get(position);

        //setting the textview values
        tvName.setText(vol.getNumVol());
        tvDesc.setText(vol.getAeroportArr() + " " + vol.getHArrivee());
       // tvDesc.setText(vol.getEmail());

        return listViewItem;

    }

}


