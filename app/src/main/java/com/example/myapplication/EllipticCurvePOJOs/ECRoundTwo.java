package com.example.myapplication.EllipticCurvePOJOs;

import com.example.myapplication.ZeroKnowledgeProofs.SchnorrZKP;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class ECRoundTwo {
    private HashMap<Long, byte[]> newGen = new HashMap<>();
    private HashMap<Long, BigInteger> bijs = new HashMap<>();
    private HashMap<Long, byte[]> newGenPowBijs = new HashMap<>();
    private HashMap<Long, SchnorrZKP> schnorrZKPbijs = new HashMap<>();

    private String signerID;

    public HashMap<Long, byte[]> getNewGen() {
        return newGen;
    }

    public void setNewGen(HashMap<Long, byte[]> newGen) {
        this.newGen = newGen;
    }

    public HashMap<Long, BigInteger> getBijs() {
        return bijs;
    }

    public void setBijs(HashMap<Long, BigInteger> bijs) {
        this.bijs = bijs;
    }

    public HashMap<Long, byte[]> getNewGenPowBijs() {
        return newGenPowBijs;
    }

    public void setNewGenPowBijs(HashMap<Long, byte[]> newGenPowBijs) {
        this.newGenPowBijs = newGenPowBijs;
    }

    public HashMap<Long, SchnorrZKP> getSchnorrZKPbijs() {
        return schnorrZKPbijs;
    }

    public void setSchnorrZKPbijs(HashMap<Long, SchnorrZKP> schnorrZKPbijs) {
        this.schnorrZKPbijs = schnorrZKPbijs;
    }

    public String getSignerID() {
        return signerID;
    }

    public void setSignerID(String signerID) {
        this.signerID = signerID;
    }
}
