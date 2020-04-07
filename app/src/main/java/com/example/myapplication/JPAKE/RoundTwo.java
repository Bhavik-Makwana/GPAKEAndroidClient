package com.example.myapplication.JPAKE;


import org.bouncycastle.crypto.agreement.jpake.JPAKERound2Payload;

import java.util.HashMap;

public class RoundTwo {
    private HashMap<Long, JPAKERound2Payload> jpakeRoundTwo = new HashMap<>();

    public HashMap<Long, JPAKERound2Payload> getJpakeRoundTwo() {
        return jpakeRoundTwo;
    }

    public void setJpakeRoundTwo(HashMap<Long, JPAKERound2Payload> jpakeRoundTwo) {
        this.jpakeRoundTwo = jpakeRoundTwo;
    }
}

