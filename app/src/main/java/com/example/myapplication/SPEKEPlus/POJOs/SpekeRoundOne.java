package com.example.myapplication.SPEKEPlus.POJOs;

import java.math.BigInteger;
import java.util.ArrayList;

public class SpekeRoundOne {
    BigInteger xi;
    BigInteger yi;
    BigInteger gsPowXi;
    BigInteger gPowYi;
    BigInteger gPowZi;
    ArrayList<BigInteger> schnorrZKPi = new ArrayList<>();
    String signerID;

    public BigInteger getXi() {
        return xi;
    }

    public void setXi(BigInteger xi) {
        this.xi = xi;
    }

    public BigInteger getYi() {
        return yi;
    }

    public void setYi(BigInteger yi) {
        this.yi = yi;
    }

    public BigInteger getGsPowXi() {
        return gsPowXi;
    }

    public void setGsPowXi(BigInteger gsPowXi) {
        this.gsPowXi = gsPowXi;
    }

    public BigInteger getgPowYi() {
        return gPowYi;
    }

    public void setgPowYi(BigInteger gPowYi) {
        this.gPowYi = gPowYi;
    }

    public BigInteger getgPowZi() {
        return gPowZi;
    }

    public void setgPowZi(BigInteger gPowZi) {
        this.gPowZi = gPowZi;
    }

    public ArrayList<BigInteger> getSchnorrZKPi() {
        return schnorrZKPi;
    }

    public void setSchnorrZKPi(ArrayList<BigInteger> schnorrZKPi) {
        this.schnorrZKPi = schnorrZKPi;
    }

    public String getSignerID() {
        return signerID;
    }

    public void setSignerID(String signerID) {
        this.signerID = signerID;
    }
}
