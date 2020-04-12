package com.example.myapplication.JPAKEPlus;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import com.example.myapplication.JPAKEPlus.POJOs.RoundOne;
import com.example.myapplication.JPAKEPlus.POJOs.RoundOneResponse;
import com.example.myapplication.JPAKEPlus.POJOs.RoundThree;
import com.example.myapplication.JPAKEPlus.POJOs.RoundThreeResponse;
import com.example.myapplication.JPAKEPlus.POJOs.RoundTwo;
import com.example.myapplication.JPAKEPlus.POJOs.RoundTwoResponse;
import com.example.myapplication.RoundZero;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class JPAKEPlus extends AsyncTask<Button, Long, Long>  {
    BigInteger p = new BigInteger("C196BA05AC29E1F9C3C72D56DFFC6154A033F1477AC88EC37F09BE6C5BB95F51C296DD20D1A28A067CCC4D4316A4BD1DCA55ED1066D438C35AEBAABF57E7DAE428782A95ECA1C143DB701FD48533A3C18F0FE23557EA7AE619ECACC7E0B51652A8776D02A425567DED36EABD90CA33A1E8D988F0BBB92D02D1D20290113BB562CE1FC856EEB7CDD92D33EEA6F410859B179E7E789A8F75F645FAE2E136D252BFFAFF89528945C1ABE705A38DBC2D364AADE99BE0D0AAD82E5320121496DC65B3930E38047294FF877831A16D5228418DE8AB275D7D75651CEFED65F78AFC3EA7FE4D79B35F62A0402A1117599ADAC7B269A59F353CF450E6982D3B1702D9CA83", 16);
    BigInteger q = new BigInteger("90EAF4D1AF0708B1B612FF35E0A2997EB9E9D263C9CE659528945C0D", 16);
    BigInteger g = new BigInteger("A59A749A11242C58C894E9E5A91804E8FA0AC64B56288F8D47D51B1EDC4D65444FECA0111D78F35FC9FDD4CB1F1B79A3BA9CBEE83A3F811012503C8117F98E5048B089E387AF6949BF8784EBD9EF45876F2E6A5A495BE64B6E770409494B7FEE1DBB1E4B2BC2A53D4F893D418B7159592E4FFFDF6969E91D770DAEBD0B5CB14C00AD68EC7DC1E5745EA55C706C4A1C5C88964E34D09DEB753AD418C1AD0F4FDFD049A955E5D78491C0B7A2F1575A008CCD727AB376DB6E695515B05BD412F5B8C2F4C77EE10DA48ABD53F5DD498927EE7B692BBBCDA2FB23A516C5B4533D73980B2A3B60E384ED200AE21B40D273651AD6060C13D97FD69AA13C5611A51B9085", 16);

    BufferedReader in;
    PrintWriter out;
    Gson gson = new Gson();
    String clientName;
    String data;
    String response;
    String sStr = "deadbeef";
    BigInteger s = getSHA256(sStr);
    int clientId;
    TreeMap<String, Long> time = new TreeMap<>();

    Button b, b2, b3;
    public JPAKEPlus(Button b, Button b2, Button b3) {
        this.b = b;
        this.b2 = b2;
        this.b3 = b3;
    }

    @Override
    protected void onPostExecute(Long bs) {
        Log.d("jpake", "DONE");
        b.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
    }

    @Override
    protected Long doInBackground(Button... b) {
        try {
            Log.d("connect", "SERVER");
            String serverAddress = "192.168.1.137";
//            String serverAddress = "192.168.0.25";

            Socket socket = new Socket(serverAddress, 8002);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            Log.d("connect", "1");
            while (true) {


                String line = in.readLine();

                System.out.println(" LINE: " + line);
                Log.d("connect", " LINE: " + line);
                if (line.startsWith("SUBMITNAME")) {
                    clientName = "TEST";
                    out.println(clientName);
                } else if (line.startsWith("NAMEACCEPTED")) {
                    clientId = Integer.parseInt(in.readLine());
                    Log.d("JPAKE", "CLIENTID  "+clientId);
                    break;
                }
            }
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {

                e.printStackTrace();
            }
            out.println(":START");
            Log.d("JPAKEPairing", "eeee");
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            jpakePlus();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
//        displayLatency();
        return (long) 1;

    }

    private BigInteger jpakePlus() {
        try {

            String json = in.readLine();
            json = in.readLine();
            RoundZero roundZero = gson.fromJson(json, RoundZero.class);
            Log.d("JPAkE", "passed in id " + clientId);
            Log.d("JPAkE", Long.toString(clientId));
            JPAKEPlusNetwork jpake = new JPAKEPlusNetwork("deadbeef", p, q, g, Long.toString(clientId), roundZero.getClientIDs());
            jpake.modTest();
            RoundOne roundOne = jpake.roundOne();
            data = gson.toJson(roundOne);
            Log.d("JPAKE", "Attempting to send data");
            out.println(data);
            Log.d("JPAKE", "Data sent");
            response = in.readLine();
            RoundOneResponse rOneResponse = gson.fromJson(response, RoundOneResponse.class);

            boolean r1v = jpake.verifyRoundOne(rOneResponse);
            if (!r1v) {
                System.exit(0);
            }
            // send confirmation to server
            out.println("1");
            // server can issue go ahead of next stage
            response = in.readLine();
            if (!response.equals("1")) {
                Log.d("jpake", "All participants failed to verify Round 1");
                System.exit(0);
            }
            RoundTwo roundTwo = jpake.roundTwo(rOneResponse);

            out.println(gson.toJson(roundTwo));
            // get serialized json of all round 2 calculations
            response = in.readLine();
            RoundTwoResponse rTwoResponse= gson.fromJson(response, RoundTwoResponse.class);
            System.out.println(response);
            boolean r2v = jpake.verifyRoundTwo(rTwoResponse);
            if (!r2v) {
                Log.d("jpake", "FAILED");
                System.exit(0);
            }
            out.println("1");
            response = in.readLine();
            if (!response.equals("1")) {
                Log.d("jpake", "All participants failed to verify Round 1");
                System.exit(0);
            }
            RoundThree roundThree = jpake.roundThree(rOneResponse, rTwoResponse);

            out.println(gson.toJson(roundThree));
            response = in.readLine();
            RoundThreeResponse rThreeResponse = gson.fromJson(response, RoundThreeResponse.class);
            boolean r3v = jpake.verifyRoundThree(rOneResponse, rThreeResponse);
            if (!r3v) {
                Log.d("jpake", "FAILED");
                System.exit(0);
            }
            out.println("1");
            response = in.readLine();
            if (!response.equals("1")) {
                Log.d("jpake", "All participants failed to verify Round 1");
                System.exit(0);
            }

            BigInteger key = jpake.computeKey(rOneResponse, rThreeResponse);
            out.println("1");
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
