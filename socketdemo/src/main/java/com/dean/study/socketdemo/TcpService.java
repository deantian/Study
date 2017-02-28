package com.dean.study.socketdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by TianWei on 2017/2/28.
 */

public class TcpService extends Service {
    private boolean isServiceDestroyed = false;
    private String[] mDefinedMessages = new String[]{"你好", "你的名字", "天气很好", "你知道吗"};


    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new TcpServiceRunnable()).start();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isServiceDestroyed = true;
    }

    private class TcpServiceRunnable implements Runnable {
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            while (!isServiceDestroyed) {
                try {
                    final Socket client = serverSocket.accept();
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            responseClient(client);
                        }
                    };
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void responseClient(Socket client) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())));
            out.println("欢迎来到聊天室");
            while (!isServiceDestroyed) {
                String str = in.readLine();
                if (str == null) {
                    //客户端断开连接
                }
                int i = new Random().nextInt(mDefinedMessages.length);
                String msg = mDefinedMessages[i];
                out.println(msg);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
