package com.royalbob.qcellcore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText phoneSecEditText;
    private TextView resultView;
    private Button queryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneSecEditText = (EditText) findViewById(R.id.phone_sec);
        resultView = (TextView) findViewById(R.id.result_text);
        queryButton = (Button) findViewById(R.id.query_btn);

        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 手机号码（段）
                String phoneSec = phoneSecEditText.getText().toString().trim();
                // 简单判断用户输入的手机号码（段）是否合法
                if ("".equals(phoneSec) || phoneSec.length() < 7) {
                    // 给出错误提示
                    phoneSecEditText.setError("您输入的手机号码（段）有误！");
                    phoneSecEditText.requestFocus();
                    // 将显示查询结果的TextView清空
                    resultView.setText("");
                    return;
                }
                // 查询手机号码（段）信息
                getRemoteInfo(phoneSec);
            }
        });
    }

    /**
     * 手机号段归属地查询
     *
     * @param phoneSec 手机号段
     */
    public String getRemoteInfo(String phoneSec) {

        HttpURLConnection conn = null;
        InputStream is = null;
        String resultData = "";

        try {
            // 定义待请求的URL
            URL url = new URL("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo");
            // 创建HttpClient实例
            conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true); //允许输入流，即允许下载
            conn.setDoOutput(true); //允许输出流，即允许上传
            conn.setUseCaches(false); //不使用缓冲
            conn.setRequestMethod("GET"); //使用get请求
            is = conn.getInputStream();   //获取输入流，此时才真正建立链接
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine  = "";
            while((inputLine = bufferReader.readLine()) != null){
                resultData += inputLine + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                conn.disconnect();
            }
        }

        // 根据URL创建HttpPost实例
        HttpPost post = new HttpPost(requestUrl);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // 设置需要传递的参数
        params.add(new BasicNameValuePair("mobileCode", phoneSec));
        params.add(new BasicNameValuePair("userId", ""));
        try {
            // 设置URL编码
            post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            // 发送请求并获取反馈
            HttpResponse response = client.execute(post);

            // 判断请求是否成功处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 解析返回的内容
                String result = EntityUtils.toString(response.getEntity());
                // 将查询结果经过解析后显示在TextView中
                resultView.setText(filterHtml(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultData;
    }

    /**
     * 使用正则表达式过滤HTML标记
     *
     * @param source 待过滤内容
     * @return
     */
    private String filterHtml(String source) {
        if (null == source) {
            return "";
        }
        return source.replaceAll("</?[^>]+>", "").trim();
    }
}
