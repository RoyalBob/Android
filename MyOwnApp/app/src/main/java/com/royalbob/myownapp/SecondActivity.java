package com.royalbob.myownapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String Name = bundle.getString("Name"); 
		
		SecondActivity.this.setTitle("Welcome:"+Name);

		Button btn2 =(Button) findViewById(R.id.button2);
		btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SecondActivity.this.finish();
			}
		});
		
	}
	
}
