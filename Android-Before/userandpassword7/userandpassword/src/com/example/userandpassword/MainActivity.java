package com.example.userandpassword;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MainActivity extends Activity {
	private EditText edtUserName, edtPassword;
	private CheckBox cbRemember;
	private Button btnLogin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		edtUserName = (EditText) findViewById(R.id.edtUserName);
		edtPassword = (EditText) findViewById(R.id.edtPassword);
		cbRemember = (CheckBox) findViewById(R.id.cbRemember);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		final SharedPreferences share = getSharedPreferences("mylog", Activity.MODE_PRIVATE);
		if(share.getString("Remember", "false").toString().toUpperCase().equals("TRUE"))
		{
			edtUserName.setText(share.getString("UserName", share.getString("UserName", "").toString()));
			edtPassword.setText(share.getString("Password", share.getString("Password", "").toString()));
			cbRemember.setChecked(true);
		}
		btnLogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Editor edit = share.edit();
				
				if(edtUserName.getText().toString().equals("admin") && edtPassword.getText().toString().equals("123456"))
				{
					Toast.makeText(MainActivity.this, "µÇÂ½³É¹¦", Toast.LENGTH_SHORT).show();
					if(cbRemember.isChecked())
					{
						edit.putString("UserName", edtUserName.getText().toString());
						edit.putString("Password", edtPassword.getText().toString());
						edit.putString("Remember", "True");
					}
					else
					{
						edit.putString("UserName", "");
						edit.putString("Password", "");
						edit.putString("Remember", "False");
					}
					edit.commit();
				}
				else
				{
					Toast.makeText(MainActivity.this, "µÇÂ½Ê§°Ü!ÓÃ»§Ãû»òÃÜÂë´íÎó!", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
