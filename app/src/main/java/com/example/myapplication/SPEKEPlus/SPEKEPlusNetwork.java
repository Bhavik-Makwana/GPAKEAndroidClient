package com.example.myapplication.SPEKEPlus;

import java.math.BigInteger;

import android.util.Log;

import com.example.myapplication.SPEKEPlus.POJOs.SpekeRoundOne;
import com.example.myapplication.SPEKEPlus.POJOs.SpekeRoundOneResponse;
import com.example.myapplication.SPEKEPlus.POJOs.SpekeRoundTwo;
import com.example.myapplication.SPEKEPlus.POJOs.SpekeRoundTwoResponse;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class SPEKEPlusNetwork {
    String sStr;
    BigInteger p;
    BigInteger q;
    BigInteger g;
    int n;
    BigInteger bigTwo = new BigInteger("2", 16);

    long startTime;
    long endTime;
    TreeMap<String, Long> time = new TreeMap<>();

    // **************************** ROUND 1 ****************************
    BigInteger xi;
    BigInteger yi;
    BigInteger gsPowXi;
    BigInteger gPowYi;
    BigInteger gPowZi;
    ArrayList<BigInteger> schnorrZKPi = new ArrayList<>();
    String signerID;

    // **************************** ROUND 2 ****************************
    BigInteger gPowZiPowYi;
    ArrayList<BigInteger> chaumPedersonZKPi = new ArrayList<>();
    ConcurrentHashMap<Long, BigInteger> pairwiseKeysMAC = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> pairwiseKeysKC = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> hMacsMAC = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> hMacsKC = new ConcurrentHashMap<>();

    // **************************** ROUND 3 ****************************

    public SPEKEPlusNetwork(String sStr, BigInteger p, BigInteger q, BigInteger g, int n, String id) {
        this.sStr = sStr;
        this.p = p;
        this.q = q;
        this.g = g;
        this.n = n;
        this.signerID = id;
    }

    public SpekeRoundOne roundOne() {
        System.out.println("*************** ROUND 1 ***************");
        startTime = System.currentTimeMillis();
        BigInteger gs = getSHA256(sStr);
        xi = org.bouncycastle.util.BigIntegers.createRandomInRange(BigInteger.ONE,
                q.subtract(BigInteger.ONE), new SecureRandom());
        yi = org.bouncycastle.util.BigIntegers.createRandomInRange(BigInteger.ZERO,
                q.subtract(BigInteger.ONE), new SecureRandom());
        gsPowXi = gs.modPow(xi, p);
        gPowYi = g.modPow(yi, p);
        SchnorrZKP schnorrZKP = new SchnorrZKP();
        schnorrZKP.generateZKP(p, q, g, gPowYi, yi, signerID);
        schnorrZKPi.add(schnorrZKP.getGenPowV());
        schnorrZKPi.add(schnorrZKP.getR());

        Log.d("speke",  "********** SEND ROUND 1 DATA **********");
        SpekeRoundOne roundOne = new SpekeRoundOne();
        roundOne.setXi(xi);
        roundOne.setYi(yi);
        roundOne.setGsPowXi(gsPowXi);
        roundOne.setgPowYi(gPowYi);
        roundOne.setgPowZi(gPowZi);
        roundOne.setSchnorrZKPi(schnorrZKPi);
        roundOne.setSignerID(signerID);
        endTime = System.currentTimeMillis();
        time.put("1) Latency of computing round 1 for participant (ms):", (endTime-startTime));
        return roundOne;

    }

    public boolean verifyRoundOne(SpekeRoundOneResponse r) {
        System.out.println("************ VERIFY ROUND 1 ***********");
        startTime = System.currentTimeMillis();
        for (int i=0; i<n; i++) {

            int iPlusOne = (i==n-1) ? 0: i+1;
            int iMinusOne = (i==0) ? n-1 : i-1;

            long current = Long.parseLong(r.getSignerID().get(i));
            long rightNeighbour = Long.parseLong(r.getSignerID().get(iPlusOne));
            long leftNeightbour = Long.parseLong(r.getSignerID().get(iMinusOne));

            r.getgPowZi().put(current, r.getgPowYi().get(leftNeightbour).modInverse(p).multiply(r.getgPowYi().get(rightNeighbour)).mod(p));
            if (r.getgPowZi().get(current).compareTo(BigInteger.ONE) == 0)
                exitWithError("Round 1 verification failed at checking g^{y_{i+1}}/g^{y_{i-1}}!=1 for i="+i);
        }

        // Verification

        // ith participant
        long cID = Long.parseLong(signerID);

        for (int j=0; j<n; j++) {
            long jID = Long.parseLong(r.getSignerID().get(j));
            if (cID==jID){
                continue;
            }
            if (r.getGsPowXi().get(jID).compareTo(bigTwo) == -1 ||
                    r.getGsPowXi().get(jID).compareTo(p.subtract(bigTwo)) == 1) {
                Log.d("speke",  "Round 1 verification failed at checking gs^{x_j} for j="+j);
                return false;
            }

            // ZKP - except ith
            if (cID==jID) {
                continue;
            }
            if (!verifySchnorrZKP(p, q, g,
                    r.getgPowYi().get(jID),
                    r.getSchnorrZKPi().get(jID).get(0),
                    r.getSchnorrZKPi().get(jID).get(1),
                    r.getSignerID().get(j))) {
                Log.d("speke",  "Round 1 verification failed at checking jth SchnorrZKP for j="+j);
                return false;
            }
        }
        endTime = System.currentTimeMillis();
        time.put("2) Latency of verifying round 1 for participant (ms):", (endTime-startTime));
        return true;
    }

    public SpekeRoundTwo roundTwo(SpekeRoundOneResponse r) {
        // Round 2: P_i sends A = {g^{y_i}, ZKP, (g^{z_i})^{y_i}, ZKP}, HMAC authentication tag,
        // HMAC key confirmation tag
        Log.d("speke",  "*********** ROUND 2 ***********");
        startTime = System.currentTimeMillis();

        long cID = Long.parseLong(signerID);

        gPowZiPowYi = r.getgPowZi().get(cID).modPow(r.getYi().get(cID), p);

        ChaumPedersonZKP chaumPedersonZKP = new ChaumPedersonZKP();
        chaumPedersonZKP.generateZKP(p, q, g,
                r.getgPowYi().get(cID),
                r.getYi().get(cID),
                r.getgPowZi().get(cID),
                gPowZiPowYi,
                signerID);

        chaumPedersonZKPi.add(chaumPedersonZKP.getGPowS());
        chaumPedersonZKPi.add(chaumPedersonZKP.getGPowZPowS());
        chaumPedersonZKPi.add(chaumPedersonZKP.getT());

        // Compute pairwise keys
        for (int j=0; j<n; j++){
            long jID = Long.parseLong(r.getSignerID().get(j));
            if (cID==jID){
                continue;
            }

            BigInteger rawKey = r.getGsPowXi().get(jID).modPow(r.getXi().get(cID), p);
            pairwiseKeysMAC.put(jID, getSHA256(rawKey, "MAC"));
            pairwiseKeysKC.put(jID, getSHA256(rawKey, "KC"));

            String hmacName = "HMac-SHA256";

            try {
                // Compute HMAC for message authentication
                SecretKey key = new SecretKeySpec(pairwiseKeysMAC.get(jID).toByteArray(), hmacName);

                Mac mac = Mac.getInstance(hmacName);
                mac.init(key);
                mac.reset();
                mac.update(r.getgPowYi().get(cID).toByteArray());
                mac.update(r.getSchnorrZKPi().get(cID).get(0).toByteArray());
                mac.update(r.getSchnorrZKPi().get(cID).get(1).toByteArray());
                mac.update(gPowZiPowYi.toByteArray());
                mac.update(chaumPedersonZKPi.get(0).toByteArray());
                mac.update(chaumPedersonZKPi.get(1).toByteArray());
                mac.update(chaumPedersonZKPi.get(2).toByteArray());

                hMacsMAC.put(jID, new BigInteger(mac.doFinal()));
                // Compute HMAC for key confirmation
                key = new SecretKeySpec(pairwiseKeysKC.get(jID).toByteArray(), hmacName);
                mac.reset();
                mac.init(key);
                mac.update("KC".getBytes());
                mac.update(new BigInteger(""+cID).toByteArray());
                mac.update(new BigInteger(""+jID).toByteArray());
                mac.update(r.getGsPowXi().get(cID).toByteArray());
                mac.update(r.getGsPowXi().get(jID).toByteArray());
                hMacsKC.put(jID, new BigInteger(mac.doFinal()));
            } catch(Exception e){
                e.printStackTrace();
                System.exit(0);

            }
        }
        SpekeRoundTwo roundTwo = new SpekeRoundTwo();
        roundTwo.setChaumPedersonZKPi(chaumPedersonZKPi);
        roundTwo.setgPowZiPowYi(gPowZiPowYi);
        roundTwo.sethMacsKC(hMacsKC);
        roundTwo.sethMacsMAC(hMacsMAC);
        roundTwo.setPairwiseKeysKC(pairwiseKeysKC);
        roundTwo.setPairwiseKeysMAC(pairwiseKeysMAC);

        endTime = System.currentTimeMillis();
        time.put("3) Latency of computing round 2 for participant (ms):", (endTime-startTime));
        return roundTwo;
    }

    public boolean verifyRoundTwo(SpekeRoundOneResponse r1, SpekeRoundTwoResponse r2) {
        Log.d("speke",  "*********** VERIFY ROUND 2 ***********");
        startTime = System.currentTimeMillis();

        // Verifying data in Round 2
        long cID = Long.parseLong(signerID);
        for (int j=0; j<n; j++) {
            long jID = Long.parseLong(r1.getSignerID().get(j));
            // check ZKP - except ith
            if (cID==jID) {
                continue;
            }

            if(!verifyChaumPedersonZKP(p, q, g,
                    r1.getgPowYi().get(jID),
                    r1.getgPowZi().get(jID),
                    r2.getgPowZiPowYi().get(jID),
                    r2.getChaumPedersonZKPi().get(jID).get(0),
                    r2.getChaumPedersonZKPi().get(jID).get(1),
                    r2.getChaumPedersonZKPi().get(jID).get(2),
                    r1.getSignerID().get(j))) {
                Log.d("speke",  "Round 2 verification failed at checking jth Chaum-Pederson for (i,j)=("+cID+","+j+")");
                return false;
            }

            // Check key confirmation - except ith
            String hmacName = "HMac-SHA256";

            if (cID==jID) {
                continue;
            }

            SecretKey key = new SecretKeySpec(r2.getPairwiseKeysKC().get(cID).get(jID).toByteArray(), hmacName);

            try {
                Mac mac = Mac.getInstance(hmacName);
                mac.init(key);

                mac.update("KC".getBytes());
                mac.update(new BigInteger(""+jID).toByteArray());
                mac.update(new BigInteger(""+cID).toByteArray());

                mac.update(r1.getGsPowXi().get(jID).toByteArray());
                mac.update(r1.getGsPowXi().get(cID).toByteArray());
                if (new BigInteger(mac.doFinal()).compareTo(r2.gethMacsKC().get(jID).get(cID)) != 0) {
                    Log.d("speke",  "Round 2 verification failed at checking KC for (i,j)=("+cID+","+jID+")");
                    return false;

                }
            }catch(Exception e){

                e.printStackTrace();
                System.exit(0);

            }

            // Check MACs - except ith
            if (cID==jID) {
                continue;
            }

            key = new SecretKeySpec(r2.getPairwiseKeysMAC().get(cID).get(jID).toByteArray(), hmacName);
            try {
                Mac mac = Mac.getInstance(hmacName);
                mac.init(key);
                mac.reset();
                mac.update(r1.getgPowYi().get(jID).toByteArray());
                mac.update(r1.getSchnorrZKPi().get(jID).get(0).toByteArray());
                mac.update(r1.getSchnorrZKPi().get(jID).get(1).toByteArray());
                mac.update(r2.getgPowZiPowYi().get(jID).toByteArray());
                mac.update(r2.getChaumPedersonZKPi().get(jID).get(0).toByteArray());
                mac.update(r2.getChaumPedersonZKPi().get(jID).get(1).toByteArray());
                mac.update(r2.getChaumPedersonZKPi().get(jID).get(2).toByteArray());

                if (new BigInteger(mac.doFinal()).compareTo(r2.gethMacsMAC().get(jID).get(cID)) != 0) {
                    Log.d("speke",  "Round 2 verification failed at checking MACs for (i,j)=("+cID+","+jID+")");
                    return false;
                }
            }catch(Exception e){
                e.printStackTrace();
                System.exit(0);
            }
        }
        endTime = System.currentTimeMillis();
        time.put("4) Latency of verifying round 2 for participant (ms):", (endTime-startTime));
        return true;
    }

    public BigInteger computeKeys(SpekeRoundOneResponse r1, SpekeRoundTwoResponse r2) {
        Log.d("speke",  "*********** KEY COMPUTATION ***********");
        startTime = System.currentTimeMillis();
        long iID = Long.parseLong(signerID);
        int i = r1.getSignerID().indexOf(signerID);
        // ith participant
        int cyclicIndex = getCyclicIndex(i-1, n);
        BigInteger firstTerm = r1.getgPowYi().get(Long.parseLong(r1.getSignerID().get(cyclicIndex)))
                .modPow(r1.getYi().get(iID).multiply(BigInteger.valueOf(n)), p);
        BigInteger finalTerm = firstTerm;

        for (int j=0; j<(n-1) ; j++) {
            cyclicIndex = getCyclicIndex(i+j, n);
            BigInteger interTerm = r2.getgPowZiPowYi().get(Long.parseLong(r1.getSignerID().get(cyclicIndex)))
                    .modPow(BigInteger.valueOf(n-1-j), p);
            finalTerm = finalTerm.multiply(interTerm).mod(p);
        }


        BigInteger key = getSHA256(finalTerm);
                endTime = System.currentTimeMillis();
        time.put("5) Latency of key computation for participant (ms):", (endTime-startTime));
        displayLatency();
        return key;
    }

    public void displayLatency() {
        System.out.println("\nLatency of Each Round JPAKEPairing+\n");
        for (Map.Entry<String, Long> e : time.entrySet()) {
            Log.d("SPEKE_LATENCY", e.getKey() + e.getValue());
        }
    }

    public int getCyclicIndex (int i, int n){

        if (i<0){
            return i+n;
        }else if (i>=n){
            return i-n;
        }else {
            return i;
        }
    }


    /*
     * Check
     * a) g^x in [1, q-1]
     * b) (g^x)^q = 1
     * c) g^v = g^r (g^x)^h
     *
     * Note: the case of x = 0 is not excluded in this routine. Handle that in the upstream if you need to.
     */
    public boolean verifySchnorrZKP(BigInteger p, BigInteger q, BigInteger gen, BigInteger genPowX, BigInteger genPowV, BigInteger r, String userID) {

        // ZKP: {V=gen^v, r}
        BigInteger h = getSHA256(gen, genPowV, genPowX, userID);

        if (genPowX.compareTo(BigInteger.ZERO) == 1 && // gen^x > 0
                genPowX.compareTo(p) == -1 && // gen^x < p
                genPowX.modPow(q, p).compareTo(BigInteger.ONE) == 0 && // gen^x^q = 1

                /* A straightforward way to compute g^r * g^x^h needs 2 exp. Using
                 * a simultaneous computation technique would only need 1 exp.
                 */
                gen.modPow(r,p).multiply(genPowX.modPow(h,p)).mod(p).compareTo(genPowV) == 0) { // gen^v=gen^r * gen^x^h
            return true;
        }
        else {
            return false;
        }
    }

    /*
     * Full checks:
     * a) g^x in [1, q-1] and (g^x)^q = 1
     * b) g^z in [2, q-1] and (g^z)^q = 1
     * c) (g^z)^x in [1, q-1] and (g^z)^x = 1
     * d) g^s = g^t (g^x)^h
     * e) (g^z)^s = (g^z)^t ((g^z)^x)^h
     *
     * Notes:
     *
     * 1) The case of x = 0 is not excluded in this routine. Handle that in the upstream if you need to.
     * 2) Only partial checks are implemented in this routine, since some overlap with the previous
     *    checks in verifying the SchnorrZKP.
     *
     */
    public boolean verifyChaumPedersonZKP(BigInteger p, BigInteger q, BigInteger g, BigInteger gPowX, BigInteger gPowZ,
                                          BigInteger gPowZPowX, BigInteger gPowS, BigInteger gPowZPowS, BigInteger t, String signerID) {

        // ZKP: {A=g^s, B=(g^z)^s, t}
        BigInteger h = getSHA256(g,gPowX,gPowZ,gPowZPowX,gPowS,gPowZPowS,signerID);

        // check a) - omitted as it's been done in round 1
    	/*
       	if (gPowX.compareTo(BigInteger.ONE) == -1 ||
       			gPowX.compareTo(q.subtract(BigInteger.ONE)) == 1 ||
       			gPowX.modPow(q, p).compareTo(BigInteger.ONE) != 0) {
       		return false;
       	}
       	*/

        // Check b) - only partial; redundant checks not repeated. e.g., the order of g^z implied by ZKP checks in round 1
        if (gPowZ.compareTo(BigInteger.ONE) == 0){
            return false;
        }

        // Check c) - full check
        if (gPowZPowX.compareTo(BigInteger.ONE) == -1 ||
                gPowZPowX.compareTo(p.subtract(BigInteger.ONE)) == 1 ||
                gPowZPowX.modPow(q, p).compareTo(BigInteger.ONE) != 0) {

            return false;
        }

        // Check d) - Use the straightforward way with 2 exp. Using a simultaneous computation technique only needs 1 exp.
        // g^s = g^t (g^x)^h
        if (g.modPow(t, p).multiply(gPowX.modPow(h, p)).mod(p).compareTo(gPowS) != 0) {
            return false;
        }

        // Check e) - Use the same method as in d)
        // (g^z)^s = (g^z)^t ((g^x)^z)^h
        if (gPowZ.modPow(t, p).multiply(gPowZPowX.modPow(h, p)).mod(p).compareTo(gPowZPowS) != 0) {
            return false;
        }

        return true;
    }

    public BigInteger getSHA256(BigInteger g, BigInteger gPowX, BigInteger gPowZ, BigInteger gPowZPowX,
                                BigInteger gPowS, BigInteger gPowXPowS, String userID) {

        MessageDigest sha256 = null;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");

            byte [] gBytes = g.toByteArray();
            byte [] gPowXBytes = gPowX.toByteArray();
            byte [] gPowZBytes = gPowZ.toByteArray();
            byte [] gPowZPowXBytes = gPowZPowX.toByteArray();
            byte [] gPowSBytes = gPowS.toByteArray();
            byte [] gPowXPowSBytes = gPowXPowS.toByteArray();
            byte [] userIDBytes = userID.getBytes();

            sha256.update(ByteBuffer.allocate(4).putInt(gBytes.length).array());
            sha256.update(gBytes);

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

    public BigInteger getSHA256(BigInteger gen, BigInteger genPowV, BigInteger genPowX, String userID) {

        MessageDigest sha256 = null;

        try {
            sha256 = MessageDigest.getInstance("SHA-256");

            byte [] genBytes = gen.toByteArray();
            byte [] genPowVBytes = genPowV.toByteArray();
            byte [] genPowXBytes = genPowX.toByteArray();
            byte [] userIDBytes = userID.getBytes();

            // Prepend each item with a 4-byte length
            sha256.update(ByteBuffer.allocate(4).putInt(genBytes.length).array());
            sha256.update(genBytes);

            sha256.update(ByteBuffer.allocate(4).putInt(genPowVBytes.length).array());
            sha256.update(genPowVBytes);

            sha256.update(ByteBuffer.allocate(4).putInt(genPowXBytes.length).array());
            sha256.update(genPowXBytes);

            sha256.update(ByteBuffer.allocate(4).putInt(userIDBytes.length).array());
            sha256.update(userIDBytes);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BigInteger(sha256.digest());
    }


    public BigInteger getSHA256 (String s)
    {
        MessageDigest sha256 = null;

        try {
            sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(s.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BigInteger(sha256.digest());
    }

    public BigInteger getSHA256 (BigInteger s)
    {
        MessageDigest sha256 = null;

        try {
            sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(s.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BigInteger(sha256.digest());
    }

    public BigInteger getSHA256 (BigInteger s, String str)
    {
        MessageDigest sha256 = null;

        try {
            sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(s.toByteArray());
            sha256.update(str.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BigInteger(sha256.digest());
    }

    public void exitWithError(String s){
        Log.d("speke",  "Exit with ERROR: " + s);
        System.exit(0);
    }



    // Based on P. 5 at https://eprint.iacr.org/2014/364.pdf
    private class ChaumPedersonZKP {

        private BigInteger gPowS = null;
        private BigInteger gPowZPowS = null;
        private BigInteger t = null;

        private ChaumPedersonZKP () {
            // Constructor
        }

        private void generateZKP(BigInteger p, BigInteger q, BigInteger g, BigInteger gPowX, BigInteger x,
                                 BigInteger gPowZ, BigInteger gPowZPowX, String signerID) {

            // Generate s from [1, q-1] and compute (A, B) = (gen^s, genPowZ^s)
            BigInteger s = org.bouncycastle.util.BigIntegers.createRandomInRange(BigInteger.ONE,
                    q.subtract(BigInteger.ONE), new SecureRandom());
            gPowS = g.modPow(s, p);
            gPowZPowS = gPowZ.modPow(s, p);

            BigInteger h = getSHA256(g,gPowX,gPowZ,gPowZPowX,gPowS,gPowZPowS,signerID); // challenge

            t = s.subtract(x.multiply(h)).mod(q); // t = s-cr
        }

        private BigInteger getGPowS() {
            return gPowS;
        }

        private BigInteger getGPowZPowS() {
            return gPowZPowS;
        }

        private BigInteger getT() {
            return t;
        }
    }

    private class SchnorrZKP {

        private BigInteger genPowV = null;
        private BigInteger r = null;

        private SchnorrZKP () {
            // constructor
        }

        private void generateZKP (BigInteger p, BigInteger q, BigInteger gen,
                                  BigInteger genPowX, BigInteger x, String signerID){

            /* Generate a random v from [0, q-1], and compute V = gen^v */
            BigInteger v = org.bouncycastle.util.BigIntegers.createRandomInRange(BigInteger.ZERO,
                    q.subtract(BigInteger.ONE), new SecureRandom());
            genPowV = gen.modPow(v,p);
            BigInteger h = getSHA256(gen,genPowV,genPowX,signerID); // h

            r = v.subtract(x.multiply(h)).mod(q); // r = v-x*h
        }

        private BigInteger getGenPowV() {
            return genPowV;
        }

        private BigInteger getR() {
            return r;
        }
    }
}
