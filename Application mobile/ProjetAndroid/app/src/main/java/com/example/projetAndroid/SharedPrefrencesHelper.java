package com.example.projetAndroid;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefrencesHelper {
    private SharedPreferences sharedPreferences;
    private Context context;
    private String IdUtilisateur = "IdUtilisateur", IdRole = "IdRole";
    public SharedPrefrencesHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences("login_session",
                Context.MODE_PRIVATE);
        this.context = context;
    }
    public String getIdUtilisateur() {
        return sharedPreferences.getString(IdUtilisateur, "");
    }
    public String getIdRole() {
        return sharedPreferences.getString(IdRole, "");
    }

    public void setIdUtilisateur(String IdUtilisateur) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.IdUtilisateur, IdUtilisateur);
        edit.commit();
    }
    public void setIdRole(String IdRole) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.IdRole, IdRole);
        edit.commit();
    }

}