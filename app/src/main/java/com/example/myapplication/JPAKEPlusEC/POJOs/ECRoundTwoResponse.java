package com.example.myapplication.JPAKEPlusEC.POJOs;

import com.example.myapplication.JPAKEPlusEC.ZeroKnowledgeProofs.SchnorrZKP;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

public class ECRoundTwoResponse {
    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, byte[]>> newGen = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> bijs = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, byte[]>> newGenPowBijs = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, SchnorrZKP>> schnorrZKPbijs = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, byte[]>> getNewGen() {
        return newGen;
    }

    public void setNewGen(ConcurrentHashMap<Long, ConcurrentHashMap<Long, byte[]>> newGen) {
        this.newGen = newGen;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> getBijs() {
        return bijs;
    }

    public void setBijs(ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> bijs) {
        this.bijs = bijs;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, byte[]>> getNewGenPowBijs() {
        return newGenPowBijs;
    }

    public void setNewGenPowBijs(ConcurrentHashMap<Long, ConcurrentHashMap<Long, byte[]>> newGenPowBijs) {
        this.newGenPowBijs = newGenPowBijs;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, SchnorrZKP>> getSchnorrZKPbijs() {
        return schnorrZKPbijs;
    }

    public void setSchnorrZKPbijs(ConcurrentHashMap<Long, ConcurrentHashMap<Long, SchnorrZKP>> schnorrZKPbijs) {
        this.schnorrZKPbijs = schnorrZKPbijs;
    }
}
