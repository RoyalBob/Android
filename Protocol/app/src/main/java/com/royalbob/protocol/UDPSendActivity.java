package com.royalbob.protocol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPSendActivity extends AppCompatActivity {

    private Button btn_send;
    private EditText edt_port, edt_msg, edt_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udpsend);

        getSupportActionBar().setTitle("UDP Send");

        findViewById();
        edt_msg.requestFocus();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int port = Integer.parseInt(edt_port.getText().toString());
                final String msg = edt_msg.getText().toString();
                final String address = edt_address.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendUDP(port, address, msg);
                    }
                }).start();

                Log.v("UDP Demo","发送了:" + msg + "\n到地址:" + address + ":" + port);
            }
        });
    }

    private void findViewById(){
        btn_send = (Button)findViewById(R.id.btn_send);
        edt_port = (EditText)findViewById(R.id.edt_port);
        edt_msg = (EditText)findViewById(R.id.edt_msg);
        edt_address = (EditText)findViewById(R.id.edt_address);
    }

    private void sendUDP(int port, String address, String msg){
        try {
            //创建一个DatagramSocket对象
            DatagramSocket socket = new  DatagramSocket ();
            //创建一个 InetAddress,相当于是地址
            InetAddress serverAddress = InetAddress.getByName(address);
            //转为byte类型
            byte data[] = msg.getBytes();
            //创建一个DatagramPacket 对象,并指定要讲这个数据包发送到网络当中的哪个地址,以及端口号
            //这个端口号是接收端端口
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length, serverAddress, port);
            //调用DatagramSocket对象的send方法 发送数据
            socket.send(datagramPacket);
            socket.close();

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_udpSend) {
            Toast.makeText(getApplicationContext(), "请勿切换至本页面！", Toast.LENGTH_LONG).show();
            return true;
        }else if (item.getItemId() == R.id.action_udpReceive){
            finish();
            Intent intent = new Intent(UDPSendActivity.this, UDPReceiveActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.action_tcpClient){
            finish();
            Intent intent = new Intent(UDPSendActivity.this, TCPClientActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.action_tcpServer){
            finish();
            Intent intent = new Intent(UDPSendActivity.this, TCPServerActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
