package com.example.projetAndroid;

public class User {
    private String Role;
    private String NomUtilisateur;
    private String Mail;


    public String getRole() {
        return Role;
    }

    public void setRole(String idUtilisateur) {
        Role = idUtilisateur;
    }

    public String getNomUtilisateur() {
        return NomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        NomUtilisateur = nomUtilisateur;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public User(String idUtilisateur, String nomUtilisateur, String mail) {
        Role = idUtilisateur;
        NomUtilisateur = nomUtilisateur;
        Mail = mail;
    }
}
