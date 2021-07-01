package com.example.projetAndroid;

public class Affectation {
    private String IdAffectation;
    private String NumVol;
    private String DateVol;
    private String AffectationCode;
    private String NumAvion;
    private String IdPilote;

    public String getIdAffectation() {
        return IdAffectation;
    }

    public void setIdAffectation(String idAffectation) {
        IdAffectation = idAffectation;
    }

    public String getNumVol() {
        return NumVol;
    }

    public void setNumVol(String numVol) {
        NumVol = numVol;
    }

    public String getDateVol() {
        return DateVol;
    }

    public void setDateVol(String dateVol) {
        DateVol = dateVol;
    }

    public String getAffectationCode() {
        return AffectationCode;
    }

    public void setAffectationCode(String affectationCode) {
        AffectationCode = affectationCode;
    }

    public String getNumAvion() {
        return NumAvion;
    }

    public void setNumAvion(String numAvion) {
        NumAvion = numAvion;
    }

    public String getIdPilote() {
        return IdPilote;
    }

    public void setIdPilote(String idPilote) {
        IdPilote = idPilote;
    }

    public Affectation(String idAffectation, String numVol, String dateVol, String numAvion, String idPilote) {
        IdAffectation = idAffectation;
        NumVol = numVol;
        DateVol = dateVol;
        NumAvion = numAvion;
        IdPilote = idPilote;
    }
}
