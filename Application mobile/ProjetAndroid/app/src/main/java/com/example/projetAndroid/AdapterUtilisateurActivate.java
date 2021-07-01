package com.example.projetAndroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterUtilisateurActivate extends ArrayAdapter<Utilisateur> {



    private List<Utilisateur> Utilisateur;
    private Context mCtx;


    public AdapterUtilisateurActivate(List<com.example.projetAndroid.Utilisateur> Utilisateur, Context mCtx){
        super(mCtx, R.layout.table_utilisateur_activate, Utilisateur);
        this.Utilisateur = Utilisateur;
        this.mCtx = mCtx;

    }

    //this method returns the list view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.table_utilisateur_activate, null, true);
        listViewItem.setFocusable(false);
        //getting text views
        TextView IdUtilisateur = listViewItem.findViewById(R.id.IdUtilisateur);
        TextView FullName  = listViewItem.findViewById(R.id.FullName);
        TextView Mail  = listViewItem.findViewById(R.id.Mail);
        TextView Statut  = listViewItem.findViewById(R.id.Statut);

        Utilisateur utilisateur = Utilisateur.get(position);

        //setting the textview values
        IdUtilisateur.setText(utilisateur.getIdUtilisateur());
        FullName.setText(utilisateur.getNom() + " " + utilisateur.getPrenom());
        Mail.setText(utilisateur.getMail());
        Statut.setText(utilisateur.getStatut());

        return listViewItem;

    }

}


