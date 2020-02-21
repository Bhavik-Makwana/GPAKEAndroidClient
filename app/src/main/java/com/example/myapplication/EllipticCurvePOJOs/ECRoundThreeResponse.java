package com.example.myapplication.EllipticCurvePOJOs;

import com.example.myapplication.ZeroKnowledgeProofs.ChaumPedersonZKP;

import java.math.BigInteger;
import java.util.HashMap;

public class ECRoundThreeResponse {
    private HashMap<Long, byte[]> gPowZiPowYi = new HashMap<>();
    private HashMap<Long, ChaumPedersonZKP> chaumPedersonZKPi = new HashMap<>();
    private HashMap<Long, HashMap<Long, BigInteger>> pairwiseKeysMAC = new HashMap<>();
    private HashMap<Long, HashMap<Long, BigInteger>> pairwiseKeysKC = new HashMap<>();
    private HashMap<Long, HashMap<Long, BigInteger>> hMacsMAC = new HashMap<>();
    private HashMap<Long, HashMap<Long, BigInteger>> hMacsKC = new HashMap<>();

    public HashMap<Long, byte[]> getgPowZiPowYi() {
        return gPowZiPowYi;
    }

    public void setgPowZiPowYi(HashMap<Long, byte[]> gPowZiPowYi) {
        this.gPowZiPowYi = gPowZiPowYi;
    }

    public HashMap<Long, ChaumPedersonZKP> getChaumPedersonZKPi() {
        return chaumPedersonZKPi;
    }

    public void setChaumPedersonZKPi(HashMap<Long, ChaumPedersonZKP> chaumPedersonZKPi) {
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
