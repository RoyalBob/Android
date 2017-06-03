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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClientActivity extends AppCompatActivity {

    private EditText edt_port_tcpsend, edt_address_tcpsend, edt_msg_tcpsend;
    private Button btn_send_tcpsend;
    private TextView tv_receiveData_tcpsend;
    private String result = "", receiveData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpclient);

        getSupportActionBar().setTitle("TCP Client");
        findViewById();
        tv_receiveData_tcpsend = (TextView)findViewById(R.id.tv_receiveData_tcpsend);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                result += msg.obj.toString() + "\n";
                tv_receiveData_tcpsend.setText(result);
                super.handleMessage(msg);
            }
        };

        btn_send_tcpsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.obj = TCPClient(edt_address_tcpsend.getText().toString(), Integer.parseInt(edt_port_tcpsend.getText().toString()), edt_msg_tcpsend.getText().toString());
                        handler.sendMessage(msg);
                    }
                }).start();
            }
        });


    }

    private void findViewById(){
        btn_send_tcpsend = (Button)findViewById(R.id.btn_send_tcpsend);
        edt_port_tcpsend = (EditText)findViewById(R.id.edt_port_tcpsend);
        edt_msg_tcpsend = (EditText)findViewById(R.id.edt_msg_tcpsend);
        edt_address_tcpsend = (EditText)findViewById(R.id.edt_address_tcpsend);
    }

    private String TCPClient(String address, int port, String msg) {
        try {
            Socket client = new Socket(address, port);
            InputStream input = client.getInputStream();
            OutputStream output = client.getOutputStream();
            byte message[] = new byte[1024];
            int len = input.read(message);
            receiveData = new String(message,0,len);
            Log.v("TCP SERVER", "message from server:" + receiveData);

            //String sendString = "Client it is!";
            output.write(msg.getBytes());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveData;
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
            Intent intent = new Intent(TCPClientActivity.this, UDPSendActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.action_udpReceive){
            finish();
            Intent intent = new Intent(TCPClientActivity.this, UDPReceiveActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.action_tcpClient){
            Toast.makeText(getApplicationContext(), "请勿切换至本页面！", Toast.LENGTH_LONG).show();
            return true;
        }else if (item.getItemId() == R.id.action_tcpServer){
            finish();
            Intent intent = new Intent(TCPClientActivity.this, TCPServerActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
