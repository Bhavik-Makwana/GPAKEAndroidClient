package com.example.myapplication.JPAKEPlus.POJOs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class RoundOneResponse {
    private  ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> aij = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> gPowAij = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, ConcurrentHashMap<Long, ArrayList<BigInteger>>> schnorrZKPaij = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> bij = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> gPowBij = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, ConcurrentHashMap<Long, ArrayList<BigInteger>>> schnorrZKPbij = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, BigInteger> yi = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, BigInteger> gPowYi = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, BigInteger> gPowZi = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, ArrayList<BigInteger>> schnorrZKPyi = new ConcurrentHashMap<>();
    private  ArrayList<String> signerID = new ArrayList<>();

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> getAij() {
        return aij;
    }

    public void setAij(ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> aij) {
        this.aij = aij;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> getgPowAij() {
        return gPowAij;
    }

    public void setgPowAij(ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> gPowAij) {
        this.gPowAij = gPowAij;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, ArrayList<BigInteger>>> getSchnorrZKPaij() {
        return schnorrZKPaij;
    }

    public void setSchnorrZKPaij(ConcurrentHashMap<Long, ConcurrentHashMap<Long, ArrayList<BigInteger>>> schnorrZKPaij) {
        this.schnorrZKPaij = schnorrZKPaij;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> getBij() {
        return bij;
    }

    public void setBij(ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> bij) {
        this.bij = bij;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> getgPowBij() {
        return gPowBij;
    }

    public void setgPowBij(ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> gPowBij) {
        this.gPowBij = gPowBij;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, ArrayList<BigInteger>>> getSchnorrZKPbij() {
        return schnorrZKPbij;
    }

    public void setSchnorrZKPbij(ConcurrentHashMap<Long, ConcurrentHashMap<Long, ArrayList<BigInteger>>> schnorrZKPbij) {
        this.schnorrZKPbij = schnorrZKPbij;
    }

    public ConcurrentHashMap<Long, BigInteger> getYi() {
        return yi;
    }

    public void setYi(ConcurrentHashMap<Long, BigInteger> yi) {
        this.yi = yi;
    }

    public ConcurrentHashMap<Long, BigInteger> getgPowYi() {
        return gPowYi;
    }

    public void setgPowYi(ConcurrentHashMap<Long, BigInteger> gPowYi) {
        this.gPowYi = gPowYi;
    }

    public ConcurrentHashMap<Long, BigInteger> getgPowZi() {
        return gPowZi;
    }

    public void setgPowZi(ConcurrentHashMap<Long, BigInteger> gPowZi) {
        this.gPowZi = gPowZi;
    }

    public ConcurrentHashMap<Long, ArrayList<BigInteger>> getSchnorrZKPyi() {
        return schnorrZKPyi;
    }

    public void setSchnorrZKPyi(ConcurrentHashMap<Long, ArrayList<BigInteger>> schnorrZKPyi) {
        this.schnorrZKPyi = schnorrZKPyi;
    }

    public ArrayList<String> getSignerID() {
        return signerID;
    }

    public void setSignerID(ArrayList<String> signerID) {
        this.signerID = signerID;
    }
}
