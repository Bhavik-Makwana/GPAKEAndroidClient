package com.example.myapplication.JPAKEPlusEC.POJOs;

import com.example.myapplication.JPAKEPlusEC.ZeroKnowledgeProofs.ChaumPedersonZKP;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

public class ECRoundThreeResponse {
    private ConcurrentHashMap<Long, byte[]> gPowZiPowYi = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, ChaumPedersonZKP> chaumPedersonZKPi = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> pairwiseKeysMAC = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> pairwiseKeysKC = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> hMacsMAC = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> hMacsKC = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Long, byte[]> getgPowZiPowYi() {
        return gPowZiPowYi;
    }

    public void setgPowZiPowYi(ConcurrentHashMap<Long, byte[]> gPowZiPowYi) {
        this.gPowZiPowYi = gPowZiPowYi;
    }

    public ConcurrentHashMap<Long, ChaumPedersonZKP> getChaumPedersonZKPi() {
        return chaumPedersonZKPi;
    }

    public void setChaumPedersonZKPi(ConcurrentHashMap<Long, ChaumPedersonZKP> chaumPedersonZKPi) {
        this.chaumPedersonZKPi = chaumPedersonZKPi;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> getPairwiseKeysMAC() {
        return pairwiseKeysMAC;
    }

    public void setPairwiseKeysMAC(ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> pairwiseKeysMAC) {
        this.pairwiseKeysMAC = pairwiseKeysMAC;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> getPairwiseKeysKC() {
        return pairwiseKeysKC;
    }

    public void setPairwiseKeysKC(ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> pairwiseKeysKC) {
        this.pairwiseKeysKC = pairwiseKeysKC;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> gethMacsMAC() {
        return hMacsMAC;
    }

    public void sethMacsMAC(ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> hMacsMAC) {
        this.hMacsMAC = hMacsMAC;
    }

    public ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> gethMacsKC() {
        return hMacsKC;
    }

    public void sethMacsKC(ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> hMacsKC) {
        this.hMacsKC = hMacsKC;
    }
}
