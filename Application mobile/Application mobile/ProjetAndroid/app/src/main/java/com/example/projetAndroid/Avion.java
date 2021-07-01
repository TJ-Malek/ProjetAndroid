package com.example.projetAndroid;

public class Avion {
    private String NumAvion;
    private String TypeAvion;
    private String BaseAeroport;

    public String getNumAvion() { return NumAvion; }
    public String getTypeAvion() { return TypeAvion; }
    public String getBaseAeroport() { return BaseAeroport; }

    public void setNumAvion(String numAvion) { this.NumAvion = numAvion; }
    public void setTypeAvion(String typeAvion) { this.TypeAvion = typeAvion; }
    public void setBaseAeroport(String baseAeroport) { this.BaseAeroport = baseAeroport; }

    public Avion(String numAvion, String typeAvion, String baseAeroport) {
        this.NumAvion = numAvion;
        this.TypeAvion = typeAvion;
        this.BaseAeroport = baseAeroport;
    }


}
