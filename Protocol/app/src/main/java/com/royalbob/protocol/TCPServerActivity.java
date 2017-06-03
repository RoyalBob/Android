package com.royalbob.protocol;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServerActivity extends AppCompatActivity {

    private Button btn_connect = null;

    private EditText edt_port_tcpserver = null;
    private EditText edit_receive = null;
    private EditText edit_send = null;
    private Button btn_send = null;
    private boolean isConnected = false;

    private TextView tv_receiveData_tcpserver;
    private String resultData="", finalData = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpserver);

        getSupportActionBar().setTitle("TCP Server");
        btn_connect = (Button)findViewById(R.id.btn_connect);
        edt_port_tcpserver = (EditText)findViewById(R.id.edt_port_tcpserver);
        tv_receiveData_tcpserver = (TextView)findViewById(R.id.tv_receiveData_tcpserver);

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.obj = TCPServer(Integer.parseInt(edt_port_tcpserver.getText().toString()));
                        handler.sendMessage(msg);
                    }
                }).start();
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            finalData += msg.obj.toString() + "\n";
            tv_receiveData_tcpserver.setText(finalData);
        }
    };

    private String TCPServer(int port){
        try {
            ServerSocket server = new ServerSocket(port);
            //等待客户端连接
            Socket client = server.accept();
            InputStream input = client.getInputStream();
            OutputStream output = client.getOutputStream();

            //if(input!=null) {
                byte message[] = new byte[1024];
                int len = input.read(message);
                resultData = new String(message, 0, len);
                Log.v("TCP Server", "message from client:" + new String(message, 0, len));

                String sendString = "Server it is!";
                output.write(sendString.getBytes());
            //}
            client.close();
            server.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultData;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_udpSend) {
            finish();
            Intent intent = new Intent(TCPServerActivity.this, UDPSendActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.action_udpReceive){
            finish();
            Intent intent = new Intent(TCPServerActivity.this, UDPReceiveActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.action_tcpClient){
            finish();
            Intent intent = new Intent(TCPServerActivity.this, TCPClientActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.action_tcpServer){
            Toast.makeText(getApplicationContext(), "请勿切换至本页面！", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
