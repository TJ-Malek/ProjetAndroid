package com.example.projetAndroid;

public class Utilisateur {
    private String IdUtilisateur;
    private String Nom;
    private String Prenom;
    private String Mail;
    private String MotDePasse;
    private String Statut;
    private String IdRole;

    public Utilisateur(String idUtilisateur, String nom, String prenom, String mail, String motDePasse, String statut, String idRole) {
        IdUtilisateur = idUtilisateur;
        Nom = nom;
        Prenom = prenom;
        Mail = mail;
        MotDePasse = motDePasse;
        Statut = statut;
        IdRole = idRole;
    }

    public String getIdUtilisateur() {
        return IdUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        IdUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getMotDePasse() {
        return MotDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        MotDePasse = motDePasse;
    }

    public String getStatut() {
        return Statut;
    }

    public void setStatut(String statut) {
        Statut = statut;
    }

    public String getIdRole() {
        return IdRole;
    }

    public void setIdRole(String idRole) {
        IdRole = idRole;
    }

}
