package com.example.myapplication.JPAKEPlusEC;

import android.util.Log;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.example.myapplication.JPAKEPlusEC.POJOs.ECRoundOne;
import com.example.myapplication.JPAKEPlusEC.POJOs.ECRoundOneResponse;
import com.example.myapplication.JPAKEPlusEC.POJOs.ECRoundThree;
import com.example.myapplication.JPAKEPlusEC.POJOs.ECRoundThreeResponse;
import com.example.myapplication.JPAKEPlusEC.POJOs.ECRoundTwo;
import com.example.myapplication.JPAKEPlusEC.POJOs.ECRoundTwoResponse;
import com.example.myapplication.JPAKEPlusEC.ZeroKnowledgeProofs.*;
//import org.bouncycastle.math.ec.ECPoint;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

public class JPAKEPlusECNetwork {
    private ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("prime256v1");
    // Domain parameters
    private ECCurve.Fp ecCurve = (ECCurve.Fp)ecSpec.getCurve();
    private BigInteger a = ecCurve.getA().toBigInteger();
    private BigInteger b = ecCurve.getB().toBigInteger();
    private BigInteger q = ecCurve.getQ();
    private BigInteger coFactor = ecSpec.getH(); // Not using the symbol "h" here to avoid confusion as h will be used later in SchnorrZKP.
    private BigInteger nEC = ecSpec.getN();
    private ECPoint G = ecSpec.getG();


    String sStr;
    //    BigInteger p;
//    BigInteger q;
    BigInteger g;
    int n;
    BigInteger s;

    int clientId;
    long startTime;
    long endTime;
    TreeMap<String, Long> time = new TreeMap<>();
    // *********************************** ROUND 1 ***********************************
    private HashMap<Long, BigInteger> aij = new HashMap<>();
    private HashMap<Long, byte[]> gPowAij = new HashMap<>();
    private HashMap<Long, SchnorrZKP> schnorrZKPaij = new HashMap<>();
    private HashMap<Long, BigInteger> bij = new HashMap<>();
    private HashMap<Long, byte[]>  gPowBij = new HashMap<>();
    private HashMap<Long, SchnorrZKP> schnorrZKPbij = new HashMap<>();
    private BigInteger yi;
    private byte[] gPowYi;
    private byte[] gPowZi;
    private SchnorrZKP schnorrZKPyi = new SchnorrZKP();
    private String signerID;
    SchnorrZKP schnorrZKP = new SchnorrZKP();

    // *********************************** ROUND 2 ***********************************
//    BigInteger [][] newGen = new BigInteger [n][n];
    private HashMap<Long, byte[]> newGen = new HashMap<>();
    //    BigInteger [][] bijs = new BigInteger [n][n];
    private HashMap<Long, BigInteger> bijs = new HashMap<>();
    //    BigInteger [][] newGenPowBijs = new BigInteger [n][n];
    private HashMap<Long, byte[]> newGenPowBijs = new HashMap<>();;
    //    BigInteger [][][] schnorrZKPbijs = new BigInteger [n][n][2];
    private HashMap<Long, SchnorrZKP> schnorrZKPbijs = new HashMap<>();

    // *********************************** ROUND 3 ***********************************
//    BigInteger [] gPowZiPowYi = new BigInteger [n];
    byte[] gPowZiPowYi;
    //    BigInteger [][] chaumPedersonZKPi = new BigInteger [n][3]; // {g^s, (g^z)^s, t}
    ChaumPedersonZKP chaumPedersonZKPi = new ChaumPedersonZKP();
    //    BigInteger [][] pairwiseKeysMAC = new BigInteger [n][n];
    HashMap<Long, BigInteger> pairwiseKeysMAC = new HashMap<>();
    //    BigInteger [][] pairwiseKeysKC = new BigInteger [n][n];
    HashMap<Long, BigInteger> pairwiseKeysKC = new HashMap<>();
    //    BigInteger [][] hMacsMAC = new BigInteger [n][n];
    HashMap<Long, BigInteger> hMacsMAC = new HashMap<>();
    //    BigInteger [][] hMacsKC = new BigInteger [n][n];
    HashMap<Long, BigInteger> hMacsKC = new HashMap<>();
    //   ************************************ KEYS ************************************
    BigInteger sessionKeys;


    ArrayList<Long> clients;

    public JPAKEPlusECNetwork(String sStr, int n, String id, ArrayList<Long> clients, int clientID) {
        this.sStr = sStr;
//        this.p = p;
//        this.q = q;
        this.g = g;
        this.n = n;
        this.signerID = id;
        this.clients = clients;
        this.s = getSHA256(this.sStr);
        this.clientId = clientID;
    }


    public ECRoundOne roundOne() {
        Log.d("ec", "*************** ROUND 1 ***************");
        startTime = System.currentTimeMillis();

        long cID = (long) clientId;
        int n = clients.size();

        signerID = clientId + "";
        HashMap<Long, ECPoint> temp = new HashMap<>();

        // aij in [0, q-1], b_ij in [1, q-1]
        for (int j=0; j<n; j++) {
            System.out.println(clients.get(j) instanceof Long);
            long jID = clients.get(j);
            if (cID==jID){
                continue;
            }

            // aij and ZKP
            aij.put(jID, org.bouncycastle.util.BigIntegers.createRandomInRange(BigInteger.ZERO,
                    nEC.subtract(BigInteger.ONE), new SecureRandom()));

            ECPoint AIJ = G.multiply(aij.get(jID));
            gPowAij.put(jID, AIJ.getEncoded(false));
            SchnorrZKP zkpAij = new SchnorrZKP();
            zkpAij.generateZKP(G, nEC, aij.get(jID), AIJ, signerID);
            schnorrZKPaij.put(jID, zkpAij);

            // bij and ZKP
            bij.put(jID, org.bouncycastle.util.BigIntegers.createRandomInRange(BigInteger.ONE,
                    nEC.subtract(BigInteger.ONE), new SecureRandom()));

            ECPoint BIJ = G.multiply(bij.get(jID));
            gPowBij.put(jID, BIJ.getEncoded(false));
            SchnorrZKP zkpBij = new SchnorrZKP();
            zkpBij.generateZKP(G, nEC, bij.get(jID), BIJ, signerID);
            schnorrZKPbij.put(jID, zkpBij);
        }

        // yi from Zq and ZKP
        yi = org.bouncycastle.util.BigIntegers.createRandomInRange(BigInteger.ZERO,
                nEC.subtract(BigInteger.ONE), new SecureRandom());

        ECPoint YI = G.multiply(yi);
        gPowYi = YI.getEncoded(false);

        signerID = clientId + "";
        schnorrZKP.generateZKP(G, nEC, yi, YI, signerID);
        schnorrZKPyi = schnorrZKP;

        Log.d("ec", "********** SEND ROUND 1 DATA **********");

        ECRoundOne dataObject = new ECRoundOne();
        dataObject.setAij(aij);
        dataObject.setBij(bij);
        dataObject.setgPowAij(gPowAij);
        dataObject.setgPowBij(gPowBij);

        dataObject.setgPowYi(gPowYi);
//        dataObject.setgPowZi(gPowZi.getEncoded(false));
        dataObject.setSchnorrZKPaij(schnorrZKPaij);
        dataObject.setSchnorrZKPbij(schnorrZKPbij);
        dataObject.setSchnorrZKPyi(schnorrZKPyi);
        dataObject.setYi(yi);
        dataObject.setSignerID(signerID);
        endTime = System.currentTimeMillis();
        time.put("1) Latency of computing round 1 per participant (ms):", (endTime-startTime));
        return dataObject;
    }

    public boolean verifyRoundOne(ECRoundOneResponse r) {
        Log.d("ec", "************ VERIFY ROUND 1 ***********");
        startTime = System.currentTimeMillis();
        long cID = (long) clientId;
        for (int i=0; i<n; i++) {

            int iPlusOne = (i==n-1) ? 0: i+1;
            int iMinusOne = (i==0) ? n-1 : i-1;

            long current = Long.parseLong(r.getSignerID().get(i));
            long rightNeighbour = Long.parseLong(r.getSignerID().get(iPlusOne));
            long leftNeighbour = Long.parseLong(r.getSignerID().get(iMinusOne));

            ECPoint leftPoint = ecCurve.decodePoint(r.getgPowYi().get(leftNeighbour));
            ECPoint rightPoint = ecCurve.decodePoint(r.getgPowYi().get(rightNeighbour));
            r.getgPowZi().put(current, rightPoint.subtract(leftPoint).getEncoded(false));
            if(new BigInteger(r.getgPowZi().get(current)).compareTo(BigInteger.ONE) == 0) {
                Log.d("ec", "Round 1 verification failed at checking g^{y_{i+1}}/g^{y_{i-1}}!=1 for i="+i);
                return false;
            }
        }


        for (int j=0; j<n; j++) {
            long jID = clients.get(j);
            if (cID==jID) {
                continue;
            }

            // Check ZKP{bji}
            ECPoint BIJ = ecCurve.decodePoint(r.getgPowBij().get(jID).get(cID));

            ECPoint V = ecCurve.decodePoint(r.getSchnorrZKPbij().get(jID).get(cID).getV());
            if (!verifyZKP(G,
                    BIJ,
                    V,
                    r.getSchnorrZKPbij().get(jID).get(cID).getr(),
                    Long.toString(clients.get(j)))) {
                Log.d("ec", "Round 1 verification failed at checking SchnorrZKP for bij. (i,j)="+"(" + cID + "," +jID + ")");
                System.exit(0);
            }
            else {
                Log.d("ec", "ALL GOOD");
            }

            // check g^{b_ji} != 1
            if (new BigInteger(r.getgPowBij().get(jID).get(cID)).compareTo(BigInteger.ONE) == 0) {
                Log.d("ec", "Round 1 verification failed at checking g^{ji} !=1");
                return false;
            }

            // Check ZKP{aji}
            ECPoint AIJ = ecCurve.decodePoint(r.getgPowAij().get(jID).get(cID));
            V = ecCurve.decodePoint(r.getSchnorrZKPaij().get(jID).get(cID).getV());
            if (!verifyZKP(G,
                    AIJ,
                    V,
                    r.getSchnorrZKPaij().get(jID).get(cID).getr(),
                    Long.toString(clients.get(j)))) {
                Log.d("ec", "Round 1 verification failed at checking SchnorrZKP for aij. (i,j)="+"(" + cID + "," + jID + ")");
                return false;
            }

            // Check ZKP{yi}
            ECPoint YI = ecCurve.decodePoint(r.getgPowYi().get(jID));
            V = ecCurve.decodePoint(r.getSchnorrZKPyi().get(jID).getV());
            if (!verifyZKP(G,
                    YI,
                    V,
                    r.getSchnorrZKPyi().get(jID).getr(),
                    Long.toString(clients.get(j)))) {
                Log.d("ec", "Round 1 verification failed at checking SchnorrZKP for yi. (i,j)="+"(" + cID + "," +jID + ")");
                return false;
            }
        }
        endTime = System.currentTimeMillis();
        time.put("2) Latency of verifying data in round 1 per participant (ms):", (endTime-startTime));
        return true;
    }

    public ECRoundTwo roundTwo(ECRoundOneResponse r) {
        Log.d("ec", "*************** ROUND 2 ***************");
        startTime = System.currentTimeMillis();

        long cID = (long) clientId;
        // Each participant sends newGen^{bij * s} and ZKP{bij * s}
        for (int j=0; j<n; j++) {
            long jID = clients.get(j);
            if (cID==jID){
                continue;
            }
            // g^{a_ij} * g^{a_ji} * g^{b_jj} mod p
            ECPoint newGenAij = ecCurve.decodePoint(r.getgPowAij().get(cID).get(jID));
            ECPoint newGenAji = ecCurve.decodePoint(r.getgPowAij().get(jID).get(cID));
            ECPoint newGenBji = ecCurve.decodePoint(r.getgPowBij().get(jID).get(cID));
            ECPoint newGenEC = newGenAij.add(newGenAji).add(newGenBji);
            newGen.put(jID, newGenEC.getEncoded(false));

            // b_ij * s
            bijs.put(jID, r.getBij().get(cID).get(jID).multiply(s).mod(nEC));

            // (g^{a_ij} * g^{a_ji} * g^{b_jj} mod p)^{b_ij * s}
            ECPoint newGenPowBijsEC = newGenEC.multiply(bijs.get(jID));
            newGenPowBijs.put(jID, newGenPowBijsEC.getEncoded(false));

            SchnorrZKP zkpBijs = new SchnorrZKP();
            zkpBijs.generateZKP(newGenEC, nEC, bijs.get(jID), newGenPowBijsEC, signerID);
            schnorrZKPbijs.put(jID, zkpBijs);

            newGenEC = ecCurve.decodePoint(newGen.get(jID));
            newGenPowBijsEC = ecCurve.decodePoint(newGenPowBijs.get(jID));
            ECPoint V = ecCurve.decodePoint(schnorrZKPbijs.get(jID).getV());

            if(!verifyZKP(newGenEC,
                    newGenPowBijsEC,
                    V,
                    schnorrZKPbijs.get(jID).getr(),
                    signerID)) {
                Log.d("ec", "newGenEC Round 2 verification failed at checking SchnorrZKP for bij. (i,j)="+"(" + clientId + "," + jID + ")");
                System.exit(0);
            }
            else {
                Log.d("ec", "ALL GOOD");
            }
        }


        ECRoundTwo data = new ECRoundTwo();
        data.setBijs(bijs);
        data.setNewGen(newGen);
        data.setNewGenPowBijs(newGenPowBijs);
        data.setSchnorrZKPbijs(schnorrZKPbijs);
        data.setSignerID(signerID);
        endTime = System.currentTimeMillis();
        time.put("3) Latency of computing round 2 per participant (ms):", (endTime-startTime));
        return data;
    }

    public boolean verifyRoundTwo(ECRoundTwoResponse r) {
        Log.d("ec", "************ VERIFY ROUND 2 ***********");
        startTime = System.currentTimeMillis();

        long cID = (long) clientId;
        //             each participant verifies ZKP{bijs}
        for (int j=0; j<n; j++) {
            long jID = clients.get(j);
            if (cID==jID){
                continue;
            }

            // Check ZKP{bji}
            ECPoint newGenEC = ecCurve.decodePoint(r.getNewGen().get(jID).get(cID));
            ECPoint newGenPowBijsEC = ecCurve.decodePoint(r.getNewGenPowBijs().get(jID).get(cID));
            ECPoint V = ecCurve.decodePoint(r.getSchnorrZKPbijs().get(jID).get(cID).getV());
            BigInteger zkpRVal = r.getSchnorrZKPbijs().get(jID).get(cID).getr();
            if(!verifyZKP(newGenEC,
                    newGenPowBijsEC,
                    V,
                    zkpRVal,
                    Long.toString(clients.get(j)))) {
                Log.d("ec", "newGenEC Round 2 verification failed at checking SchnorrZKP for bij. (i,j)="+"(" + clientId + "," + jID + ")");
                System.exit(0);
            }
            else {
                Log.d("ec", "ALL GOOD");
            }


        }
        endTime = System.currentTimeMillis();
        time.put("4) Latency of verifying round 2 per participant (ms):", (endTime-startTime));
        return true;
    }

    public ECRoundThree roundThree(ECRoundOneResponse r1, ECRoundTwoResponse r2) {
        Log.d("ec", "*************** ROUND 3 ***************");
        startTime = System.currentTimeMillis();

        long cID = (long) clientId;
//        gPowZiPowYi = r1.getgPowZi().get(cID).modPow(r1.getYi().get(cID), p);
        ECPoint zi = ecCurve.decodePoint(r1.getgPowZi().get(cID));
        BigInteger yi = r1.getYi().get(cID);
        gPowZiPowYi = zi.multiply(yi).getEncoded(false);
        ChaumPedersonZKP chaumPedersonZKP = new ChaumPedersonZKP();


        chaumPedersonZKP.generateZKP(G, nEC,
                ecCurve.decodePoint(r1.getgPowYi().get(cID)),
                r1.getYi().get(cID),
                ecCurve.decodePoint(r1.getgPowZi().get(cID)),
                zi.multiply(yi),
                signerID,
                q);
        chaumPedersonZKPi = chaumPedersonZKP;


        // Compute pairwise keys
        for (int j=0; j<n; j++) {
            long jID = clients.get(j);
            if (cID==jID) {
                continue;
            }

            // Kji = (Bji/g^(bij * bji * s))^bij
            ECPoint gPowBijEC = ecCurve.decodePoint(r1.getgPowBij().get(jID).get(cID));
            BigInteger bijsEC = r2.getBijs().get(cID).get(jID);

            ECPoint newGenPowBijsEC = ecCurve.decodePoint(r2.getNewGenPowBijs().get(jID).get(cID));
            BigInteger bijEC = r1.getBij().get(cID).get(jID);
            ECPoint rawKey = newGenPowBijsEC.subtract(gPowBijEC.multiply(bijsEC)).multiply(bijEC);

            pairwiseKeysMAC.put(jID, getSHA256(rawKey, "MAC"));
            pairwiseKeysKC.put(jID, getSHA256(rawKey, "KC"));

            // Compute MAC
            String hmacName = "HMac-SHA256";

            try {
                SecretKey key = new SecretKeySpec(pairwiseKeysMAC.get(jID).toByteArray(), hmacName);
                Mac mac = Mac.getInstance(hmacName);
                mac.init(key);
                mac.update(r1.getgPowYi().get(cID));
                mac.update(r1.getSchnorrZKPyi().get(cID).getV());
                mac.update(r1.getSchnorrZKPyi().get(cID).getr().toByteArray());
                mac.update(gPowZiPowYi);
                mac.update(chaumPedersonZKPi.getGPowS());
                mac.update(chaumPedersonZKPi.getGPowZPowS());
                mac.update(chaumPedersonZKPi.getT().toByteArray());
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

                mac.update(r1.getgPowAij().get(cID).get(jID));
                mac.update(r1.getgPowBij().get(cID).get(jID));
                mac.update(r1.getgPowAij().get(jID).get(cID));
                mac.update(r1.getgPowBij().get(jID).get(cID));

                hMacsKC.put(jID, new BigInteger(mac.doFinal()));
            } catch(Exception e) {

                e.printStackTrace();
                System.exit(0);

            }
        }



        ECRoundThree data = new ECRoundThree();
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

    public boolean roundFour(ECRoundOneResponse r1, ECRoundTwoResponse r2, ECRoundThreeResponse r3) {
        Log.d("ec", "*************** ROUND 4 ***************");
        startTime = System.currentTimeMillis();

        long cID = (long) clientId;
        // ith participant
        for (int j=0; j<n; j++) {
            // check ZKP - except ith
            long jID = clients.get(j);
            if (cID==jID) {
                continue;
            }

            if (!verifyChaumPedersonZKP(G, nEC,
                    ecCurve.decodePoint(r1.getgPowYi().get(jID)),
                    ecCurve.decodePoint(r1.getgPowZi().get(jID)),
                    ecCurve.decodePoint(r3.getgPowZiPowYi().get(jID)),
                    ecCurve.decodePoint(r3.getChaumPedersonZKPi().get(jID).getGPowS()),
                    ecCurve.decodePoint(r3.getChaumPedersonZKPi().get(jID).getGPowZPowS()),
                    r3.getChaumPedersonZKPi().get(jID).getT(),
                    Long.toString(jID))) {
                Log.d("ec", "Round 3 verification failed at checking jth Chaum-Pederson for (i,j)=("+cID+","+jID+")");
                return false;
            }
            else {
                Log.d("ec", "ALL GOOD");
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

                mac.update(r1.getgPowAij().get(jID).get(cID));
                mac.update(r1.getgPowBij().get(jID).get(cID));
                mac.update(r1.getgPowAij().get(cID).get(jID));
                mac.update(r1.getgPowBij().get(cID).get(jID));
                BigInteger temp = new BigInteger(mac.doFinal());
                System.out.println(temp);
                System.out.println(r3.gethMacsKC().get(jID).get(cID));
                if (temp.compareTo(r3.gethMacsKC().get(jID).get(cID)) != 0) {
                    Log.d("ec", "Round 3 verification failed at checking KC for (i,j)=("+cID+","+jID+")");
                    return false;
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

                mac.update(r1.getgPowYi().get(jID));
                mac.update(r1.getSchnorrZKPyi().get(jID).getV());
                mac.update(r1.getSchnorrZKPyi().get(jID).getr().toByteArray());
                mac.update(r3.getgPowZiPowYi().get(jID));
                mac.update(r3.getChaumPedersonZKPi().get(jID).getGPowS());
                mac.update(r3.getChaumPedersonZKPi().get(jID).getGPowZPowS());
                mac.update(r3.getChaumPedersonZKPi().get(jID).getT().toByteArray());

                if (new BigInteger(mac.doFinal()).compareTo(r3.gethMacsMAC().get(jID).get(cID)) != 0) {
                    Log.d("ec", "Round 2 verification failed at checking MACs for (i,j)=("+cID+","+jID+")");
                    return false;
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

    public BigInteger computeKey(ECRoundOneResponse r1, ECRoundThreeResponse r3) {
        Log.d("ec", "*********** KEY COMPUTATION ***********");
        startTime = System.currentTimeMillis();

        startTime = System.currentTimeMillis();

        int i = r1.getSignerID().indexOf(signerID);
        long iID = clients.get(i);

        // ith participant

        int cyclicIndex = getCyclicIndex(i-1, n);
        ECPoint gPowYiEC = ecCurve.decodePoint(r1.getgPowYi().get(clients.get(cyclicIndex)));
        ECPoint firstTerm = gPowYiEC
                .multiply(r1.getYi().get(iID).multiply(BigInteger.valueOf(n)));
        ECPoint finalTerm = firstTerm;

        for (int j=0; j<(n-1) ; j++) {
            cyclicIndex = getCyclicIndex(i+j, n);
            ECPoint gPowZiPowYiEC = ecCurve.decodePoint(r3.getgPowZiPowYi().get(clients.get(cyclicIndex)));
            ECPoint interTerm = gPowZiPowYiEC.multiply(BigInteger.valueOf(n-1-j));
            finalTerm = finalTerm.add(interTerm);
        }

        BigInteger key = getSHA256(finalTerm);
        endTime = System.currentTimeMillis();
        time.put("7) Latency of computing key for participant (ms):", (endTime-startTime));
        return key;
    }

    public void displayLatency() {
        System.out.println("\nLatency of Each Round JPAKE+\n");
        for (Map.Entry<String, Long> e : time.entrySet()) {
            Log.d("jpake", e.getKey() + e.getValue());
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

    public BigInteger getSHA256 (ECPoint s)
    {
        MessageDigest sha256 = null;

        try {
            sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(s.getEncoded(false));
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

    public BigInteger getSHA256 (ECPoint s, String str)
    {
        MessageDigest sha256 = null;

        try {
            sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(s.getEncoded(false));
            sha256.update(str.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BigInteger(sha256.digest());
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

    public boolean verifyChaumPedersonZKP(ECPoint G, BigInteger n, ECPoint gPowX, ECPoint gPowZ,
                                          ECPoint gPowZPowX, ECPoint gPowS, ECPoint gPowZPowS, BigInteger t, String signerID) {

        // ZKP: {A=g^s, B=(g^z)^s, t}
        BigInteger h = getSHA256(G, gPowX, gPowZ, gPowZPowX, gPowS, gPowZPowS, signerID);
        // check a) - omitted as it's been done in round 1
    	/*
       	if (gPowX.compareTo(BigInteger.ONE) == -1 ||
       			gPowX.compareTo(q.subtract(BigInteger.ONE)) == 1 ||
       			gPowX.modPow(q, p).compareTo(BigInteger.ONE) != 0) {
       		return false;
       	}
       	*/

        // Check b) - only partial; redundant checks not repeated. e.g., the order of g^z implied by ZKP checks in round 1
        if (gPowZ.normalize().getXCoord().toBigInteger().compareTo(BigInteger.ONE) == 0 &&
                gPowZ.normalize().getYCoord().toBigInteger().compareTo(BigInteger.ONE) == 0)
            return false;



        // Check c) - full check
        if (gPowZPowX.normalize().getXCoord().toBigInteger().compareTo(BigInteger.ZERO) == -1 ||
                gPowZPowX.normalize().getXCoord().toBigInteger().compareTo(q.subtract(BigInteger.ONE)) == 1 ||
                gPowZPowX.normalize().getYCoord().toBigInteger().compareTo(BigInteger.ZERO) == -1 ||
                gPowZPowX.normalize().getYCoord().toBigInteger().compareTo(q.subtract(BigInteger.ONE)) == 1) {
            return false;
        }

        // Check d) - Use the straightforward way with 2 exp. Using a simultaneous computation technique only needs 1 exp.
        // g^s = g^t (g^x)^h
        ECPoint gPowt = G.multiply(t).add(gPowX.multiply(h));
        if (!gPowt.equals(gPowS)) {
            return false;
        }

        // Check e) - Use the same method as in d)
        // (g^z)^s = (g^z)^t ((g^x)^z)^h
        if (!gPowZ.multiply(t).add(gPowZPowX.multiply(h)).equals(gPowZPowS)) {
            return false;
        }

        return true;
    }

    public boolean verifyZKP(ECPoint generator, ECPoint X, ECPoint V, BigInteger r, String userID) {

        /* ZKP: {V=G*v, r} */
        BigInteger h = getSHA256(generator, V, X, userID);

        // Public key validation based on p. 25
        // http://cs.ucsb.edu/~koc/ccs130h/notes/ecdsa-cert.pdf

        // 1. X != infinity
        if (X.isInfinity()){
            return false;
        }
        // 2. Check x and y coordinates are in Fq, i.e., x, y in [0, q-1]
        if (X.normalize().getXCoord().toBigInteger().compareTo(BigInteger.ZERO) == -1 ||
                X.normalize().getXCoord().toBigInteger().compareTo(q.subtract(BigInteger.ONE)) == 1 ||
                X.normalize().getYCoord().toBigInteger().compareTo(BigInteger.ZERO) == -1 ||
                X.normalize().getYCoord().toBigInteger().compareTo(q.subtract(BigInteger.ONE)) == 1) {
            return false;
        }

        // 3. Check X lies on the curve
        try {
            ecCurve.decodePoint(X.getEncoded(false));
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

        // 4. Check that nX = infinity.
        // It is equivalent - but more more efficient - to check the coFactor*X is not infinity
        if (X.multiply(coFactor).isInfinity()) {
            return false;
        }

        // Now check if V = G*r + X*h.
        // Given that {G, X} are valid points on curve, the equality implies that V is also a point on curve.
        ECPoint temp = generator.multiply(r).add(X.multiply(h.mod(nEC)));
        if (V.equals(temp)) {
            return true;
        }
        else {
            return false;
        }
    }


    public void exitWithError(String s){
        Log.d("ec", "Exit with ERROR: " + s);
        System.exit(0);
    }


}
