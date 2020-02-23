package com.example.myapplication.JPAKEPlus.POJOs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class RoundThreeResponse {
    private HashMap<Long, BigInteger> gPowZiPowYi;
    private HashMap<Long, ArrayList<BigInteger>> chaumPedersonZKPi = new HashMap<>();
    private HashMap<Long, HashMap<Long, BigInteger>> pairwiseKeysMAC = new HashMap<>();
    private HashMap<Long, HashMap<Long, BigInteger>> pairwiseKeysKC = new HashMap<>();
    private HashMap<Long, HashMap<Long, BigInteger>> hMacsMAC = new HashMap<>();
    private HashMap<Long, HashMap<Long, BigInteger>> hMacsKC = new HashMap<>();

    public RoundThreeResponse() {};

    public HashMap<Long, BigInteger> getgPowZiPowYi() {
        return gPowZiPowYi;
    }

    public void setgPowZiPowYi(HashMap<Long, BigInteger> gPowZiPowYi) {
        this.gPowZiPowYi = gPowZiPowYi;
    }

    public HashMap<Long, ArrayList<BigInteger>> getChaumPedersonZKPi() {
        return chaumPedersonZKPi;
    }

    public void setChaumPedersonZKPi(HashMap<Long, ArrayList<BigInteger>> chaumPedersonZKPi) {
        this.chaumPedersonZKPi = chaumPedersonZKPi;
    }

    public HashMap<Long, HashMap<Long, BigInteger>> getPairwiseKeysMAC() {
        return pairwiseKeysMAC;
    }

    public void setPairwiseKeysMAC(HashMap<Long, HashMap<Long, BigInteger>> pairwiseKeysMAC) {
        this.pairwiseKeysMAC = pairwiseKeysMAC;
    }

    public HashMap<Long, HashMap<Long, BigInteger>> getPairwiseKeysKC() {
        return pairwiseKeysKC;
    }

    public void setPairwiseKeysKC(HashMap<Long, HashMap<Long, BigInteger>> pairwiseKeysKC) {
        this.pairwiseKeysKC = pairwiseKeysKC;
    }

    public HashMap<Long, HashMap<Long, BigInteger>> gethMacsMAC() {
        return hMacsMAC;
    }

    public void sethMacsMAC(HashMap<Long, HashMap<Long, BigInteger>> hMacsMAC) {
        this.hMacsMAC = hMacsMAC;
    }

    public HashMap<Long, HashMap<Long, BigInteger>> gethMacsKC() {
        return hMacsKC;
    }

    public void sethMacsKC(HashMap<Long, HashMap<Long, BigInteger>> hMacsKC) {
        this.hMacsKC = hMacsKC;
    }
}
