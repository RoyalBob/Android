package com.royalbob.userandpassword;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText edtUserName, edtPassword;
    private CheckBox cbRemember, cbShow;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        cbShow = (CheckBox) findViewById(R.id.cbShow);
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
                SharedPreferences.Editor edit = share.edit();

                if(edtUserName.getText().toString().equals("admin") && edtPassword.getText().toString().equals("123456"))
                {
                    Snackbar.make(arg0, "登陆成功", Snackbar.LENGTH_SHORT).show();
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
                    Snackbar.make(arg0, "登陆失败!用户名或密码错误!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        cbShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //如果选中，显示密码
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

    }


}
