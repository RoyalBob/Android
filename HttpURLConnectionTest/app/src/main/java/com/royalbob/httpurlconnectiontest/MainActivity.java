package com.royalbob.httpurlconnectiontest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    Button visitWebBtn = null;
    TextView showTextView = null;
//    String urlString = "http://life.tenpay.com/cgi-bin/mobile/MobileQueryAttribution.cgi?";
//    String param = "chgmobile=13291921791";
    String urlString = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?";
    String param = "tel=13291921791";
    String str = "";

    private Handler handler = new Handler(){

        public void handleMessage(Message msg) {
            String text = (String) msg.obj;
            text = text.substring(19);
            try {
                JSONObject jsonObject =new JSONObject(text);
                str="省份:\t"+jsonObject.getString("province") + "\n" + "运营商:\t" + jsonObject.getString("catName");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            showTextView.setText(str);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showTextView = (TextView)findViewById(R.id.textview_show);
        visitWebBtn = (Button)findViewById(R.id.btn_visit_web);
        visitWebBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(urlString); //URL对象
                            HttpURLConnection conn = (HttpURLConnection)url.openConnection(); //使用URL打开一个链接
                            conn.setRequestMethod("POST"); //使用post请求
                            conn.setReadTimeout(5000);
                            conn.setConnectTimeout(5000);

                            conn.setDoInput(true); //向连接中写入数据，允许输入流，即允许下载
                            conn.setDoOutput(true); //向连接中读取数据，允许输出流，即允许上传
                            conn.setUseCaches(false); //禁止缓存
                            conn.setInstanceFollowRedirects(true);//自动执行HTTP重定向
//                            conn.setRequestProperty("Content-Type", "application/soap+xml");//设置内容类型
                            conn.setRequestProperty("Charset", "UTF-8");

                            // 建立输出流，并写入数据
                            OutputStream outputStream = conn.getOutputStream();
                            outputStream.write(param.getBytes("UTF-8"));

                            conn.connect();
                            //outputStream.close();
                            // 判断是否响应成功
                            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {

                                InputStreamReader isr = new InputStreamReader(conn.getInputStream(),"gb2312");
                                BufferedReader responseReader = new BufferedReader(isr);
                                String resultData = "";
                                String readLine = null;
                                while ((readLine = responseReader.readLine()) != null) {
                                    resultData += readLine ;
                                }

                                Message msg = new Message();
                                msg.obj = resultData;
                                handler.sendMessage(msg);
                                responseReader.close();
                            }

                            conn.disconnect();

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
