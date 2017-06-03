package com.hyg.check.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hyg.check.Utils.HttpUtil;
import com.hyg.check.Utils.JsonTools;
import com.hyg.activity.R;
import com.hyg.check.adapter.TimeAdapter;
import com.hyg.check.app.AppApplication;
import com.hyg.check.config.ActionConfig;
import com.hyg.check.config.DataConfig;
import com.loopj.android.http.RequestParams;

import java.util.List;
import java.util.Map;

/**
 * Created by hyg on 2015/3/21.
 * 功能描述：选择操作功能，成绩查询和寝室检查
 */
public class ChoiceActivity extends ActionBarActivity{
    private Toolbar mToolbar;
    private Button mGrade;
    private Button mCheck;
    private TextView mTitle;
    private Intent mIntent;
    private int position;
    private AppApplication application;
    private Map<String,Object> shardData;
    private JsonTools jsonTools;
    private List<Map<String,Object>> mTimeList;
    private TimeAdapter mTimeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        initData();
        initView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filters=new IntentFilter();
        filters.addAction(ActionConfig.Time_SUCCESS);
        filters.addAction(ActionConfig.Time_FAIL);
        application.registerReceiver(receiver,filters);
    }

    private void initData(){
        mIntent=new Intent();
        application=(AppApplication)getApplication();
        shardData=application.getSharedData();
        jsonTools=new JsonTools();
        mTimeAdapter=new TimeAdapter(this);
        RequestParams params=new RequestParams();
        params.put("code",shardData.get(DataConfig.COLLEGE_NO));
        //网络获取次数数据
        HttpUtil.getTimePermition(application, params);
    }

    private void initView() {
        initToolbar();
        mGrade=(Button)this.findViewById(R.id.choice_grade);
        mCheck=(Button)this.findViewById(R.id.choice_check);
        mGrade.setOnClickListener(new ChickedListener());
        mCheck.setOnClickListener(new ChickedListener());
    }


    private void initToolbar() {
        mToolbar=(Toolbar)this.findViewById(R.id.choice_toolbar);
        Toolbar.LayoutParams params=new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
        params.gravity= Gravity.CENTER;
        mTitle=new TextView(this);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        mTitle.setText(R.string.choice);
        mToolbar.addView(mTitle,params);
    }

    /**
     * 接收网络请求发出的广播
     */
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (ActionConfig.Time_SUCCESS.equals(intent.getAction())){
                String mTime=intent.getStringExtra(ActionConfig.Time_SUCCESS);
                Log.i(getClass().getSimpleName(),mTime);
                //解析返回的检查次数数据
                mTimeList=jsonTools.getTime(mTime);
            }else if(ActionConfig.Time_FAIL.equals(intent.getAction())){
                Toast.makeText(ChoiceActivity.this,"data error",Toast.LENGTH_SHORT).show();
            }

        }
    };
    private class ChickedListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.choice_grade:
                    Toast.makeText(ChoiceActivity.this,"功能未开发",Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent();
                    //intent.setClass(ChoiceActivity.this, com.hyg.observe.ui.activity.MainActivity.class);
                    //startActivity(intent);
                    break;
                case R.id.choice_check:
                    setCheckCount();
                    break;
            }

        }


        private void setCheckCount(){
            mTimeAdapter.setData(mTimeList);
            AlertDialog.Builder dialog=new AlertDialog.Builder(ChoiceActivity.this);
            dialog.setTitle(R.string.login_dialog_title);
            dialog.setSingleChoiceItems(mTimeAdapter,0,new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    position=which;
                    shardData.put("count",mTimeList.get(position).get("frequency"));
                    shardData.put("sex",mTimeList.get(position).get("sex"));
                    shardData.put("flag",mTimeList.get(position).get("flag"));
                    shardData.put("college",mTimeList.get(position).get("college"));
                    shardData.put("year",mTimeList.get(position).get("year"));
                    shardData.put("session",mTimeList.get(position).get("session"));
                    dialog.dismiss();
                    mIntent = new Intent(ChoiceActivity.this, MainActivity.class);
                    startActivity(mIntent);
                }
            });
            dialog.setNegativeButton(R.string.dialog_cancel,new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.create().show();
        }

    }
}
