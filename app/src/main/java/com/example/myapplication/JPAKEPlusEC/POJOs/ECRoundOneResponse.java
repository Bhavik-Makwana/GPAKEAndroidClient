package com.example.myapplication.JPAKEPlusEC.POJOs;

import com.example.myapplication.JPAKEPlusEC.ZeroKnowledgeProofs.SchnorrZKP;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class ECRoundOneResponse {
    private  HashMap<Long, HashMap<Long, BigInteger>> aij = new HashMap<>();
    private  HashMap<Long, HashMap<Long, byte[]>> gPowAij = new HashMap<>();
    private  HashMap<Long, HashMap<Long, SchnorrZKP>> schnorrZKPaij = new HashMap<>();
    private  HashMap<Long, HashMap<Long, BigInteger>> bij = new HashMap<>();
    private  HashMap<Long, HashMap<Long, byte[]>> gPowBij = new HashMap<>();
    private  HashMap<Long, HashMap<Long, SchnorrZKP>> schnorrZKPbij = new HashMap<>();
    private  HashMap<Long, BigInteger> yi = new HashMap<>();
    private  HashMap<Long, byte[]> gPowYi = new HashMap<>();
    private  HashMap<Long, byte[]> gPowZi = new HashMap<>();
    private  HashMap<Long, SchnorrZKP> schnorrZKPyi = new HashMap<>();
    private  ArrayList<String> signerID = new ArrayList<>();

    public HashMap<Long, HashMap<Long, BigInteger>> getAij() {
        return aij;
    }

    public void setAij(HashMap<Long, HashMap<Long, BigInteger>> aij) {
        this.aij = aij;
    }

    public HashMap<Long, HashMap<Long, byte[]>> getgPowAij() {
        return gPowAij;
    }

    public void setgPowAij(HashMap<Long, HashMap<Long, byte[]>> gPowAij) {
        this.gPowAij = gPowAij;
    }

    public HashMap<Long, HashMap<Long, SchnorrZKP>> getSchnorrZKPaij() {
        return schnorrZKPaij;
    }

    public void setSchnorrZKPaij(HashMap<Long, HashMap<Long, SchnorrZKP>> schnorrZKPaij) {
        this.schnorrZKPaij = schnorrZKPaij;
    }

    public HashMap<Long, HashMap<Long, BigInteger>> getBij() {
        return bij;
    }

    public void setBij(HashMap<Long, HashMap<Long, BigInteger>> bij) {
        this.bij = bij;
    }

    public HashMap<Long, HashMap<Long, byte[]>> getgPowBij() {
        return gPowBij;
    }

    public void setgPowBij(HashMap<Long, HashMap<Long, byte[]>> gPowBij) {
        this.gPowBij = gPowBij;
    }

    public HashMap<Long, HashMap<Long, SchnorrZKP>> getSchnorrZKPbij() {
        return schnorrZKPbij;
    }

    public void setSchnorrZKPbij(HashMap<Long, HashMap<Long, SchnorrZKP>> schnorrZKPbij) {
        this.schnorrZKPbij = schnorrZKPbij;
    }

    public HashMap<Long, BigInteger> getYi() {
        return yi;
    }

    public void setYi(HashMap<Long, BigInteger> yi) {
        this.yi = yi;
    }

    public HashMap<Long, byte[]> getgPowYi() {
        return gPowYi;
    }

    public void setgPowYi(HashMap<Long, byte[]> gPowYi) {
        this.gPowYi = gPowYi;
    }

    public HashMap<Long, byte[]> getgPowZi() {
        return gPowZi;
    }

    public void setgPowZi(HashMap<Long, byte[]> gPowZi) {
        this.gPowZi = gPowZi;
    }

    public HashMap<Long, SchnorrZKP> getSchnorrZKPyi() {
        return schnorrZKPyi;
    }

    public void setSchnorrZKPyi(HashMap<Long, SchnorrZKP> schnorrZKPyi) {
        this.schnorrZKPyi = schnorrZKPyi;
    }

    public ArrayList<String> getSignerID() {
        return signerID;
    }

    public void setSignerID(ArrayList<String> signerID) {
        this.signerID = signerID;
    }
}
