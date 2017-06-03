package com.royalbob.settitle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button btn1=(Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent (FirstActivity.this,SecondActivity.class);

                EditText edtName = (EditText) findViewById(R.id.edtName);
                intent.putExtra("Name", edtName.getText().toString());

                startActivity(intent);

                overridePendingTransition(R.anim.enter,R.anim.exit );
            }
        });

    }

}
