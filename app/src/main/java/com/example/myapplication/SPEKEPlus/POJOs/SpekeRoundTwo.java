package com.example.myapplication.SPEKEPlus.POJOs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class SpekeRoundTwo {
    private BigInteger gPowZiPowYi;
    private ArrayList<BigInteger> chaumPedersonZKPi = new ArrayList<>();
    private HashMap<Long, BigInteger> pairwiseKeysMAC = new HashMap<>();
    private HashMap<Long, BigInteger> pairwiseKeysKC = new HashMap<>();
    private HashMap<Long, BigInteger> hMacsMAC = new HashMap<>();
    private HashMap<Long, BigInteger> hMacsKC = new HashMap<>();

    public SpekeRoundTwo() {}

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

    public HashMap<Long, BigInteger> getPairwiseKeysMAC() {
        return pairwiseKeysMAC;
    }

    public void setPairwiseKeysMAC(HashMap<Long, BigInteger> pairwiseKeysMAC) {
        this.pairwiseKeysMAC = pairwiseKeysMAC;
    }

    public HashMap<Long, BigInteger> getPairwiseKeysKC() {
        return pairwiseKeysKC;
    }

    public void setPairwiseKeysKC(HashMap<Long, BigInteger> pairwiseKeysKC) {
        this.pairwiseKeysKC = pairwiseKeysKC;
    }

    public HashMap<Long, BigInteger> gethMacsMAC() {
        return hMacsMAC;
    }

    public void sethMacsMAC(HashMap<Long, BigInteger> hMacsMAC) {
        this.hMacsMAC = hMacsMAC;
    }

    public HashMap<Long, BigInteger> gethMacsKC() {
        return hMacsKC;
    }

    public void sethMacsKC(HashMap<Long, BigInteger> hMacsKC) {
        this.hMacsKC = hMacsKC;
    }
}
