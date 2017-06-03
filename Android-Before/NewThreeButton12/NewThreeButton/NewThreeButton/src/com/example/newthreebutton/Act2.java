package com.example.newthreebutton;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Act2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act2, menu);
		return true;
	}

}
