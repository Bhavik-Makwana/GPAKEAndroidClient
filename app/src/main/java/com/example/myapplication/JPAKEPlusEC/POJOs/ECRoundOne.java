package com.example.myapplication.JPAKEPlusEC.POJOs;

import com.example.myapplication.JPAKEPlusEC.ZeroKnowledgeProofs.SchnorrZKP;

import java.math.BigInteger;
import java.util.HashMap;

public class ECRoundOne {
    // *********************************** ROUND 1 ***********************************
    private HashMap<Long, BigInteger> aij = new HashMap<>();
    private HashMap<Long, byte[]> gPowAij = new HashMap<>();
    private HashMap<Long, SchnorrZKP> schnorrZKPaij = new HashMap<>();
    private HashMap<Long, BigInteger> bij = new HashMap<>();
    private HashMap<Long, byte[]>  gPowBij = new HashMap<>();
    private HashMap<Long, SchnorrZKP> schnorrZKPbij = new HashMap<>();
    private BigInteger yi;
    private byte[] gPowYi;
    private byte[] gPowZi;
    private SchnorrZKP schnorrZKPyi = new SchnorrZKP();
    private String signerID;


    public HashMap<Long, BigInteger> getAij() {
        return aij;
    }

    public void setAij(HashMap<Long, BigInteger> aij) {
        this.aij = aij;
    }

    public HashMap<Long, byte[]> getgPowAij() {
        return gPowAij;
    }

    public void setgPowAij(HashMap<Long, byte[]> gPowAij) {
        this.gPowAij = gPowAij;
    }

    public HashMap<Long, SchnorrZKP> getSchnorrZKPaij() {
        return schnorrZKPaij;
    }

    public void setSchnorrZKPaij(HashMap<Long, SchnorrZKP> schnorrZKPaij) {
        this.schnorrZKPaij = schnorrZKPaij;
    }

    public HashMap<Long, BigInteger> getBij() {
        return bij;
    }

    public void setBij(HashMap<Long, BigInteger> bij) {
        this.bij = bij;
    }

    public HashMap<Long, byte[]> getgPowBij() {
        return gPowBij;
    }

    public void setgPowBij(HashMap<Long, byte[]> gPowBij) {
        this.gPowBij = gPowBij;
    }

    public HashMap<Long, SchnorrZKP> getSchnorrZKPbij() {
        return schnorrZKPbij;
    }

    public void setSchnorrZKPbij(HashMap<Long, SchnorrZKP> schnorrZKPbij) {
        this.schnorrZKPbij = schnorrZKPbij;
    }

    public BigInteger getYi() {
        return yi;
    }

    public void setYi(BigInteger yi) {
        this.yi = yi;
    }

    public byte[] getgPowYi() {
        return gPowYi;
    }

    public void setgPowYi(byte[] gPowYi) {
        this.gPowYi = gPowYi;
    }

    public byte[] getgPowZi() {
        return gPowZi;
    }

    public void setgPowZi(byte[] gPowZi) {
        this.gPowZi = gPowZi;
    }

    public SchnorrZKP getSchnorrZKPyi() {
        return schnorrZKPyi;
    }

    public void setSchnorrZKPyi(SchnorrZKP schnorrZKPyi) {
        this.schnorrZKPyi = schnorrZKPyi;
    }

    public String getSignerID() {
        return signerID;
    }

    public void setSignerID(String signerID) {
        this.signerID = signerID;
    }
}
