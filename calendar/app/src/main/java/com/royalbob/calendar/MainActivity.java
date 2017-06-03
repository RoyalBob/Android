package com.royalbob.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout rly = (RelativeLayout) findViewById(R.id.mainlayout);
        MyCalendar ca = new MyCalendar(getBaseContext());
        rly.addView(ca);
    }
}
