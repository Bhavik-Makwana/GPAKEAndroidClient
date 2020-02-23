package com.example.myapplication.JPAKEPlusEC.POJOs;

import com.example.myapplication.JPAKEPlusEC.ZeroKnowledgeProofs.SchnorrZKP;

import java.math.BigInteger;
import java.util.HashMap;

public class ECRoundTwoResponse {
    private HashMap<Long, HashMap<Long, byte[]>> newGen = new HashMap<>();
    private HashMap<Long, HashMap<Long, BigInteger>> bijs = new HashMap<>();
    private HashMap<Long, HashMap<Long, byte[]>> newGenPowBijs = new HashMap<>();
    private HashMap<Long, HashMap<Long, SchnorrZKP>> schnorrZKPbijs = new HashMap<>();

    public HashMap<Long, HashMap<Long, byte[]>> getNewGen() {
        return newGen;
    }

    public void setNewGen(HashMap<Long, HashMap<Long, byte[]>> newGen) {
        this.newGen = newGen;
    }

    public HashMap<Long, HashMap<Long, BigInteger>> getBijs() {
        return bijs;
    }

    public void setBijs(HashMap<Long, HashMap<Long, BigInteger>> bijs) {
        this.bijs = bijs;
    }

    public HashMap<Long, HashMap<Long, byte[]>> getNewGenPowBijs() {
        return newGenPowBijs;
    }

    public void setNewGenPowBijs(HashMap<Long, HashMap<Long, byte[]>> newGenPowBijs) {
        this.newGenPowBijs = newGenPowBijs;
    }

    public HashMap<Long, HashMap<Long, SchnorrZKP>> getSchnorrZKPbijs() {
        return schnorrZKPbijs;
    }

    public void setSchnorrZKPbijs(HashMap<Long, HashMap<Long, SchnorrZKP>> schnorrZKPbijs) {
        this.schnorrZKPbijs = schnorrZKPbijs;
    }
}
