package com.example.myapplication.SPEKEPlus;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import com.example.myapplication.RoundZero;
import com.example.myapplication.SPEKEPlus.POJOs.SpekeRoundOne;
import com.example.myapplication.SPEKEPlus.POJOs.SpekeRoundOneResponse;
import com.example.myapplication.SPEKEPlus.POJOs.SpekeRoundTwo;
import com.example.myapplication.SPEKEPlus.POJOs.SpekeRoundTwoResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;

import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.Security;

public class SPEKEPlus extends AsyncTask<Button, Long, BigInteger>  {
    BigInteger p = new BigInteger("AC6BDB41324A9A9BF166DE5E1389582FAF72B6651987EE07FC3192943DB56050A37329CBB4A099ED8193E0757767A13DD52312AB4B03310DCD7F48A9DA04FD50E8083969EDB767B0CF6095179A163AB3661A05FBD5FAAAE82918A9962F0B93B855F97993EC975EEAA80D740ADBF4FF747359D041D5C33EA71D281E446B14773BCA97B43A23FB801676BD207A436C6481F1D2B9078717461A5B9D32E688F87748544523B524B0D57D5EA77A2775D2ECFA032CFBDBF52FB3786160279004E57AE6AF874E7303CE53299CCC041C7BC308D82A5698F3A8D0C38271AE35F8E9DBFBB694B5C803D89F7AE435DE236D525F54759B65E372FCD68EF20FA7111F9E4AFF73", 16);
    BigInteger q = p.subtract(BigInteger.ONE).divide(new BigInteger("2", 16));
    BigInteger g = new BigInteger("3", 16);

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

    public SPEKEPlus(Button b, Button b2, Button b3) {
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
            out.println(":SPEKE");
            in.readLine();
            Log.d("speke", "eeee");
            return spekePlus();
        }
        catch (IOException e) {
            return BigInteger.ONE;
        }
    }

    private BigInteger spekePlus() {
        try {
            String json = in.readLine();
            RoundZero roundZero = gson.fromJson(json, RoundZero.class);
            SPEKEPlusNetwork speke = new SPEKEPlusNetwork("deadbeef", p, q, g, roundZero.getClientIDs().size(), Long.toString(clientId));
            speke.modTest();
            SpekeRoundOne sRoundOne = speke.roundOne();
            data = gson.toJson(sRoundOne);
            out.println(data);
            response = in.readLine();
            SpekeRoundOneResponse rOneResponse = gson.fromJson(response, SpekeRoundOneResponse.class);

            boolean r1v = speke.verifyRoundOne(rOneResponse);
            if (!r1v) {
                System.exit(0);
            }
            // send confirmation to server
            out.println("1");
            // server can issue go ahead of next stage
            response = in.readLine();
            if (!response.equals("1")) {
                Log.d("speke", "All participants failed to verify Round 1");
                System.exit(0);
            }
            SpekeRoundTwo sRoundTwo = speke.roundTwo(rOneResponse);

            out.println(gson.toJson(sRoundTwo));
            // get serialized json of all round 2 calculations
            response = in.readLine();
            SpekeRoundTwoResponse rTwoResponse = gson.fromJson(response, SpekeRoundTwoResponse.class);
            System.out.println(response);
            boolean r2v = speke.verifyRoundTwo(rOneResponse, rTwoResponse);
            if (!r2v) {
                Log.d("speke", "FAILED");
                System.exit(0);
            }
            out.println("1");
            response = in.readLine();
            if (!response.equals("1")) {
                Log.d("speke", "All participants failed to verify Round 1");
                System.exit(0);
            }
            BigInteger key = speke.computeKeys(rOneResponse, rTwoResponse);

            return key;
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
