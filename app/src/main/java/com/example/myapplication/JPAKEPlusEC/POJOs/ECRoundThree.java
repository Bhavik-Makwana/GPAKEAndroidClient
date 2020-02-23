package com.example.myapplication.JPAKEPlusEC.POJOs;

import com.example.myapplication.JPAKEPlusEC.ZeroKnowledgeProofs.ChaumPedersonZKP;

import java.math.BigInteger;
import java.util.HashMap;

public class ECRoundThree {
    byte[] gPowZiPowYi;
    ChaumPedersonZKP chaumPedersonZKPi = new ChaumPedersonZKP();
    HashMap<Long, BigInteger> pairwiseKeysMAC = new HashMap<>();
    HashMap<Long, BigInteger> pairwiseKeysKC = new HashMap<>();
    HashMap<Long, BigInteger> hMacsMAC = new HashMap<>();
    HashMap<Long, BigInteger> hMacsKC = new HashMap<>();

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
