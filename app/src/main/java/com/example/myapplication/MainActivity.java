package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.JPAKEPlus.JPAKEPlus;
import com.example.myapplication.JPAKEPlusEC.JPAKEPlusEC;
import com.example.myapplication.SPEKEPlus.SPEKEPlus;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;


/**
 * Created by Girish Bhalerao on 5/4/2017.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTextViewReplyFromServer;
    private EditText mEditTextSendMessage;
    public BigInteger groupKey;
    public BigInteger pairKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("button", "print test 1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSend = (Button) findViewById(R.id.btn_send);
        Button buttonConnectJPAKEPlus = (Button) findViewById(R.id.btn_connect_jpake);
        Button buttonConnectSPEKEPlus = (Button) findViewById(R.id.btn_connect_speke);
        Button buttonConnectECJPAKEPlus = (Button) findViewById(R.id.btn_connect_EC);
        FloatingActionButton buttonSettings = (FloatingActionButton) findViewById(R.id.btn_settings);
        mEditTextSendMessage = (EditText) findViewById(R.id.edt_send_message);
        mTextViewReplyFromServer = (TextView) findViewById(R.id.tv_reply_from_server);

        buttonSend.setOnClickListener(this);
        buttonConnectJPAKEPlus.setOnClickListener(this);
        buttonConnectSPEKEPlus.setOnClickListener(this);
        buttonConnectECJPAKEPlus.setOnClickListener(this);
        buttonSettings.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d("button", "print test 2");
        Button ECBtn = (Button) findViewById(R.id.btn_connect_EC);
        Button jpakeBtn = (Button) findViewById(R.id.btn_connect_jpake);
        Button spekeBtn = (Button) findViewById(R.id.btn_connect_speke);

        switch (v.getId()) {

            case R.id.btn_send:
                Log.d("button", "send message");
                Log.d("button", mEditTextSendMessage.getText().toString());
                sendMessage(mEditTextSendMessage.getText().toString());
                break;
            case R.id.btn_connect_EC:
                Log.d("button", "connect button pressed");
                ECBtn.setEnabled(false);
                jpakeBtn.setEnabled(false);
                spekeBtn.setEnabled(false);
                new JPAKEPlusEC(ECBtn, jpakeBtn, spekeBtn).execute((Button) findViewById(R.id.btn_connect_EC));

                break;
            case R.id.btn_connect_jpake:
                Log.d("button", "connect button pressed");
                jpakeBtn.setEnabled(false);
                ECBtn.setEnabled(false);
                spekeBtn.setEnabled(false);
//                new JPAKEPlusEC((Button) findViewById(R.id.btn_connect)).execute((Button) findViewById(R.id.btn_connect));
                new JPAKEPlus(jpakeBtn, ECBtn, spekeBtn).execute((Button) findViewById(R.id.btn_connect_jpake));
                break;
            case R.id.btn_connect_speke:
                Log.d("button", "connect button pressed");
                spekeBtn.setEnabled(false);
                ECBtn.setEnabled(false);
                jpakeBtn.setEnabled(false);
//                new JPAKEPlusEC((Button) findViewById(R.id.btn_connect)).execute((Button) findViewById(R.id.btn_connect));
                new SPEKEPlus(spekeBtn, jpakeBtn, ECBtn).execute((Button) findViewById(R.id.btn_connect_speke));
                break;
            case R.id.btn_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);

        }
    }

    private void connect() {

    }

    private void sendMessage(final String msg) {

        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //Replace below IP with the IP of that device in which server socket open.
                    //If you change port then change the port number in the server side code also.
//                    Socket s = new Socket("192.168.1.98", 8002);
                    Socket s = new Socket("192.168.1.137", 8002);
                    OutputStream out = s.getOutputStream();

                    PrintWriter output = new PrintWriter(out);
                    Log.d("button", msg);
                    output.println(msg);
                    output.flush();
                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    final String st = input.readLine();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            String s = mTextViewReplyFromServer.getText().toString();
                            if (st.trim().length() != 0)
                                mTextViewReplyFromServer.setText(s + "\nFrom Server : " + st);
                        }
                    });

                    output.close();
                    out.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }




}