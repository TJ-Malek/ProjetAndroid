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
        super(mCtx, R.layout.table_vol,Vol);
        this.Vol = Vol;
        this.mCtx = mCtx;

    }

    //this method returns the list view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.table_vol, null, true);

        //getting text views
        TextView NumVol = listViewItem.findViewById(R.id.NumVol);
        TextView AeroportDept  = listViewItem.findViewById(R.id.AeroportDept);
        TextView HDepart  = listViewItem.findViewById(R.id.HDepart);
        TextView AeroportArr  = listViewItem.findViewById(R.id.AeroportArr);
        TextView HArrivee   = listViewItem.findViewById(R.id.HArrivee);
        //getting the circle for the specific possition
        Vol vol = Vol.get(position);

        //setting the textview values
        NumVol.setText(vol.getNumVol());
        AeroportDept.setText(vol.getAeroportDept());
        HDepart.setText(vol.getHDepart().substring(0,5));
        AeroportArr.setText(vol.getAeroportArr());
        HArrivee.setText(vol.getHArrivee().substring(0,5));
       // tvDesc.setText(vol.getEmail());

        return listViewItem;

    }

}


