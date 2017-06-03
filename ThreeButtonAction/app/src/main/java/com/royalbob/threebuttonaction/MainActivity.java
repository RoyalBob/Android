package com.royalbob.threebuttonaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ThreeButton myButton = (ThreeButton) findViewById(R.id.mainbutton);
        myButton.setButtonText("Android", "IOS", "WP8");
        myButton.setLeftButtonListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Act1.class);
                startActivity(intent);
            }
        });
        myButton.setMiddleButtonListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Act2.class);
                startActivity(intent);
            }
        });
        myButton.setRightButtonListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Act3.class);
                startActivity(intent);
            }
        });
    }
}
