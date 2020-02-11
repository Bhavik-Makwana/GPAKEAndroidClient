package com.example.myapplication;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class RoundOneResponse {
    private  HashMap<Long, HashMap<Long, BigInteger>> aij = new HashMap<>();
    private  HashMap<Long, HashMap<Long, BigInteger>> gPowAij = new HashMap<>();
    private  HashMap<Long, HashMap<Long, ArrayList<BigInteger>>> schnorrZKPaij = new HashMap<>();
    private  HashMap<Long, HashMap<Long, BigInteger>> bij = new HashMap<>();
    private  HashMap<Long, HashMap<Long, BigInteger>> gPowBij = new HashMap<>();
    private  HashMap<Long, HashMap<Long, ArrayList<BigInteger>>> schnorrZKPbij = new HashMap<>();
    private  HashMap<Long, BigInteger> yi = new HashMap<>();
    private  HashMap<Long, BigInteger> gPowYi = new HashMap<>();
    private  HashMap<Long, BigInteger> gPowZi = new HashMap<>();
    private  HashMap<Long, ArrayList<BigInteger>> schnorrZKPyi = new HashMap<>();
    private  ArrayList<String> signerID = new ArrayList<>();

    public HashMap<Long, HashMap<Long, BigInteger>> getAij() {
        return aij;
    }

    public void setAij(HashMap<Long, HashMap<Long, BigInteger>> aij) {
        this.aij = aij;
    }

    public HashMap<Long, HashMap<Long, BigInteger>> getgPowAij() {
        return gPowAij;
    }

    public void setgPowAij(HashMap<Long, HashMap<Long, BigInteger>> gPowAij) {
        this.gPowAij = gPowAij;
    }

    public HashMap<Long, HashMap<Long, ArrayList<BigInteger>>> getSchnorrZKPaij() {
        return schnorrZKPaij;
    }

    public void setSchnorrZKPaij(HashMap<Long, HashMap<Long, ArrayList<BigInteger>>> schnorrZKPaij) {
        this.schnorrZKPaij = schnorrZKPaij;
    }

    public HashMap<Long, HashMap<Long, BigInteger>> getBij() {
        return bij;
    }

    public void setBij(HashMap<Long, HashMap<Long, BigInteger>> bij) {
        this.bij = bij;
    }

    public HashMap<Long, HashMap<Long, BigInteger>> getgPowBij() {
        return gPowBij;
    }

    public void setgPowBij(HashMap<Long, HashMap<Long, BigInteger>> gPowBij) {
        this.gPowBij = gPowBij;
    }

    public HashMap<Long, HashMap<Long, ArrayList<BigInteger>>> getSchnorrZKPbij() {
        return schnorrZKPbij;
    }

    public void setSchnorrZKPbij(HashMap<Long, HashMap<Long, ArrayList<BigInteger>>> schnorrZKPbij) {
        this.schnorrZKPbij = schnorrZKPbij;
    }

    public HashMap<Long, BigInteger> getYi() {
        return yi;
    }

    public void setYi(HashMap<Long, BigInteger> yi) {
        this.yi = yi;
    }

    public HashMap<Long, BigInteger> getgPowYi() {
        return gPowYi;
    }

    public void setgPowYi(HashMap<Long, BigInteger> gPowYi) {
        this.gPowYi = gPowYi;
    }

    public HashMap<Long, BigInteger> getgPowZi() {
        return gPowZi;
    }

    public void setgPowZi(HashMap<Long, BigInteger> gPowZi) {
        this.gPowZi = gPowZi;
    }

    public HashMap<Long, ArrayList<BigInteger>> getSchnorrZKPyi() {
        return schnorrZKPyi;
    }

    public void setSchnorrZKPyi(HashMap<Long, ArrayList<BigInteger>> schnorrZKPyi) {
        this.schnorrZKPyi = schnorrZKPyi;
    }

    public ArrayList<String> getSignerID() {
        return signerID;
    }

    public void setSignerID(ArrayList<String> signerID) {
        this.signerID = signerID;
    }
}
