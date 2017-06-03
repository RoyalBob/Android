package com.example.selectbox;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView t1 = (TextView) findViewById(R.id.textView1);
		t1.setText("调查:你喜欢Android的原因?");
		Button b1 = (Button) findViewById(R.id.button1);
		final CheckBox cb1 = (CheckBox) findViewById(R.id.checkBox1);
		final CheckBox cb2 = (CheckBox) findViewById(R.id.checkBox2);
		final CheckBox cb3 = (CheckBox) findViewById(R.id.checkBox3);
		final CheckBox cb4 = (CheckBox) findViewById(R.id.checkBox4);
		b1.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int n=0;
				if(cb1.isChecked()){
					n++;
				}
				if(cb2.isChecked()){
					n++;
				}
				if(cb3.isChecked()){
					n++;
				}
				if(cb4.isChecked()){
					n++;
				}
				if(n>=2){
					DisplayToast("谢谢参与!您一共选择了" + n +"选");
				}
				else{
					if(cb1.isChecked()){
						DisplayToast("您选择了:" + cb1.getText().toString());
					}
					if(cb2.isChecked()){
						DisplayToast("您选择了:" + cb2.getText().toString());
					}
					if(cb3.isChecked()){
						DisplayToast("您选择了:" + cb3.getText().toString());
					}
					if(cb4.isChecked()){
						DisplayToast("您选择了:" + cb4.getText().toString());
					}
				}
			}
		});
		
	}
	public void DisplayToast(String str)
	{
		Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
		toast.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
