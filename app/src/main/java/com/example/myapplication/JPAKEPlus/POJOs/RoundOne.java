package com.example.myapplication.JPAKEPlus.POJOs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class RoundOne {
    ConcurrentHashMap<Long, BigInteger> aij = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> gPowAij = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, ArrayList<BigInteger>> schnorrZKPaij = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> bij = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger>  gPowBij = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, ArrayList<BigInteger>> schnorrZKPbij = new ConcurrentHashMap<>();
    BigInteger yi;
    BigInteger gPowYi;
    BigInteger gPowZi;
    ArrayList<BigInteger> schnorrZKPyi = new ArrayList<>();
    String signerID;

    public RoundOne() {
    }

    public ConcurrentHashMap<Long, BigInteger> getAij() {
        return aij;
    }

    public void setAij(ConcurrentHashMap<Long, BigInteger> aij) {
        this.aij = aij;
    }

    public ConcurrentHashMap<Long, BigInteger> getgPowAij() {
        return gPowAij;
    }

    public void setgPowAij(ConcurrentHashMap<Long, BigInteger> gPowAij) {
        this.gPowAij = gPowAij;
    }

    public ConcurrentHashMap<Long, ArrayList<BigInteger>> getSchnorrZKPaij() {
        return schnorrZKPaij;
    }

    public void setSchnorrZKPaij(ConcurrentHashMap<Long, ArrayList<BigInteger>> schnorrZKPaij) {
        this.schnorrZKPaij = schnorrZKPaij;
    }

    public ConcurrentHashMap<Long, BigInteger> getBij() {
        return bij;
    }

    public void setBij(ConcurrentHashMap<Long, BigInteger> bij) {
        this.bij = bij;
    }

    public ConcurrentHashMap<Long, BigInteger> getgPowBij() {
        return gPowBij;
    }

    public void setgPowBij(ConcurrentHashMap<Long, BigInteger> gPowBij) {
        this.gPowBij = gPowBij;
    }

    public ConcurrentHashMap<Long, ArrayList<BigInteger>> getSchnorrZKPbij() {
        return schnorrZKPbij;
    }

    public void setSchnorrZKPbij(ConcurrentHashMap<Long, ArrayList<BigInteger>> schnorrZKPbij) {
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
