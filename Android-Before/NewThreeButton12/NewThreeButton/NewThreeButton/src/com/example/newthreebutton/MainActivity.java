package com.example.newthreebutton;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		NewThreeButton myButton = (NewThreeButton) findViewById(R.id.mainbutton);
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
