package com.example.myapplication.SPEKEPlus.POJOs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class SpekeRoundOneResponse {
    private ConcurrentHashMap<Long, BigInteger> xi = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, BigInteger> yi = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, BigInteger> gsPowXi = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, BigInteger> gPowYi = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, BigInteger> gPowZi = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, ArrayList<BigInteger>> schnorrZKPi = new ConcurrentHashMap<>();
    private ArrayList<String> signerID = new ArrayList<>();

    public ConcurrentHashMap<Long, BigInteger> getXi() {
        return xi;
    }

    public void setXi(ConcurrentHashMap<Long, BigInteger> xi) {
        this.xi = xi;
    }

    public ConcurrentHashMap<Long, BigInteger> getYi() {
        return yi;
    }

    public void setYi(ConcurrentHashMap<Long, BigInteger> yi) {
        this.yi = yi;
    }

    public ConcurrentHashMap<Long, BigInteger> getGsPowXi() {
        return gsPowXi;
    }

    public void setGsPowXi(ConcurrentHashMap<Long, BigInteger> gsPowXi) {
        this.gsPowXi = gsPowXi;
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

    public ConcurrentHashMap<Long, ArrayList<BigInteger>> getSchnorrZKPi() {
        return schnorrZKPi;
    }

    public void setSchnorrZKPi(ConcurrentHashMap<Long, ArrayList<BigInteger>> schnorrZKPi) {
        this.schnorrZKPi = schnorrZKPi;
    }

    public ArrayList<String> getSignerID() {
        return signerID;
    }

    public void setSignerID(ArrayList<String> signerID) {
        this.signerID = signerID;
    }
}
