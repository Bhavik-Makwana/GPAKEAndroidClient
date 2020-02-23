package com.example.myapplication.JPAKEPlus.POJOs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class RoundTwoResponse {
    private HashMap<Long, HashMap<Long, BigInteger>> newGen = new HashMap<>();
    private HashMap<Long, HashMap<Long, BigInteger>> bijs = new HashMap<>();
    private HashMap<Long, HashMap<Long, BigInteger>> newGenPowBijs = new HashMap<>();
    private HashMap<Long, HashMap<Long, ArrayList<BigInteger>>> schnorrZKPbijs = new HashMap<>();

    private ArrayList<String> SignerID = new ArrayList<>();

    public RoundTwoResponse() {}

    public HashMap<Long, HashMap<Long, BigInteger>> getNewGen() {
        return newGen;
    }

    public void setNewGen(HashMap<Long, HashMap<Long, BigInteger>> newGen) {
        this.newGen = newGen;
    }

    public HashMap<Long, HashMap<Long, BigInteger>> getBijs() {
        return bijs;
    }

    public void setBijs(HashMap<Long, HashMap<Long, BigInteger>> bijs) {
        this.bijs = bijs;
    }

    public HashMap<Long, HashMap<Long, BigInteger>> getNewGenPowBijs() {
        return newGenPowBijs;
    }

    public void setNewGenPowBijs(HashMap<Long, HashMap<Long, BigInteger>> newGenPowBijs) {
        this.newGenPowBijs = newGenPowBijs;
    }

    public HashMap<Long, HashMap<Long, ArrayList<BigInteger>>> getSchnorrZKPbijs() {
        return schnorrZKPbijs;
    }

    public void setSchnorrZKPbijs(HashMap<Long, HashMap<Long, ArrayList<BigInteger>>> schnorrZKPbijs) {
        this.schnorrZKPbijs = schnorrZKPbijs;
    }

    public ArrayList<String> getSignerID() {
        return SignerID;
    }

    public void setSignerID(ArrayList<String> signerID) {
        SignerID = signerID;
    }
}

