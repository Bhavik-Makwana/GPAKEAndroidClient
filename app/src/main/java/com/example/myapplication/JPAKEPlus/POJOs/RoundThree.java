package com.example.myapplication.JPAKEPlus.POJOs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class RoundThree {
    BigInteger gPowZiPowYi;
    ArrayList<BigInteger> chaumPedersonZKPi = new ArrayList<>();
    ConcurrentHashMap<Long, BigInteger> pairwiseKeysMAC = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> pairwiseKeysKC = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> hMacsMAC = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> hMacsKC = new ConcurrentHashMap<>();

    public RoundThree(){};

    public BigInteger getgPowZiPowYi() {
        return gPowZiPowYi;
    }

    public void setgPowZiPowYi(BigInteger gPowZiPowYi) {
        this.gPowZiPowYi = gPowZiPowYi;
    }

    public ArrayList<BigInteger> getChaumPedersonZKPi() {
        return chaumPedersonZKPi;
    }

    public void setChaumPedersonZKPi(ArrayList<BigInteger> chaumPedersonZKPi) {
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

