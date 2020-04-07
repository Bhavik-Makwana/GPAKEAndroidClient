package com.example.myapplication.JPAKEPlusEC.POJOs;

import com.example.myapplication.JPAKEPlusEC.ZeroKnowledgeProofs.SchnorrZKP;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ECRoundOneResponse {
    private  ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> aij = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, ConcurrentHashMap<Long, byte[]>> gPowAij = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, ConcurrentHashMap<Long, SchnorrZKP>> schnorrZKPaij = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> bij = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, ConcurrentHashMap<Long, byte[]>> gPowBij = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, ConcurrentHashMap<Long, SchnorrZKP>> schnorrZKPbij = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, BigInteger> yi = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, byte[]> gPowYi = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, byte[]> gPowZi = new ConcurrentHashMap<>();
    private  ConcurrentHashMap<Long, SchnorrZKP> schnorrZKPyi = new ConcurrentHashMap<>();
    private  ArrayList<String> signerID = new ArrayList<>();

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> getAij() {
        return aij;
    }

    public void setAij(ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> aij) {
        this.aij = aij;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, byte[]>> getgPowAij() {
        return gPowAij;
    }

    public void setgPowAij(ConcurrentHashMap<Long, ConcurrentHashMap<Long, byte[]>> gPowAij) {
        this.gPowAij = gPowAij;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, SchnorrZKP>> getSchnorrZKPaij() {
        return schnorrZKPaij;
    }

    public void setSchnorrZKPaij(ConcurrentHashMap<Long, ConcurrentHashMap<Long, SchnorrZKP>> schnorrZKPaij) {
        this.schnorrZKPaij = schnorrZKPaij;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> getBij() {
        return bij;
    }

    public void setBij(ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> bij) {
        this.bij = bij;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, byte[]>> getgPowBij() {
        return gPowBij;
    }

    public void setgPowBij(ConcurrentHashMap<Long, ConcurrentHashMap<Long, byte[]>> gPowBij) {
        this.gPowBij = gPowBij;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, SchnorrZKP>> getSchnorrZKPbij() {
        return schnorrZKPbij;
    }

    public void setSchnorrZKPbij(ConcurrentHashMap<Long, ConcurrentHashMap<Long, SchnorrZKP>> schnorrZKPbij) {
        this.schnorrZKPbij = schnorrZKPbij;
    }

    public ConcurrentHashMap<Long, BigInteger> getYi() {
        return yi;
    }

    public void setYi(ConcurrentHashMap<Long, BigInteger> yi) {
        this.yi = yi;
    }

    public ConcurrentHashMap<Long, byte[]> getgPowYi() {
        return gPowYi;
    }

    public void setgPowYi(ConcurrentHashMap<Long, byte[]> gPowYi) {
        this.gPowYi = gPowYi;
    }

    public ConcurrentHashMap<Long, byte[]> getgPowZi() {
        return gPowZi;
    }

    public void setgPowZi(ConcurrentHashMap<Long, byte[]> gPowZi) {
        this.gPowZi = gPowZi;
    }

    public ConcurrentHashMap<Long, SchnorrZKP> getSchnorrZKPyi() {
        return schnorrZKPyi;
    }

    public void setSchnorrZKPyi(ConcurrentHashMap<Long, SchnorrZKP> schnorrZKPyi) {
        this.schnorrZKPyi = schnorrZKPyi;
    }

    public ArrayList<String> getSignerID() {
        return signerID;
    }

    public void setSignerID(ArrayList<String> signerID) {
        this.signerID = signerID;
    }
}
