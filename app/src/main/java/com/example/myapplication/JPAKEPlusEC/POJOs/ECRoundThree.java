package com.example.myapplication.JPAKEPlusEC.POJOs;

import com.example.myapplication.JPAKEPlusEC.ZeroKnowledgeProofs.ChaumPedersonZKP;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

public class ECRoundThree {
    byte[] gPowZiPowYi;
    ChaumPedersonZKP chaumPedersonZKPi = new ChaumPedersonZKP();
    ConcurrentHashMap<Long, BigInteger> pairwiseKeysMAC = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> pairwiseKeysKC = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> hMacsMAC = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> hMacsKC = new ConcurrentHashMap<>();

    public byte[] getgPowZiPowYi() {
        return gPowZiPowYi;
    }

    public void setgPowZiPowYi(byte[] gPowZiPowYi) {
        this.gPowZiPowYi = gPowZiPowYi;
    }

    public ChaumPedersonZKP getChaumPedersonZKPi() {
        return chaumPedersonZKPi;
    }

    public void setChaumPedersonZKPi(ChaumPedersonZKP chaumPedersonZKPi) {
        this.chaumPedersonZKPi = chaumPedersonZKPi;
    }

    public ConcurrentHashMap<Long, BigInteger> getPairwiseKeysMAC() {
        return pairwiseKeysMAC;
    }

    public void setPairwiseKeysMAC(ConcurrentHashMap<Long, BigInteger> pairwiseKeysMAC) {
        this.pairwiseKeysMAC = pairwiseKeysMAC;
    }

    public ConcurrentHashMap<Long, BigInteger> getPairwiseKeysKC() {
        return pairwiseKeysKC;
    }

    public void setPairwiseKeysKC(ConcurrentHashMap<Long, BigInteger> pairwiseKeysKC) {
        this.pairwiseKeysKC = pairwiseKeysKC;
    }

    public ConcurrentHashMap<Long, BigInteger> gethMacsMAC() {
        return hMacsMAC;
    }

    public void sethMacsMAC(ConcurrentHashMap<Long, BigInteger> hMacsMAC) {
        this.hMacsMAC = hMacsMAC;
    }

    public ConcurrentHashMap<Long, BigInteger> gethMacsKC() {
        return hMacsKC;
    }

    public void sethMacsKC(ConcurrentHashMap<Long, BigInteger> hMacsKC) {
        this.hMacsKC = hMacsKC;
    }
}
