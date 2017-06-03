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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * Created by RoyalBob on 2016/7/25.
 */
public class UDPReceiveActivity extends AppCompatActivity {

    private EditText edt_port;
    private Button btn_receive;
    private TextView tv_receiveData;
    private String result = "", receiveData = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udpreceive);

        getSupportActionBar().setTitle("UDP Receive");

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
                        msg.obj = ReceiveUDP(Integer.parseInt(edt_port.getText().toString()));
                        handler.sendMessage(msg);
                    }
                }).start();
            }
        });
    }


    private String ReceiveUDP(int port){
        byte data[] = new byte[1024];   //创建一个byte数组用于接收
        try {
            //创建一个DatagramSocket对象，并指定监听的端口号,绑定到本地地址和本地的9000端口
            //如果不传参数：绑定到本地端口和本地可用端口中的随机端口
            DatagramSocket socket = new  DatagramSocket (null);
            socket.setReuseAddress(true);
            socket.setBroadcast(true);
            socket.bind(new InetSocketAddress(port));
            DatagramPacket datagramPacket = new DatagramPacket(data , data.length);
            Log.v("UDP Receive Demo","准备接收!");
            socket.receive(datagramPacket);
            receiveData = new String(datagramPacket.getData()).trim();
            Log.v("UDP Receive Demo","接收到的数据是:" + receiveData + "\n在端口:" + port);
            socket.close();

        } catch (SocketException e) {
            Log.v("UDP Receive Demo","SocketException" +  e);
        } catch (IOException e) {
            Log.v("UDP Receive Demo","IOException" +  e);
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
            Intent intent = new Intent(UDPReceiveActivity.this, UDPSendActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.action_udpReceive){
            Toast.makeText(getApplicationContext(), "请勿切换至本页面！", Toast.LENGTH_LONG).show();
            return true;
        }else if (item.getItemId() == R.id.action_tcpClient){
            finish();
            Intent intent = new Intent(UDPReceiveActivity.this, TCPClientActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.action_tcpServer){
            finish();
            Intent intent = new Intent(UDPReceiveActivity.this, TCPServerActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}