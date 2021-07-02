package com.example.projetAndroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class adapterUser extends ArrayAdapter<User> {



    private List<User> User;
    private Context mCtx;


    public adapterUser(List<com.example.projetAndroid.User> User, Context mCtx){
        super(mCtx, R.layout.table_user, User);
        this.User = User;
        this.mCtx = mCtx;

    }

    //this method returns the list view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.table_user, null, true);
        listViewItem.setFocusable(false);
        //getting text views
        TextView NomUtilisateur = listViewItem.findViewById(R.id.NomUtilisateur);
        TextView Mail  = listViewItem.findViewById(R.id.Mail);
        TextView Role  = listViewItem.findViewById(R.id.Role);

        User user = User.get(position);

        //setting the textview values
        NomUtilisateur.setText(user.getNomUtilisateur());
        Mail.setText(user.getMail());
        Role.setText(user.getRole());

        return listViewItem;

    }

}



