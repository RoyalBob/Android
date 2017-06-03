package com.royalbob.setringtong;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button mRingtongButton;

    //定义类型
    private static final int RingtongButton=0;

    //铃声文件夹
    private String strRingtongFolder="/sdcard/myHead";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRingtongButton=(Button)findViewById(R.id.myRingtongButton);
        mRingtongButton.setOnClickListener(new myRingtongButtonListener());
    }

    //设置来电铃声监听器
    private class myRingtongButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(isFolder(strRingtongFolder)){
                //打开系统铃声设置
                Intent intent=new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI, true);
                //类型为来电ringtong
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
                //设置显示的题目
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置来电的铃声");
                //当设置完成之后返回到当前的activity
                startActivityForResult(intent, RingtongButton);
            }
        }
    }

    //检查是否存在指定的文件夹，如果不存在就创建
    private boolean isFolder(String strFolder){
        boolean tmp = false;
        File f1 = new File(strFolder);
        if (!f1.exists())
        {
            if (f1.mkdirs())
            {
                tmp = true;
            }
            else
            {
                tmp = false;
            }
        }
        else
        {
            tmp = true;
        }
        return tmp;
    }
    //设置铃声之后的回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=RESULT_OK){
            return;
        }
        switch(requestCode){
            case RingtongButton:
                try {
                    //得到我们选择的铃声
                    Uri pickedUri=data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    //将我们选择的铃声选择成默认
                    if(pickedUri!=null){
                        RingtoneManager.setActualDefaultRingtoneUri(MainActivity.this, RingtoneManager.TYPE_RINGTONE, pickedUri);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
