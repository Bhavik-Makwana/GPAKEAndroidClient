package com.example.myapplication.JPAKEPlus.POJOs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class RoundTwo {
    private HashMap<Long, BigInteger> newGen = new HashMap<>();
    private HashMap<Long, BigInteger> bijs = new HashMap<>();
    private HashMap<Long, BigInteger> newGenPowBijs = new HashMap<>();;
    private HashMap<Long, ArrayList<BigInteger>> schnorrZKPbijs = new HashMap<>();

    private String signerID;

    public RoundTwo() {};

    public HashMap<Long, BigInteger> getNewGen() {
        return newGen;
    }

    public void setNewGen(HashMap<Long, BigInteger> newGen) {
        this.newGen = newGen;
    }

    public HashMap<Long, BigInteger> getBijs() {
        return bijs;
    }

    public void setBijs(HashMap<Long, BigInteger> bijs) {
        this.bijs = bijs;
    }

    public HashMap<Long, BigInteger> getNewGenPowBijs() {
        return newGenPowBijs;
    }

    public void setNewGenPowBijs(HashMap<Long, BigInteger> newGenPowBijs) {
        this.newGenPowBijs = newGenPowBijs;
    }

    public HashMap<Long, ArrayList<BigInteger>> getSchnorrZKPbijs() {
        return schnorrZKPbijs;
    }

    public void setSchnorrZKPbijs(HashMap<Long, ArrayList<BigInteger>> schnorrZKPbijs) {
        this.schnorrZKPbijs = schnorrZKPbijs;
    }

    public String getSignerID() {
        return signerID;
    }

    public void setSignerID(String signerID) {
        this.signerID = signerID;
    }
}
