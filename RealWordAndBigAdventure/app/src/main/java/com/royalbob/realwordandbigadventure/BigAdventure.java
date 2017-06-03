package com.royalbob.realwordandbigadventure;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.*;

import java.io.IOException;

//进入后的大冒险界面
public class BigAdventure extends AppCompatActivity {

    private TextView bigadventure_tv;
    private ShakeChange shakeChange;
    private LinearLayout linearLayout;
    private ImageView topview,bottomview;
    private TextRead textRead;
    private RelativeLayout relativeLayout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bigadventure);

        bigadventure_tv = (TextView)findViewById(R.id.bigadventure_textview);
        topview = (ImageView)findViewById(R.id.bigadventure_topview);
        bottomview = (ImageView)findViewById(R.id.bigadventure_bottomview);
        relativeLayout = (RelativeLayout)findViewById(R.id.big_rl);
        shakeChange = new ShakeChange(BigAdventure.this,bigadventure_tv,2,topview,bottomview);
        textRead = new TextRead(BigAdventure.this,2);

        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_bottom);

        //设置触摸监听器，在触摸时调用
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                shakeChange.StartAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            bigadventure_tv.setText(textRead.LineRead());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },1000);
                return false;
            }
        });

    }

}