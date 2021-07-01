package com.example.projetAndroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterBarMenu extends ArrayAdapter<Vol> {




    private Context mCtx;


    public AdapterBarMenu(List<Vol> Vol, Context mCtx){
        super(mCtx, R.layout.menu_bar);

        this.mCtx = mCtx;

    }

    //this method returns the list view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.menu_bar, null, true);
        listViewItem.setFocusable(false);




        return listViewItem;

    }

}