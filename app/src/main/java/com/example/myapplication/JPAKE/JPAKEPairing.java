//package com.example.myapplication.SPEKEPlus;
//
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.Button;
//
//import com.example.myapplication.AESEncryption;
//import com.example.myapplication.JPAKE.RoundOne;
//import com.example.myapplication.JPAKE.RoundThree;
//import com.example.myapplication.JPAKE.RoundTwo;
//import com.example.myapplication.RoundZero;
//import com.google.gson.Gson;
//
//import org.bouncycastle.*;
//import org.bouncycastle.crypto.CryptoException;
//import org.bouncycastle.crypto.agreement.jpake.JPAKEParticipant;
//import org.bouncycastle.crypto.agreement.jpake.JPAKEPrimeOrderGroup;
//import org.bouncycastle.crypto.agreement.jpake.JPAKEPrimeOrderGroups;
//import org.bouncycastle.crypto.agreement.jpake.JPAKERound1Payload;
//import org.bouncycastle.crypto.agreement.jpake.JPAKERound2Payload;
//import org.bouncycastle.crypto.agreement.jpake.JPAKERound3Payload;
//
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.math.BigInteger;
//import java.net.Socket;
//import java.security.MessageDigest;
//import java.security.Security;
//import java.util.ArrayList;
//
//public class JPAKEPairing extends AsyncTask<Button, Long, BigInteger> {
//    BufferedReader in;
//    PrintWriter out;
//    Gson gson = new Gson();
//    String clientName;
//    String data;
//    String response;
//    String sStr = "twopair";
//    BigInteger s = getSHA256(sStr);
//    int clientId;
//
//
//    Button b, b2, b3;
//
//    public JPAKEPairing(Button b, Button b2, Button b3) {
//        this.b = b;
//        this.b2 = b2;
//        this.b3 = b3;
//    }
//    @Override
//    protected void onPostExecute(BigInteger bs) {
//        Log.d("speke", "DONE");
//        b.setEnabled(true);
//        b2.setEnabled(true);
//        b3.setEnabled(true);
//    }
//
//    @Override
//    protected BigInteger doInBackground(Button... b) {
//        try {
//            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//            // Make connection and initialize streams
//            String serverAddress = "192.168.1.137";
//            Socket socket = new Socket(serverAddress, 8002);
//            in = new BufferedReader(new InputStreamReader(
//                    socket.getInputStream()));
//            out = new PrintWriter(socket.getOutputStream(), true);
//            while (true) {
//                String line = in.readLine();
//                Log.d("connect", " LINE: " + line);
//                if (line.startsWith("SUBMITNAME")) {
//                    clientName = "TEST";
//                    out.println(clientName);
//                } else if (line.startsWith("NAMEACCEPTED")) {
//                    clientId = Integer.parseInt(in.readLine());
//                    break;
//                }
//            }
//            try {
//                Thread.sleep(3000);
//            }
//            catch (InterruptedException e) {
//
//                e.printStackTrace();
//            }
//            out.println(":PAIR");
//            in.readLine();
//            Log.d("pair", "eeee");
//            return jpake();
//        }
//        catch (IOException e) {
//            return BigInteger.ONE;
//        }
//    }
//
//    private BigInteger jpake(BigInteger groupKey) {
//        System.out.println("hello");
//        try {
//            String json = in.readLine();
//            System.out.println("*************************** ROUND 0 ***************************");
//            RoundZero roundZero = gson.fromJson(json, RoundZero.class);
//            ArrayList<Long> clients =  roundZero.getClientIDs();
//
//            String s = "twopair";
//            JPAKEPrimeOrderGroup group = JPAKEPrimeOrderGroups.NIST_2048;
//
//            JPAKEParticipant client = new JPAKEParticipant(Integer.toString(clientId), s.toCharArray(), group);
//            /* Step 1: Alice sends g^{x1}, g^{x2}, and Bob sends g^{x3}, g^{x4} */
//            System.out.println("*************************** ROUND 1 ***************************");
//            JPAKERound1Payload round1 = client.createRound1PayloadToSend();
//
//            System.out.println("************************* SEND ROUND 1 ************************");
//            data = gson.toJson(round1);
//            out.println(data);
//            System.out.println("************************* RECI ROUND 1 ************************");
//            response = in.readLine();
//            RoundOne round1Response = gson.fromJson(response, RoundOne.class);
//            Long partnerID = 0l;
//            for (Long key : round1Response.getJpakeRoundOne().keySet()) {
//                if (key != (long) clientId) {
//                    partnerID = key;
//                }
//            }
//            System.out.println("*************************** ROUND 1V **************************");
//            /* Alice verifies Bob's ZKPs and also check g^{x4} != 1*/
//            try {
//                client.validateRound1PayloadReceived(round1Response.getJpakeRoundOne().get(partnerID));
//            } catch (CryptoException e) {
//                e.printStackTrace();
//                System.out.println("Invalid round 1 payload received. Exit.");
//                System.exit(0);
//            }
//            out.println("1");
//            response = in.readLine();
//            if (!response.equals("1")) {
//                System.out.println("All participants failed to verify Round 1");
//            }
//            System.out.println("*************************** ROUND 2 ***************************");
//            /* Step 2: Alice sends A and Bob sends B */
//            JPAKERound2Payload round2 = client.createRound2PayloadToSend();
//            data = gson.toJson(round2);
//            out.println(data);
//            response = in.readLine();
//            RoundTwo round2Response = gson.fromJson(response, RoundTwo.class);
//            /* Alice verifies Bob's ZKP in step 2*/
//            System.out.println("*************************** ROUND 2V **************************");
//            try {
//                client.validateRound2PayloadReceived(round2Response.getJpakeRoundTwo().get(partnerID));
//            } catch (CryptoException e) {
//                e.printStackTrace();
//                System.out.println("Invalid round 2 payload received. Exit.");
//                System.exit(0);
//            }
//
//            out.println("1");
//            response = in.readLine();
//            if (!response.equals("1")) {
//                Log.d("pair", "All participants failed to verify Round 1");
//            }
//
//            /* After step 2, compute the common key material */
//            BigInteger keyingMaterial = client.calculateKeyingMaterial();
////        BigInteger bobKeyingMaterial = bob.calculateKeyingMaterial();
//
//            System.out.println("*************************** ROUND 3 ***************************");
//            /* Step 3 (optional): Explicit key confirmation */
//            JPAKERound3Payload round3 = client.createRound3PayloadToSend(keyingMaterial);
////        JPAKERound3Payload bobRound3 = bob.createRound3PayloadToSend(bobKeyingMaterial);
//            data = gson.toJson(round3);
//            out.println(data);
//            response = in.readLine();
//            RoundThree round3Response = gson.fromJson(response, RoundThree.class);
//            System.out.println("*************************** ROUND 3V ***************************");
//            try {
//                client.validateRound3PayloadReceived(round3Response.getJpakeRoundThree().get(partnerID), keyingMaterial);
//            } catch (CryptoException e) {
//                e.printStackTrace();
//                System.out.println("Key confirmation failed. Exit.");
//                System.exit(0);
//            }
//            out.println("1"); // OK
//
//            response = in.readLine();
//
//            if (response.equals("1")) {
//                out.println("1");
//            }
//            response = in.readLine();
//
//            AESEncryption aes = new AESEncryption();
//            byte[] k = new byte[32];
//            for(int i = 0; i< 32; i++)
//                k[i]= keyingMaterial.toByteArray()[i];
//
//            aes.createKey(k);
//            if (response.equals("1")) {
//
//                String encryptedString = aes.encrypt(groupKey.toString()) ;
//                out.println(encryptedString);
//
//            }
//            else {
//                System.out.println("Receiving key");
////                response = in.readLine();
//                String akey = aes.decrypt(response);
//
//                groupKey = new BigInteger(akey);
//                System.out.println("Group key " + groupKey.toString(16));
//            }
//            return getSHA256(keyingMaterial);
//        }
//        catch(IOException e) {
//            e.printStackTrace();
//        }
//        return BigInteger.ZERO;
//
//    }
//
//    public BigInteger getSHA256(BigInteger K) {
//
//        java.security.MessageDigest sha = null;
//
//        try {
//            sha = java.security.MessageDigest.getInstance("SHA-256");
//            sha.update(K.toByteArray());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return new BigInteger(1, sha.digest()); // 1 for positive int
//    }
//}
