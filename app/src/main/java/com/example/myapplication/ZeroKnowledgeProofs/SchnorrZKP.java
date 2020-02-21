package com.example.myapplication.ZeroKnowledgeProofs;

import org.bouncycastle.math.ec.ECPoint;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class SchnorrZKP implements Serializable {

    private byte[] V = null;
    private BigInteger r = null;

    public SchnorrZKP () {
        // constructor
    }

    public void generateZKP (ECPoint generator, BigInteger n, BigInteger x, ECPoint X, String userID) {

        /* Generate a random v from [1, n-1], and compute V = G*v */
        BigInteger v = org.bouncycastle.util.BigIntegers.createRandomInRange(BigInteger.ONE,
                n.subtract(BigInteger.ONE), new SecureRandom());
        ECPoint vEC = generator.multiply(v);
        V = vEC.getEncoded(false);

        BigInteger h = getSHA256(generator, vEC, X, userID); // h

        r = v.subtract(x.multiply(h)).mod(n); // r = v-x*h mod n
    }

    public byte[] getV() {
        return V;
    }

    public BigInteger getr() {
        return r;
    }
    public BigInteger getSHA256(ECPoint generator, ECPoint V, ECPoint X, String userID) {

        MessageDigest sha256 = null;

        try {
            sha256 = MessageDigest.getInstance("SHA-256");

            byte [] GBytes = generator.getEncoded(false);
            byte [] VBytes = V.getEncoded(false);
            byte [] XBytes = X.getEncoded(false);
            byte [] userIDBytes = userID.getBytes();

            // It's good practice to prepend each item with a 4-byte length
            sha256.update(ByteBuffer.allocate(4).putInt(GBytes.length).array());
            sha256.update(GBytes);

            sha256.update(ByteBuffer.allocate(4).putInt(VBytes.length).array());
            sha256.update(VBytes);

            sha256.update(ByteBuffer.allocate(4).putInt(XBytes.length).array());
            sha256.update(XBytes);

            sha256.update(ByteBuffer.allocate(4).putInt(userIDBytes.length).array());
            sha256.update(userIDBytes);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BigInteger(sha256.digest());
    }
}