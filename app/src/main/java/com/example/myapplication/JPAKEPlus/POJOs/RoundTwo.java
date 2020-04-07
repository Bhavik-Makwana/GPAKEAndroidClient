package com.example.myapplication.JPAKEPlus.POJOs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class RoundTwo {
    private ConcurrentHashMap<Long, BigInteger> newGen = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, BigInteger> bijs = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, BigInteger> newGenPowBijs = new ConcurrentHashMap<>();;
    private ConcurrentHashMap<Long, ArrayList<BigInteger>> schnorrZKPbijs = new ConcurrentHashMap<>();

    private String signerID;

    public RoundTwo() {};

    public ConcurrentHashMap<Long, BigInteger> getNewGen() {
        return newGen;
    }

    public void setNewGen(ConcurrentHashMap<Long, BigInteger> newGen) {
        this.newGen = newGen;
    }

    public ConcurrentHashMap<Long, BigInteger> getBijs() {
        return bijs;
    }

    public void setBijs(ConcurrentHashMap<Long, BigInteger> bijs) {
        this.bijs = bijs;
    }

    public ConcurrentHashMap<Long, BigInteger> getNewGenPowBijs() {
        return newGenPowBijs;
    }

    public void setNewGenPowBijs(ConcurrentHashMap<Long, BigInteger> newGenPowBijs) {
        this.newGenPowBijs = newGenPowBijs;
    }

    public ConcurrentHashMap<Long, ArrayList<BigInteger>> getSchnorrZKPbijs() {
        return schnorrZKPbijs;
    }

    public void setSchnorrZKPbijs(ConcurrentHashMap<Long, ArrayList<BigInteger>> schnorrZKPbijs) {
        this.schnorrZKPbijs = schnorrZKPbijs;
    }

    public String getSignerID() {
        return signerID;
    }

    public void setSignerID(String signerID) {
        this.signerID = signerID;
    }
}
