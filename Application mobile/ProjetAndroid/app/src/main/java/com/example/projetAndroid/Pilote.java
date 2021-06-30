package com.example.projetAndroid;

public class Pilote {
    private String IdPilote;
    private String NomPilote;
    private String PrenomPilote;
    private String Matricule;

    public Pilote(String idPilote, String nomPilote, String prenomPilote, String matricule) {
        IdPilote = idPilote;
        NomPilote = nomPilote;
        PrenomPilote = prenomPilote;
        Matricule = matricule;
    }
    public String getIdPilote() {
        return IdPilote;
    }

    public void setIdPilote(String idPilote) {
        IdPilote = idPilote;
    }

    public String getNomPilote() {
        return NomPilote;
    }

    public void setNomPilote(String nomPilote) {
        NomPilote = nomPilote;
    }

    public String getPrenomPilote() {
        return PrenomPilote;
    }

    public void setPrenomPilote(String prenomPilote) {
        PrenomPilote = prenomPilote;
    }

    public String getMatricule() {
        return Matricule;
    }

    public void setMatricule(String matricule) {
        Matricule = matricule;
    }
}
