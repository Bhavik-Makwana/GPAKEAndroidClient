package com.example.myapplication.SPEKEPlus.POJOs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class SpekeRoundOneResponse {
    private HashMap<Long, BigInteger> xi = new HashMap<>();
    private HashMap<Long, BigInteger> yi = new HashMap<>();
    private HashMap<Long, BigInteger> gsPowXi = new HashMap<>();
    private HashMap<Long, BigInteger> gPowYi = new HashMap<>();
    private HashMap<Long, BigInteger> gPowZi = new HashMap<>();
    private HashMap<Long, ArrayList<BigInteger>> schnorrZKPi = new HashMap<>();
    private ArrayList<String> signerID = new ArrayList<>();

    public HashMap<Long, BigInteger> getXi() {
        return xi;
    }

    public void setXi(HashMap<Long, BigInteger> xi) {
        this.xi = xi;
    }

    public HashMap<Long, BigInteger> getYi() {
        return yi;
    }

    public void setYi(HashMap<Long, BigInteger> yi) {
        this.yi = yi;
    }

    public HashMap<Long, BigInteger> getGsPowXi() {
        return gsPowXi;
    }

    public void setGsPowXi(HashMap<Long, BigInteger> gsPowXi) {
        this.gsPowXi = gsPowXi;
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

    public HashMap<Long, ArrayList<BigInteger>> getSchnorrZKPi() {
        return schnorrZKPi;
    }

    public void setSchnorrZKPi(HashMap<Long, ArrayList<BigInteger>> schnorrZKPi) {
        this.schnorrZKPi = schnorrZKPi;
    }

    public ArrayList<String> getSignerID() {
        return signerID;
    }

    public void setSignerID(ArrayList<String> signerID) {
        this.signerID = signerID;
    }
}
