package com.example.app1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.style.BackgroundColorSpan;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FirstActivity extends Activity {
	
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    	
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
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
}
