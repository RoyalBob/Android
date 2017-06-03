package com.royalbob.baymax;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BayMax imageball = new BayMax(this);
        RelativeLayout rly = (RelativeLayout)findViewById(R.id.mainLayout);
        rly.addView(imageball);
    }
}
