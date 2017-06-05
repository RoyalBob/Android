package com.royalbob.selectbox;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
                    DisplayToast(arg0,"谢谢参与!您一共选择了" + n +"选");
                }
                else{
                    if(cb1.isChecked()){
                        DisplayToast(arg0,"您选择了:" + cb1.getText().toString());
                    }
                    if(cb2.isChecked()){
                        DisplayToast(arg0,"您选择了:" + cb2.getText().toString());
                    }
                    if(cb3.isChecked()){
                        DisplayToast(arg0,"您选择了:" + cb3.getText().toString());
                    }
                    if(cb4.isChecked()){
                        DisplayToast(arg0,"您选择了:" + cb4.getText().toString());
                    }
                }
            }
        });
    }

    public void DisplayToast(View view, String str)
    {
        Snackbar snackbar = Snackbar.make(view, str, Toast.LENGTH_SHORT);
        snackbar.show();
    }

/*    public void DisplayToast(String str)
    {
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.show();
    }*/
}
