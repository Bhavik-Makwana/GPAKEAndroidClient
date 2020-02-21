package com.example.myapplication.ZeroKnowledgeProofs;


import org.bouncycastle.math.ec.ECPoint;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class ChaumPedersonZKP {

    private byte[] gPowS = null;
    private byte[] gPowZPowS = null;
    private BigInteger t = null;

    public ChaumPedersonZKP () {
        // Constructor
    }

    public void generateZKP(ECPoint G, BigInteger n, ECPoint gPowX, BigInteger x,
                            ECPoint gPowZ, ECPoint gPowZPowX, String signerID, BigInteger q) {

        // Generate s from [1, q-1] and compute (A, B) = (gen^s, genPowZ^s)
        BigInteger s = org.bouncycastle.util.BigIntegers.createRandomInRange(BigInteger.ONE,
                q.subtract(BigInteger.ONE), new SecureRandom());
//        gPowS = g.modPow(s, p);
        ECPoint gPowSEC = G.multiply(s);
        gPowS = gPowSEC.getEncoded(false);
//        gPowZPowS = gPowZ.modPow(s, p);
        ECPoint gPowZPowSEC = gPowZ.multiply(s);
        gPowZPowS = gPowZPowSEC.getEncoded(false);
//        BigInteger h = getSHA256(G,gPowX,gPowZ,gPowZPowX,gPowS,gPowZPowS,signerID); // challenge
        BigInteger h = getSHA256(G, gPowX, gPowZ, gPowZPowX, gPowSEC, gPowZPowSEC, signerID);
//        t = s.subtract(x.multiply(h)).mod(q); // t = s-cr
        t = s.subtract(x.multiply(h)).mod(n);
    }

    public byte[] getGPowS() {
        return gPowS;
    }

    public byte[] getGPowZPowS() {
        return gPowZPowS;
    }

    public BigInteger getT() {
        return t;
    }


    public BigInteger getSHA256(ECPoint generator, ECPoint gPowX, ECPoint gPowZ, ECPoint gPowZPowX,
                                ECPoint gPowS, ECPoint gPowXPowS, String userID) {

        MessageDigest sha256 = null;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");

            byte [] GBytes = generator.getEncoded(false);
            byte [] gPowXBytes = gPowX.getEncoded(false);
            byte [] gPowZBytes = gPowZ.getEncoded(false);
            byte [] gPowZPowXBytes = gPowZPowX.getEncoded(false);
            byte [] gPowSBytes = gPowS.getEncoded(false);
            byte [] gPowXPowSBytes = gPowXPowS.getEncoded(false);
            byte [] userIDBytes = userID.getBytes();

            sha256.update(ByteBuffer.allocate(4).putInt(GBytes.length).array());
            sha256.update(GBytes);

            sha256.update(ByteBuffer.allocate(4).putInt(gPowXBytes.length).array());
            sha256.update(gPowXBytes);

            sha256.update(ByteBuffer.allocate(4).putInt(gPowZBytes.length).array());
            sha256.update(gPowZBytes);

            sha256.update(ByteBuffer.allocate(4).putInt(gPowZPowXBytes.length).array());
            sha256.update(gPowZPowXBytes);

            sha256.update(ByteBuffer.allocate(4).putInt(gPowSBytes.length).array());
            sha256.update(gPowSBytes);

            sha256.update(ByteBuffer.allocate(4).putInt(gPowXPowSBytes.length).array());
            sha256.update(gPowXPowSBytes);

            sha256.update(ByteBuffer.allocate(4).putInt(userIDBytes.length).array());
            sha256.update(userIDBytes);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BigInteger(sha256.digest());

    }
}