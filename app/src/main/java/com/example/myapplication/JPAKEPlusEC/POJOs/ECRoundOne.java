package com.example.myapplication.JPAKEPlusEC.POJOs;

import com.example.myapplication.JPAKEPlusEC.ZeroKnowledgeProofs.SchnorrZKP;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

public class ECRoundOne {
    // *********************************** ROUND 1 ***********************************
    private ConcurrentHashMap<Long, BigInteger> aij = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, byte[]> gPowAij = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, SchnorrZKP> schnorrZKPaij = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, BigInteger> bij = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, byte[]>  gPowBij = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, SchnorrZKP> schnorrZKPbij = new ConcurrentHashMap<>();
    private BigInteger yi;
    private byte[] gPowYi;
    private byte[] gPowZi;
    private SchnorrZKP schnorrZKPyi = new SchnorrZKP();
    private String signerID;


    public ConcurrentHashMap<Long, BigInteger> getAij() {
        return aij;
    }

    public void setAij(ConcurrentHashMap<Long, BigInteger> aij) {
        this.aij = aij;
    }

    public ConcurrentHashMap<Long, byte[]> getgPowAij() {
        return gPowAij;
    }

    public void setgPowAij(ConcurrentHashMap<Long, byte[]> gPowAij) {
        this.gPowAij = gPowAij;
    }

    public ConcurrentHashMap<Long, SchnorrZKP> getSchnorrZKPaij() {
        return schnorrZKPaij;
    }

    public void setSchnorrZKPaij(ConcurrentHashMap<Long, SchnorrZKP> schnorrZKPaij) {
        this.schnorrZKPaij = schnorrZKPaij;
    }

    public ConcurrentHashMap<Long, BigInteger> getBij() {
        return bij;
    }

    public void setBij(ConcurrentHashMap<Long, BigInteger> bij) {
        this.bij = bij;
    }

    public ConcurrentHashMap<Long, byte[]> getgPowBij() {
        return gPowBij;
    }

    public void setgPowBij(ConcurrentHashMap<Long, byte[]> gPowBij) {
        this.gPowBij = gPowBij;
    }

    public ConcurrentHashMap<Long, SchnorrZKP> getSchnorrZKPbij() {
        return schnorrZKPbij;
    }

    public void setSchnorrZKPbij(ConcurrentHashMap<Long, SchnorrZKP> schnorrZKPbij) {
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
