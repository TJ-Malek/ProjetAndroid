package com.example.projetAndroid;

public class Vol {
    private String NumVol;
    private String AeroportDept;
    private String HDepart;
    private String AeroportArr;
    private String HArrivee;

    public String getNumVol() {
        return NumVol;
    }

    public void setNumVol(String numVol) {
        NumVol = numVol;
    }

    public String getAeroportDept() {
        return AeroportDept;
    }

    public void setAeroportDept(String aeroportDept) {
        AeroportDept = aeroportDept;
    }

    public String getHDepart() {
        return HDepart;
    }

    public void setHDepart(String HDepart) {
        this.HDepart = HDepart;
    }

    public String getAeroportArr() {
        return AeroportArr;
    }

    public void setAeroportArr(String aeroportArr) {
        AeroportArr = aeroportArr;
    }

    public String getHArrivee() {
        return HArrivee;
    }

    public void setHArrivee(String HArrivee) {
        this.HArrivee = HArrivee;
    }

    public Vol(String numVol, String aeroportDept, String HDepart, String aeroportArr, String HArrivee) {
        NumVol = numVol;
        AeroportDept = aeroportDept;
        this.HDepart = HDepart;
        AeroportArr = aeroportArr;
        this.HArrivee = HArrivee;
    }
}
