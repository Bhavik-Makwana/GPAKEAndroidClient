package com.example.myapplication.JPAKEPlus.POJOs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class RoundTwoResponse {
    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> newGen = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> bijs = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> newGenPowBijs = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, ArrayList<BigInteger>>> schnorrZKPbijs = new ConcurrentHashMap<>();

    private ArrayList<String> SignerID = new ArrayList<>();

    public RoundTwoResponse() {}

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> getNewGen() {
        return newGen;
    }

    public void setNewGen(ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> newGen) {
        this.newGen = newGen;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> getBijs() {
        return bijs;
    }

    public void setBijs(ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> bijs) {
        this.bijs = bijs;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> getNewGenPowBijs() {
        return newGenPowBijs;
    }

    public void setNewGenPowBijs(ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> newGenPowBijs) {
        this.newGenPowBijs = newGenPowBijs;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, ArrayList<BigInteger>>> getSchnorrZKPbijs() {
        return schnorrZKPbijs;
    }

    public void setSchnorrZKPbijs(ConcurrentHashMap<Long, ConcurrentHashMap<Long, ArrayList<BigInteger>>> schnorrZKPbijs) {
        this.schnorrZKPbijs = schnorrZKPbijs;
    }

    public ArrayList<String> getSignerID() {
        return SignerID;
    }

    public void setSignerID(ArrayList<String> signerID) {
        SignerID = signerID;
    }
}

