package com.royalbob.tcpclient;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private EditText edt_port;
    private Button btn_receive;
    private TextView tv_receiveData;
    private String result = "", receiveData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_port = (EditText)findViewById(R.id.edt_port);
        btn_receive = (Button)findViewById(R.id.btn_receive);
        tv_receiveData = (TextView)findViewById(R.id.tv_receiveData);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                result += msg.obj.toString() + "\n";
                tv_receiveData.setText(result);
                super.handleMessage(msg);
            }
        };

        btn_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.obj = TCPClient(Integer.parseInt(edt_port.getText().toString()));
                        handler.sendMessage(msg);
                    }
                }).start();
            }
        });


    }

    private String TCPClient(int port) {
        try {
            Socket client = new Socket("192.168.1.130", port);
            InputStream input = client.getInputStream();
            OutputStream output = client.getOutputStream();
            byte message[] = new byte[1024];
            int len = input.read(message);
            receiveData = new String(message,0,len);
            Log.v("TCP SERVER", "message from server:" + receiveData);

            String sendString = "Client it is!";
            output.write(sendString.getBytes());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveData;
    }
}
