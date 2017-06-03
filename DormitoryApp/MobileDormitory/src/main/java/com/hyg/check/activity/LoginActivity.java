package com.hyg.check.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.hyg.activity.R;
import com.hyg.check.Utils.HttpUtil;
import com.hyg.check.app.AppApplication;
import com.hyg.check.config.ActionConfig;
import com.hyg.check.config.DataConfig;
import com.loopj.android.http.RequestParams;

import java.util.Map;

/**
 * Created by hyg on 2015/3/6.
 * 功能描述：用户登录界面
 */
public class LoginActivity extends Activity{
    private ActionProcessButton mActionButton;
    private EditText mUser;
    private EditText mPassword;
    private AppApplication mApplication;
    private RequestParams params;
    private Intent mIntent;
    private LinearLayout mLayout;
    private Map<String, Object> shardData;
    private int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initData();
        initView();
    }

    private void initData() {
        mApplication=(AppApplication)getApplication();
        shardData=mApplication.getSharedData();

    }

    /**
     * 添加过滤器并注册广播
     */
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filters=new IntentFilter();
        filters.addAction(ActionConfig.LOGIN_FAIL);
        filters.addAction(ActionConfig.LOGIN_SUCCESS);
        mApplication.registerReceiver(receiver,filters);
    }

    /**
     * 初始化View
     */
    private void initView(){
        mLayout=(LinearLayout)this.findViewById(R.id.ll_login);
        mUser = (EditText) this.findViewById(R.id.login_user);
        mPassword = (EditText) this.findViewById(R.id.login_password);
        mActionButton = (ActionProcessButton) this.findViewById(R.id.login);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //输入框不可输入
                mUser.setEnabled(false);
                mPassword.setEnabled(false);
                //判断输入是否为空
                if("".equals(mUser.getText().toString().trim())||"".equals(mPassword.getText().toString().trim())){
                    Toast.makeText(LoginActivity.this,R.string.login_user_null,Toast.LENGTH_SHORT).show();
                }else{
                    //发起网络请求登录
                    params=new RequestParams();
                    params.add("name",mUser.getText().toString());
                    params.add("pass",mPassword.getText().toString());
                    HttpUtil.doLogin(mApplication, params);
                    mActionButton.setProgress(50);
                }
            }
        });

        /**
         * 当点击空白处隐藏软键盘
         */
        mLayout.setOnTouchListener(new View.OnTouchListener()
        {

            public boolean onTouch(View arg0, MotionEvent arg1)
            {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        });
    }


    /**
     * 接收网络请求发出的广播
     */
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (ActionConfig.LOGIN_SUCCESS.equals(intent.getAction())){
                String mUserInfo=intent.getStringExtra(ActionConfig.LOGIN_SUCCESS);
                Log.i(getClass().getSimpleName(),mUserInfo);
                //请求成功跳转到MainActivity
                if (mUserInfo.split(",")[0].equals("Y")) {
                    //存储用户信息
                    saveUserInfo(mUserInfo);
                    Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show();
                    mActionButton.setProgress(100);
                    mIntent = new Intent(LoginActivity.this, ChoiceActivity.class);
                    startActivity(mIntent);
                    LoginActivity.this.finish();
                }else if(mUserInfo.split(",")[0].equals("Z")){
                    //存储用户信息
                    saveUserInfo(mUserInfo);
                    Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show();
                    mActionButton.setProgress(100);
                    mIntent = new Intent(LoginActivity.this, com.hyg.observe.ui.activity.MainActivity.class);
                    startActivity(mIntent);
                    LoginActivity.this.finish();

                }else {
                    mActionButton.setProgress(-1);
                    Toast.makeText(context, R.string.login_failure, Toast.LENGTH_SHORT).show();
                }
            }else if(ActionConfig.LOGIN_FAIL.equals(intent.getAction())){
                mActionButton.setProgress(-1);
                Toast.makeText(context,R.string.login_service_err,Toast.LENGTH_SHORT).show();
            }
            //输入框可输入
            mUser.setEnabled(true);
            mPassword.setEnabled(true);
        }
    };

    /**
     * 取消广播注册
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApplication.unregisterReceiver(receiver);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mActionButton.setProgress(0);
    }

    /**
     * 存储用户信息
     * @param userInfo 用户信息字符串
     */
    private void saveUserInfo(String userInfo){
        Map<String,Object> sharedData=mApplication.getSharedData();
        String[] user=userInfo.split(",");
        String[] dataConfigs={"", DataConfig.USER_NAME,DataConfig.COLLEGE_NO,DataConfig.USER_COLLEGE,DataConfig.USER_SEX,DataConfig.USER_CLASS,DataConfig.USER_DORMITORY};
        for(int i=0;i<dataConfigs.length;i++) {
            sharedData.put(dataConfigs[i], user[i]);
        }

    }


}
