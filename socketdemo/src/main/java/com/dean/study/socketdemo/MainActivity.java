package com.dean.study.socketdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private static final int SOCKET_CONNECTED = 0;
    private static final int RECIVE_NEW_MASSAGE = 1;
    private TextView mMessageTv;
    private Button mSendBtn;
    private EditText mTextEt;
    private Socket mSocket;
    private PrintWriter mPrintWriter;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessageTv = (TextView) findViewById(R.id.message_tv);
        mSendBtn = (Button) findViewById(R.id.send_btn);
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mTextEt = (EditText) findViewById(R.id.msg_et);
        Intent intent = new Intent(this, TcpService.class);
        startService(intent);
        new Thread() {
            @Override
            public void run() {
                super.run();
                connectTcpService();
            }
        };
    }

    private void connectTcpService() {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 8688);
            mSocket = socket;
            mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            mHandler.sendEmptyMessage(SOCKET_CONNECTED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!this.isFinishing()) {
                String msg = br.readLine();
                if (msg != null) {
                    mHandler.obtainMessage(RECIVE_NEW_MASSAGE, "server" + msg);
                }
            }
            mPrintWriter.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
