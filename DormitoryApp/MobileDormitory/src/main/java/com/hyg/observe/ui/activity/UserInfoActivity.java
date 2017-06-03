package com.hyg.observe.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hyg.activity.R;
import com.hyg.check.app.AppApplication;
import com.hyg.check.config.DataConfig;
import com.hyg.observe.adapter.UserInfoAdapter;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by hyg on 2015/4/28.
 */
public class UserInfoActivity extends ActionBarActivity{
    private Toolbar mToolbar;
    private ListView mListView;
    private UserInfoAdapter mAdapter;
    private TextView mTitle;
    private String[] userItem;
    private Map<String,Object> map;
    private AppApplication appApplication;
    private Map<String,Object> sharedData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initData();
        initView();
    }


    private void initData() {
        appApplication=(AppApplication)getApplication();
        sharedData=appApplication.getSharedData();
        userItem=getResources().getStringArray(R.array.user_item);
        map=new HashMap<String,Object>();
        map.put(userItem[0],sharedData.get(DataConfig.USER_NAME));
        map.put(userItem[1],sharedData.get(DataConfig.USER_COLLEGE));
        map.put(userItem[2],sharedData.get(DataConfig.USER_CLASS));
        map.put(userItem[3],sharedData.get(DataConfig.USER_DORMITORY));
    }

    private void initView() {
        initToolbar();
        initListView();

    }

    private void initListView() {
        mListView=(ListView)this.findViewById(R.id.lv_user);
        mAdapter=new UserInfoAdapter(this);
        mAdapter.setData(map,userItem);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new ItemClicked());
    }

    private void initToolbar() {
        mToolbar=(Toolbar)this.findViewById(R.id.toolbar);

        mTitle=new TextView(this);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        mTitle.setText(R.string.toolbar_title_userInfo);
        Toolbar.LayoutParams params=new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT,Toolbar.LayoutParams.WRAP_CONTENT);
        params.gravity= Gravity.CENTER;
        mToolbar.addView(mTitle,params);
        //设置回退键
        mToolbar.setNavigationIcon(R.drawable.header_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationClicked();
            }
        });

    }

    private class ItemClicked implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }

    private void navigationClicked(){
        this.finish();
    }



}
