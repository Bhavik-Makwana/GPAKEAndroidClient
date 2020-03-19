package com.example.myapplication.JPAKEPlusEC;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import com.example.myapplication.JPAKEPlusEC.POJOs.ECRoundOne;
import com.example.myapplication.JPAKEPlusEC.POJOs.ECRoundOneResponse;
import com.example.myapplication.JPAKEPlusEC.POJOs.ECRoundThree;
import com.example.myapplication.JPAKEPlusEC.POJOs.ECRoundThreeResponse;
import com.example.myapplication.JPAKEPlusEC.POJOs.ECRoundTwo;
import com.example.myapplication.JPAKEPlusEC.POJOs.ECRoundTwoResponse;
import com.example.myapplication.RoundZero;
import com.google.gson.Gson;

import java.io.BufferedReader;

import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.Security;
import java.util.ArrayList;

public class JPAKEPlusEC extends AsyncTask<Button, Long, BigInteger>  {
    BufferedReader in;
    PrintWriter out;
    Gson gson = new Gson();
    String clientName;
    String data;
    String response;
    String sStr = "deadbeef";
    BigInteger s = getSHA256(sStr);
    int clientId;


    Button b, b2, b3;

    public JPAKEPlusEC(Button b, Button b2, Button b3) {
        this.b = b;
        this.b2 = b2;
        this.b3 = b3;
    }
    @Override
    protected void onPostExecute(BigInteger bs) {
        Log.d("speke", "DONE");
        b.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
    }

    @Override
    protected BigInteger doInBackground(Button... b) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            // Make connection and initialize streams
            String serverAddress = "192.168.1.137";
            Socket socket = new Socket(serverAddress, 8002);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                String line = in.readLine();
                Log.d("connect", " LINE: " + line);
                if (line.startsWith("SUBMITNAME")) {
                    clientName = "TEST";
                    out.println(clientName);
                } else if (line.startsWith("NAMEACCEPTED")) {
                    clientId = Integer.parseInt(in.readLine());
                    break;
                }
            }
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException e) {

                e.printStackTrace();
            }
            out.println(":EC");
            in.readLine();
            Log.d("ec", "eeee");
            return jpakePlusEC();
        }
        catch (IOException e) {
            return BigInteger.ONE;
        }
    }

    private BigInteger jpakePlusEC() {
        try {

            String json = in.readLine();
            RoundZero roundZero = gson.fromJson(json, RoundZero.class);
            ArrayList<Long> clients =  roundZero.getClientIDs();
            JPAKEPlusECNetwork jpake = new JPAKEPlusECNetwork("deadbeef", roundZero.getClientIDs().size(), Long.toString(clientId), clients, clientId);
            ECRoundOne roundOne = jpake.roundOne();
            data = gson.toJson(roundOne);
            System.out.println("Ggg");
            out.println(data);
            response = in.readLine();
            ECRoundOneResponse rOneResponse = gson.fromJson(response, ECRoundOneResponse.class);

            boolean passedRoundOne = jpake.verifyRoundOne(rOneResponse);
            if (!passedRoundOne) {
                out.println("0");
                System.exit(0);
            }
            // send confirmation to server
            out.println("1");
            // server can issue go ahead of next stage
            response = in.readLine();
            if (!response.equals("1")) {
                Log.d("EC", "All participants failed to verify Round 1");
                System.exit(0);
            }

            ECRoundTwo roundTwo = jpake.roundTwo(rOneResponse);
            // send serialized round two data to server
            out.println(gson.toJson(roundTwo));
            // get serialized json of all round 2 calculations
            response = in.readLine();
            ECRoundTwoResponse rTwoResponse = gson.fromJson(response, ECRoundTwoResponse.class);

            boolean passedRoundTwo = jpake.verifyRoundTwo(rTwoResponse);
            if (!passedRoundTwo) {
                System.out.println("FAILED");
                System.exit(0);
            }
            // send confirmation to server
            out.println("1");
            // server can issue go ahead of next stage
            response = in.readLine();
            if (!response.equals("1")) {
                Log.d("EC","All participants failed to verify Round 1");
                System.exit(0);
            }

            ECRoundThree roundThree = jpake.roundThree(rOneResponse, rTwoResponse);
            out.println(gson.toJson(roundThree));

            response = in.readLine();
            ECRoundThreeResponse rThreeResponse = gson.fromJson(response, ECRoundThreeResponse.class);

            boolean passedRoundThree = jpake.roundFour(rOneResponse, rTwoResponse, rThreeResponse);
            if (!passedRoundThree) {
                Log.d("EC", "All paricipants failed to verify round 3");
                System.exit(0);
            }

            // send confirmation to server
            out.println("1");
            // server can issue go ahead of next stage
            response = in.readLine();
            if (!response.equals("1")) {
                Log.d("EC","All participants failed to verify Round 1");
                System.exit(0);
            }


            BigInteger key = jpake.computeKey(rOneResponse, rThreeResponse);
            out.println("1");
            return key;
//            return BigInteger.ONE;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
}
