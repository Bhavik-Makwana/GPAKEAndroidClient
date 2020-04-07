package com.example.myapplication.SPEKEPlus.POJOs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class SpekeRoundTwoResponse {
    private ConcurrentHashMap<Long, BigInteger> gPowZiPowYi;
    private ConcurrentHashMap<Long, ArrayList<BigInteger>> chaumPedersonZKPi = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> pairwiseKeysMAC = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> pairwiseKeysKC = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> hMacsMAC = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, ConcurrentHashMap<Long, BigInteger>> hMacsKC = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Long, BigInteger> getgPowZiPowYi() {
        return gPowZiPowYi;
    }

    public void setgPowZiPowYi(ConcurrentHashMap<Long, BigInteger> gPowZiPowYi) {
        this.gPowZiPowYi = gPowZiPowYi;
    }

    public ConcurrentHashMap<Long, ArrayList<BigInteger>> getChaumPedersonZKPi() {
        return chaumPedersonZKPi;
    }

    public void setChaumPedersonZKPi(ConcurrentHashMap<Long, ArrayList<BigInteger>> chaumPedersonZKPi) {
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
