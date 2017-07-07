package com.royalbob.showtime;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView textview1;
    private Handler myHandler;
    private int year, month, day, hour, minute, second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview1 = (TextView) findViewById(R.id.textview1);
        MyThread myThread = new MyThread();
        myThread.start();
        myHandler = new Handler()
        {
            public void handleMessage(Message msg) {
                textview1.setText(String.valueOf(msg.obj));
            }
        };

    }

    class MyThread extends Thread
    {
        public void run() {
            while(true)
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Calendar date = Calendar.getInstance();
                year = date.get(Calendar.YEAR);
                month = date.get(Calendar.MONTH)+1;
                day = date.get(Calendar.DAY_OF_MONTH);
                hour = date.get(Calendar.HOUR_OF_DAY);
                minute = date.get(Calendar.MINUTE);
                second = date.get(Calendar.SECOND);
                Message msg = new Message();
                msg.obj = year+"年"+month+"月"+day+"日"+"\n"+hour+":"+minute+":"+second;
                myHandler.sendMessage(msg);
            }
        }
    }

}
