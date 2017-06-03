package com.royalbob.tcpserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button btn_send;
    private EditText edt_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_send = (Button)findViewById(R.id.btn_send);
        edt_msg = (EditText)findViewById(R.id.edt_msg);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TCPServer();
                    }
                });
            }
        });
    }

    private void TCPServer(){
        try {
            ServerSocket server = new ServerSocket(8888);
            //等待客户端连接
            Socket client = server.accept();
            InputStream input = client.getInputStream();
            OutputStream output = client.getOutputStream();

            byte message[] = new byte[1024];
            int len = input.read(message);
            Log.v("TCP Server","message from client:" + new String(message,0,len));

            String sendString = "Server it is!";
            output.write(sendString.getBytes());
            client.close();
            server.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
