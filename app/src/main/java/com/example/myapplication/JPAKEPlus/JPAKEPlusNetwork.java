package com.example.myapplication.JPAKEPlus;

import android.util.Log;

import com.example.myapplication.JPAKEPlus.POJOs.RoundOne;
import com.example.myapplication.JPAKEPlus.POJOs.RoundOneResponse;
import com.example.myapplication.JPAKEPlus.POJOs.RoundThree;
import com.example.myapplication.JPAKEPlus.POJOs.RoundThreeResponse;
import com.example.myapplication.JPAKEPlus.POJOs.RoundTwo;
import com.example.myapplication.JPAKEPlus.POJOs.RoundTwoResponse;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class JPAKEPlusNetwork {
    String sStr;
    BigInteger p;
    BigInteger q;
    BigInteger g;
    int n;

    BigInteger s;
    int clientId;

    long startTime;
    long endTime;
    TreeMap<String, Long> time = new TreeMap<>();
    // ROUND 1
    ConcurrentHashMap<Long, BigInteger> aij = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> gPowAij = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, ArrayList<BigInteger>> schnorrZKPaij = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> bij = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger>  gPowBij = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, ArrayList<BigInteger>> schnorrZKPbij = new ConcurrentHashMap<>();
    BigInteger yi;
    BigInteger gPowYi;
    BigInteger gPowZi;
    ArrayList<BigInteger> schnorrZKPyi = new ArrayList<>();
    String signerID;
    SchnorrZKP schnorrZKP = new SchnorrZKP();

    // *********************************** ROUND 2 ***********************************
    ConcurrentHashMap<Long, BigInteger> newGen = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> bijs = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> newGenPowBijs = new ConcurrentHashMap<>();;
    ConcurrentHashMap<Long, ArrayList<BigInteger>> schnorrZKPbijs = new ConcurrentHashMap<>();

    // *********************************** ROUND 3 ***********************************
    BigInteger gPowZiPowYi;
    ArrayList<BigInteger> chaumPedersonZKPi = new ArrayList<>();
    ConcurrentHashMap<Long, BigInteger> pairwiseKeysMAC = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> pairwiseKeysKC = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> hMacsMAC = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, BigInteger> hMacsKC = new ConcurrentHashMap<>();
    //   ************************************ KEYS ************************************
    BigInteger sessionKeys;

    ArrayList<Long> clients = new ArrayList<>();

    public JPAKEPlusNetwork(String sStr, BigInteger p, BigInteger q, BigInteger g, String id, ArrayList<Long> clients) {
        this.sStr = sStr;
        this.p = p;
        this.q = q;
        this.g = g;
        this.n = clients.size();
        this.signerID = id;
        s = getSHA256(this.sStr);
        this.clients = clients;
    }

    public void modTest() {
        BigInteger X = org.bouncycastle.util.BigIntegers.createRandomInRange(BigInteger.ZERO,
                q.subtract(BigInteger.ONE), new SecureRandom());

        startTime = System.currentTimeMillis();

        g.modPow(X, p);

        endTime = System.currentTimeMillis();
        System.out.println("mod time: "
                + (endTime-startTime));
    }

    public RoundOne roundOne() {
        long cID = Long.parseLong(signerID);
        Log.d("JPAKEPairing", "*************** ROUND 1 ***************");
        int n = clients.size();
//        signerID = clientId + "";

        startTime = System.currentTimeMillis();
        // aij in [0, q-1], b_ij in [1, q-1]

        for (int j=0; j<n; j++) {
            long jID = clients.get(j);
            if (cID==jID){
                continue;
            }

            // aij and ZKP
            aij.put(jID, org.bouncycastle.util.BigIntegers.createRandomInRange(BigInteger.ZERO,
                    q.subtract(BigInteger.ONE), new SecureRandom()));

            gPowAij.put(jID, g.modPow(aij.get(jID), p));

            schnorrZKP.generateZKP(p, q, g, gPowAij.get(jID), aij.get(jID), signerID);
            schnorrZKPaij.put(jID, new ArrayList<BigInteger>());
            schnorrZKPaij.get(jID).add(schnorrZKP.getGenPowV());
            schnorrZKPaij.get(jID).add(schnorrZKP.getR());


            // bij and ZKP
            bij.put(jID, org.bouncycastle.util.BigIntegers.createRandomInRange(BigInteger.ONE,
                    q.subtract(BigInteger.ONE), new SecureRandom()));

            gPowBij.put(jID, g.modPow(bij.get(jID),p));
            schnorrZKP.generateZKP(p, q, g, gPowBij.get(jID), bij.get(jID), signerID);
            schnorrZKPbij.put(jID, new ArrayList<BigInteger>());
            schnorrZKPbij.get(jID).add(schnorrZKP.getGenPowV());
            schnorrZKPbij.get(jID).add(schnorrZKP.getR());
        }

        // yi from Zq and ZKP
        yi = (org.bouncycastle.util.BigIntegers.createRandomInRange(BigInteger.ZERO,
                q.subtract(BigInteger.ONE), new SecureRandom()));

        gPowYi = g.modPow(yi, p);
        schnorrZKP.generateZKP(p, q, g, gPowYi, yi, signerID);
        schnorrZKPyi.add(schnorrZKP.getGenPowV());
        schnorrZKPyi.add(schnorrZKP.getR());

        Log.d("JPAKEPairing", "********** SEND ROUND 1 DATA **********");
        RoundOne dataObject = new RoundOne();
        dataObject.setAij(aij);
        dataObject.setBij(bij);
        dataObject.setgPowAij(gPowAij);
        dataObject.setgPowBij(gPowBij);
        dataObject.setgPowYi(gPowYi);
        dataObject.setgPowZi(gPowZi);
        dataObject.setSchnorrZKPaij(schnorrZKPaij);
        dataObject.setSchnorrZKPbij(schnorrZKPbij);
        dataObject.setSchnorrZKPyi(schnorrZKPyi);
        dataObject.setYi(yi);
        dataObject.setSignerID(signerID);

        endTime = System.currentTimeMillis();
        time.put("1) Latency of computing round 1 per participant (ms):", (endTime-startTime));
        return dataObject;
    }

    public boolean verifyRoundOne(RoundOneResponse r1) {
        Log.d("JPAKEPairing", "************ VERIFY ROUND 1 ***********");


        startTime = System.currentTimeMillis();
        long cID = Long.parseLong(signerID);
        for (int i=0; i<n; i++) {

            int iPlusOne = (i==n-1) ? 0: i+1;
            int iMinusOne = (i==0) ? n-1 : i-1;

            long current = Long.parseLong(r1.getSignerID().get(i));
            long rightNeighbour = Long.parseLong(r1.getSignerID().get(iPlusOne));
            long leftNeighbour = Long.parseLong(r1.getSignerID().get(iMinusOne));
            r1.getgPowZi().put(current, r1.getgPowYi().get(leftNeighbour).modInverse(p).multiply(r1.getgPowYi().get(rightNeighbour)).mod(p));
            if(r1.getgPowZi().get(current).compareTo(BigInteger.ONE) == 0) {
                Log.d("JPAKEPairing", "Round 1 verification failed at checking g^{y_{i+1}}/g^{y_{i-1}}!=1 for i="+i);
            }
        }


        for (int j=0; j<n; j++) {
            long jID = clients.get(j);
            if (cID==jID) {
                continue;
            }

            // Check ZKP{bji}
            if(!verifySchnorrZKP(p, q, g,
                    r1.getgPowBij().get(jID).get(cID),
                    r1.getSchnorrZKPbij().get(jID).get(cID).get(0),
                    r1.getSchnorrZKPbij().get(jID).get(cID).get(1),
                    Long.toString(jID))) {
                Log.d("JPAKEPairing", "Round 1 verification failed at checking SchnorrZKP for bij. (i,j)="+"(" + cID + "," +jID + ")");
                return false;
            }

            // check g^{b_ji} != 1
            if (r1.getgPowBij().get(jID).get(cID).compareTo(BigInteger.ONE) == 0) {
                Log.d("JPAKEPairing", "Round 1 verification failed at checking g^{ji} !=1");
                return false;
            }

            // Check ZKP{aji}

            if(!verifySchnorrZKP(p, q, g,
                    r1.getgPowAij().get(jID).get(cID),
                    r1.getSchnorrZKPaij().get(jID).get(cID).get(0),
                    r1.getSchnorrZKPaij().get(jID).get(cID).get(1),
                    Long.toString(jID))) {
                Log.d("JPAKEPairing", "Round 1 verification failed at checking SchnorrZKP for aij. (i,j)="+"(" + cID + "," + jID + ")");
                return false;
            }

            // Check ZKP{yi}
            if (!verifySchnorrZKP(p, q, g,
                    r1.getgPowYi().get(jID),
                    r1.getSchnorrZKPyi().get(jID).get(0),
                    r1.getSchnorrZKPyi().get(jID).get(1),
                    Long.toString(jID))) {
                Log.d("JPAKEPairing", "Round 1 verification failed at checking SchnorrZKP for yi. (i,j)="+"(" + cID + "," +jID + ")");
                return false;
            }
        }

        endTime = System.currentTimeMillis();
        time.put("2) Latency of verifying data in round 1 per participant (ms):", (endTime-startTime));
        return true;
    }

    public RoundTwo roundTwo(RoundOneResponse r) {
        Log.d("JPAKEPairing", "*************** ROUND 2 ***************");
        startTime = System.currentTimeMillis();
        long cID = Long.parseLong(signerID);
        // Each participant sends newGen^{bij * s} and ZKP{bij * s}
        for (int j=0; j<n; j++) {

            long jID = clients.get(j);
            if (cID==jID){
                continue;
            }

            // g^{a_ij} * g^{a_ji} * g^{b_jj} mod p
            newGen.put(jID,
                    gPowAij.get(jID)
                            .multiply(r.getgPowAij().get(jID).get(cID))
                            .multiply(r.getgPowBij().get(jID).get(cID)).mod(p));

            // b_ij * s
            bijs.put(jID, bij.get(jID).multiply(s).mod(q));

            // (g^{a_ij} * g^{a_ji} * g^{b_jj} mod p)^{b_ij * s}
            newGenPowBijs.put(jID, newGen.get(jID).modPow(bijs.get(jID), p));

            schnorrZKP.generateZKP(p, q, newGen.get(jID), newGenPowBijs.get(jID), bijs.get(jID), signerID);
            schnorrZKPbijs.put(jID, new ArrayList<BigInteger>());
            schnorrZKPbijs.get(jID).add(schnorrZKP.getGenPowV());
            schnorrZKPbijs.get(jID).add(schnorrZKP.getR());
        }


        RoundTwo data = new RoundTwo();
//            dataRoundTwo.setBijs(bijs);
        data.setNewGen(newGen);
        data.setNewGenPowBijs(newGenPowBijs);
        data.setSchnorrZKPbijs(schnorrZKPbijs);
        data.setSignerID(signerID);

        endTime = System.currentTimeMillis();
        time.put("3) Latency of computing round 2 per participant (ms):", (endTime-startTime));
        return data;
    }

    public boolean verifyRoundTwo(RoundTwoResponse r2) {
        Log.d("JPAKEPairing", "************ VERIFY ROUND 2 ***********");
        startTime = System.currentTimeMillis();
        long cID = Long.parseLong(signerID);
        //             each participant verifies ZKP{bijs}
        for (int j=0; j<n; j++) {
            long jID = clients.get(j);
            if (cID==jID){
                continue;
            }

            // Check ZKP{bji}
            if(!verifySchnorrZKP(p, q,
                    r2.getNewGen().get(jID).get(cID),
                    r2.getNewGenPowBijs().get(jID).get(cID),
                    r2.getSchnorrZKPbijs().get(jID).get(cID).get(0),
                    r2.getSchnorrZKPbijs().get(jID).get(cID).get(1),
                    Long.toString(jID))) {
                Log.d("jpake", "Round 2 verification failed at checking SchnorrZKP for bij. (i,j)=" + "(" + clientId + "," + jID + ")");
                return false;
            }
        }

        endTime = System.currentTimeMillis();
        time.put("4) Latency of verifying round 2 per participant (ms):", (endTime-startTime));
        return true;
    }

    public RoundThree roundThree(RoundOneResponse r1, RoundTwoResponse r2) {
        Log.d("JPAKEPairing", "*************** ROUND 3 ***************");
        startTime = System.currentTimeMillis();
        long cID = Long.parseLong(signerID);
        gPowZiPowYi = r1.getgPowZi().get(cID).modPow(r1.getYi().get(cID), p);

        ChaumPedersonZKP chaumPedersonZKP = new ChaumPedersonZKP();

        chaumPedersonZKP.generateZKP(p, q, g,
                r1.getgPowYi().get(cID),
                r1.getYi().get(cID),
                r1.getgPowZi().get(cID),
                gPowZiPowYi,
                signerID);

        chaumPedersonZKPi.add(chaumPedersonZKP.getGPowS());
        chaumPedersonZKPi.add(chaumPedersonZKP.getGPowZPowS());
        chaumPedersonZKPi.add(chaumPedersonZKP.getT());

        // Compute pairwise keys
        for (int j=0; j<n; j++) {
            long jID = clients.get(j);
            if (cID==jID){
                continue;
            }

            BigInteger rawKey = r1.getgPowBij().get(jID).get(cID)
                    .modPow(bijs.get(jID), p)
                    .modInverse(p)
                    .multiply(r2.getNewGenPowBijs().get(jID).get(cID))
                    .modPow(bij.get(jID), p);

            pairwiseKeysMAC.put(jID, getSHA256(rawKey, "MAC"));
            pairwiseKeysKC.put(jID, getSHA256(rawKey, "KC"));

            // Compute MAC
            String hmacName = "HMac-SHA256";

            try {
                SecretKey key = new SecretKeySpec(pairwiseKeysMAC.get(jID).toByteArray(), hmacName);
                Mac mac = Mac.getInstance(hmacName);
                mac.init(key);
                mac.update(r1.getgPowYi().get(cID).toByteArray());
                mac.update(r1.getSchnorrZKPyi().get(cID).get(0).toByteArray());
                mac.update(r1.getSchnorrZKPyi().get(cID).get(1).toByteArray());
                mac.update(gPowZiPowYi.toByteArray());
                mac.update(chaumPedersonZKPi.get(0).toByteArray());
                mac.update(chaumPedersonZKPi.get(1).toByteArray());
                mac.update(chaumPedersonZKPi.get(2).toByteArray());

                hMacsMAC.put(jID, new BigInteger(mac.doFinal()));

            } catch(Exception e) {

                e.printStackTrace();
                System.exit(0);

            }

            // Compute HMAC for key confirmation
            try{
                SecretKey key = new SecretKeySpec(pairwiseKeysKC.get(jID).toByteArray(), hmacName);
                Mac mac = Mac.getInstance(hmacName);
                mac.init(key);
                mac.update("KC".getBytes());
                mac.update(new BigInteger(""+cID).toByteArray());
                mac.update(new BigInteger(""+jID).toByteArray());

                mac.update(r1.getgPowAij().get(cID).get(jID).toByteArray());
                mac.update(r1.getgPowBij().get(cID).get(jID).toByteArray());

                mac.update(r1.getgPowAij().get(jID).get(cID).toByteArray());
                mac.update(r1.getgPowBij().get(jID).get(cID).toByteArray());

                hMacsKC.put(jID, new BigInteger(mac.doFinal()));
            } catch(Exception e) {

                e.printStackTrace();
                System.exit(0);

            }
        }

        RoundThree data = new RoundThree();
        data.setChaumPedersonZKPi(chaumPedersonZKPi);
        data.setgPowZiPowYi(gPowZiPowYi);
        data.sethMacsKC(hMacsKC);
        data.sethMacsMAC(hMacsMAC);
        data.setPairwiseKeysKC(pairwiseKeysKC);
        data.setPairwiseKeysMAC(pairwiseKeysMAC);

        endTime = System.currentTimeMillis();
        time.put("5) Latency of computing round 3 per participant (ms):", (endTime-startTime));
        return data;
    }

    public boolean verifyRoundThree(RoundOneResponse r1, RoundThreeResponse r3) {
        Log.d("JPAKEPairing", "*************** ROUND 4 ***************");
        startTime = System.currentTimeMillis();
        long cID = Long.parseLong(signerID);
        // ith participant
        for (int j=0; j<n; j++) {
            // check ZKP - except ith
            long jID = clients.get(j);
            if (cID==jID) {
                continue;
            }

            if (!verifyChaumPedersonZKP(p, q, g,
                    r1.getgPowYi().get(jID),
                    r1.getgPowZi().get(jID),
                    r3.getgPowZiPowYi().get(jID),
                    r3.getChaumPedersonZKPi().get(jID).get(0),
                    r3.getChaumPedersonZKPi().get(jID).get(1),
                    r3.getChaumPedersonZKPi().get(jID).get(2),
                    Long.toString(jID))) {
                exitWithError("Round 2 verification failed at checking jth Chaum-Pederson for (i,j)=("+cID+","+jID+")");
            }

            // Check key confirmation - except ith
            String hmacName = "HMac-SHA256";

            if (cID == jID) {
                continue;
            }

            SecretKey key = new SecretKeySpec(r3.getPairwiseKeysKC().get(cID).get(jID).toByteArray(), hmacName);

            try {
                Mac mac = Mac.getInstance(hmacName);
                mac.init(key);

                mac.update("KC".getBytes());
                mac.update(new BigInteger(""+jID).toByteArray());
                mac.update(new BigInteger(""+cID).toByteArray());

                mac.update(r1.getgPowAij().get(jID).get(cID).toByteArray());
                mac.update(r1.getgPowBij().get(jID).get(cID).toByteArray());

                mac.update(r1.getgPowAij().get(cID).get(jID).toByteArray());
                mac.update(r1.getgPowBij().get(cID).get(jID).toByteArray());
                BigInteger temp = new BigInteger(mac.doFinal());

                if (temp.compareTo(r3.gethMacsKC().get(jID).get(cID)) != 0) {
                    exitWithError("Round 3 verification failed at checking KC for (i,j)=("+cID+","+jID+")");
                }
            } catch(Exception e) {
                e.printStackTrace();
                System.exit(0);
            }

            // Check MACs - except ith
            if (cID == jID) {
                continue;
            }

            key = new SecretKeySpec(r3.getPairwiseKeysMAC().get(cID).get(jID).toByteArray(), hmacName);

            try {
                Mac mac = Mac.getInstance(hmacName);
                mac.init(key);
                mac.reset();

                mac.update(r1.getgPowYi().get(jID).toByteArray());
                mac.update(r1.getSchnorrZKPyi().get(jID).get(0).toByteArray());
                mac.update(r1.getSchnorrZKPyi().get(jID).get(1).toByteArray());

                mac.update(r3.getgPowZiPowYi().get(jID).toByteArray());
                mac.update(r3.getChaumPedersonZKPi().get(jID).get(0).toByteArray());
                mac.update(r3.getChaumPedersonZKPi().get(jID).get(1).toByteArray());
                mac.update(r3.getChaumPedersonZKPi().get(jID).get(2).toByteArray());

                if (new BigInteger(mac.doFinal()).compareTo(r3.gethMacsMAC().get(jID).get(cID)) != 0) {
                    exitWithError("Round 2 verification failed at checking MACs for (i,j)=("+cID+","+jID+")");

                }
            }catch(Exception e){

                e.printStackTrace();
                System.exit(0);

            }
        }

        endTime = System.currentTimeMillis();

        time.put("6) Latency of verifying round 3 for participant (ms):", (endTime-startTime));
        return true;
    }

    public BigInteger computeKey(RoundOneResponse r1, RoundThreeResponse r3) {
        ConcurrentHashMap<Long, BigInteger> multipleSessionKeys = new ConcurrentHashMap<>();
        Log.d("JPAKEPairing", "*********** KEY COMPUTATION ***********");
        startTime = System.currentTimeMillis();
        long iID = Long.parseLong(signerID);
        int i = r1.getSignerID().indexOf(signerID);
        // ith participant
        int cyclicIndex = getCyclicIndex(i-1, n);
        BigInteger firstTerm = r1.getgPowYi().get(clients.get(cyclicIndex))
                .modPow(r1.getYi().get(iID).multiply(BigInteger.valueOf(n)), p);
        BigInteger finalTerm = firstTerm;

        for (int j=0; j<(n-1) ; j++) {
            cyclicIndex = getCyclicIndex(i+j, n);
            BigInteger interTerm = r3.getgPowZiPowYi().get(Long.parseLong(r1.getSignerID().get(cyclicIndex)))
                    .modPow(BigInteger.valueOf(n-1-j), p);
            finalTerm = finalTerm.multiply(interTerm).mod(p);
        }

        multipleSessionKeys.put(clients.get(i), getSHA256(finalTerm));
        sessionKeys =  getSHA256(finalTerm);


        BigInteger key = getSHA256(finalTerm);
        endTime = System.currentTimeMillis();
        time.put("7) Latency of computing key for participant (ms):", (endTime-startTime));
        displayLatency();
        return key;
    }

    public void displayLatency() {
        System.out.println("\nLatency of Each Round JPAKEPairing+\n");
        for (Map.Entry<String, Long> e : time.entrySet()) {
            Log.d("LATENCY", e.getKey() + e.getValue());
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


    public static BigInteger getSHA256(BigInteger gen, BigInteger genPowV, BigInteger genPowX, String userID) {

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


    public void exitWithError(String s){
        Log.d("JPAKEPairing", "Exit with ERROR: " + s);
        System.exit(0);
    }

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

            /* Generate a random v from [1, q-1], and compute V = gen^v */
            BigInteger v = org.bouncycastle.util.BigIntegers.createRandomInRange(BigInteger.ONE,
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
