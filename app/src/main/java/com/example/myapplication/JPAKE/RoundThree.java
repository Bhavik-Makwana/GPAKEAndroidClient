package com.example.myapplication.JPAKE;

import org.bouncycastle.crypto.agreement.jpake.JPAKERound3Payload;

import java.util.HashMap;

public class RoundThree {
    private HashMap<Long, JPAKERound3Payload> jpakeRoundThree = new HashMap<>();

    public HashMap<Long, JPAKERound3Payload> getJpakeRoundThree() {
        return jpakeRoundThree;
    }

    public void setJpakeRoundThree(HashMap<Long, JPAKERound3Payload> jpakeRoundThree) {
        this.jpakeRoundThree = jpakeRoundThree;
    }
}

