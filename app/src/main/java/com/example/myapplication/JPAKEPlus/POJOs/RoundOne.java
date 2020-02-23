package com.example.myapplication.JPAKEPlus.POJOs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class RoundOne {
    HashMap<Long, BigInteger> aij = new HashMap<>();
    HashMap<Long, BigInteger> gPowAij = new HashMap<>();
    HashMap<Long, ArrayList<BigInteger>> schnorrZKPaij = new HashMap<>();
    HashMap<Long, BigInteger> bij = new HashMap<>();
    HashMap<Long, BigInteger>  gPowBij = new HashMap<>();
    HashMap<Long, ArrayList<BigInteger>> schnorrZKPbij = new HashMap<>();
    BigInteger yi;
    BigInteger gPowYi;
    BigInteger gPowZi;
    ArrayList<BigInteger> schnorrZKPyi = new ArrayList<>();
    String signerID;

    public RoundOne() {
    }

    public HashMap<Long, BigInteger> getAij() {
        return aij;
    }

    public void setAij(HashMap<Long, BigInteger> aij) {
        this.aij = aij;
    }

    public HashMap<Long, BigInteger> getgPowAij() {
        return gPowAij;
    }

    public void setgPowAij(HashMap<Long, BigInteger> gPowAij) {
        this.gPowAij = gPowAij;
    }

    public HashMap<Long, ArrayList<BigInteger>> getSchnorrZKPaij() {
        return schnorrZKPaij;
    }

    public void setSchnorrZKPaij(HashMap<Long, ArrayList<BigInteger>> schnorrZKPaij) {
        this.schnorrZKPaij = schnorrZKPaij;
    }

    public HashMap<Long, BigInteger> getBij() {
        return bij;
    }

    public void setBij(HashMap<Long, BigInteger> bij) {
        this.bij = bij;
    }

    public HashMap<Long, BigInteger> getgPowBij() {
        return gPowBij;
    }

    public void setgPowBij(HashMap<Long, BigInteger> gPowBij) {
        this.gPowBij = gPowBij;
    }

    public HashMap<Long, ArrayList<BigInteger>> getSchnorrZKPbij() {
        return schnorrZKPbij;
    }

    public void setSchnorrZKPbij(HashMap<Long, ArrayList<BigInteger>> schnorrZKPbij) {
        this.schnorrZKPbij = schnorrZKPbij;
    }

    public BigInteger getYi() {
        return yi;
    }

    public void setYi(BigInteger yi) {
        this.yi = yi;
    }

    public BigInteger getgPowYi() {
        return gPowYi;
    }

    public void setgPowYi(BigInteger gPowYi) {
        this.gPowYi = gPowYi;
    }

    public BigInteger getgPowZi() {
        return gPowZi;
    }

    public void setgPowZi(BigInteger gPowZi) {
        this.gPowZi = gPowZi;
    }

    public ArrayList<BigInteger> getSchnorrZKPyi() {
        return schnorrZKPyi;
    }

    public void setSchnorrZKPyi(ArrayList<BigInteger> schnorrZKPyi) {
        this.schnorrZKPyi = schnorrZKPyi;
    }

    public String getSignerID() {
        return signerID;
    }

    public void setSignerID(String signerID) {
        this.signerID = signerID;
    }
}
