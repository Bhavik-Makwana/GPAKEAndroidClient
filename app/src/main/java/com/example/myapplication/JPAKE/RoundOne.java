package com.example.myapplication.JPAKE;

import org.bouncycastle.crypto.agreement.jpake.JPAKERound1Payload;

import java.util.HashMap;

public class RoundOne {
    private HashMap<Long, JPAKERound1Payload> jpakeRoundOne = new HashMap<>();

    public HashMap<Long, JPAKERound1Payload> getJpakeRoundOne() {
        return jpakeRoundOne;
    }

    public void setJpakeRoundOne(HashMap<Long, JPAKERound1Payload> jpakeRoundOne) {
        this.jpakeRoundOne = jpakeRoundOne;
    }
}
