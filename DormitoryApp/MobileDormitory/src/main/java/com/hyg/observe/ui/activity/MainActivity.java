package com.hyg.observe.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyg.activity.R;
import com.hyg.check.Utils.JsonTools;
import com.hyg.check.activity.LoginActivity;
import com.hyg.check.app.AppApplication;
import com.hyg.observe.adapter.DrawerAdapter;
import com.hyg.observe.adapter.ListViewAdapter;
import com.hyg.observe.config.Config;
import com.hyg.observe.db.Dao;
import com.hyg.observe.utils.HttpUtil;
import com.hyg.observe.utils.SharedPreferenceUtil;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;

import java.util.List;
import java.util.Map;

import me.drakeet.materialdialog.MaterialDialog;


public class MainActivity extends ActionBarActivity {
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private DrawerArrowDrawable drawerArrow;
    private Toolbar mToolbar;
    private LinearLayout mLayout;
    private SwipeRefreshLayout mRefresh;
    private ListView mListView;
    private ListView mDrawerList;
    private ListViewAdapter mAdapter;
    private TextView mTitle;
    private SharedPreferenceUtil spUtil;

    private String[] mDrawerItems;
    private DrawerAdapter mDrawerAdapter;
    private TextView mUserName;
    private String username="胡永港";
    private MaterialDialog mMaterialDialog;
    private Intent intent;
    private Dao mDao;
    private List<Map<String,Object>> mTimes;
    private AppApplication appApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observe);
        initData();
        initView();
    }

    /**
     * 添加过滤器并注册广播
     */
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filters=new IntentFilter();
        filters.addAction(Config.GETSCORE_SUCCESS);
        filters.addAction(Config.GETSCORE_FAIL);
        registerReceiver(receiver, filters);
    }

    private void initData() {
        mDao=new Dao(this);
        mTimes=mDao.getTimes();
        spUtil=new SharedPreferenceUtil(this, Config.SP_FILE);
        appApplication=(AppApplication)getApplication();
        mDrawerItems=getResources().getStringArray(R.array.drawer_items);

    }

    private void initToolbar(){

        mToolbar=(Toolbar)this.findViewById(R.id.toolbar);
        mTitle=new TextView(this);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        mTitle.setText(R.string.toolbar_title_main);
        Toolbar.LayoutParams params=new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT,Toolbar.LayoutParams.WRAP_CONTENT);
        params.gravity= Gravity.CENTER;
        mToolbar.addView(mTitle,params);
        mToolbar.setTitle("");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }
    private void initView(){
        initToolbar();
        initDrawer();
        mListView=(ListView)this.findViewById(R.id.gridview);
        mAdapter=new ListViewAdapter(this);
        mAdapter.setData(mTimes);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new ItemClicked());
        mRefresh=(SwipeRefreshLayout)this.findViewById(R.id.refresh);
        mRefresh.setColorScheme(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_light);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HttpUtil.getUserScore(appApplication);
            }
        });
    }

    private void initDrawer() {
        mLayout=(LinearLayout)this.findViewById(R.id.drawer_view);
        mDrawerList=(ListView)this.findViewById(R.id.drawer_listview);
        mUserName=(TextView)this.findViewById(R.id.drwaer_name);
        mUserName.setText(username);
        mDrawerAdapter=new DrawerAdapter(this,mDrawerItems);
        mDrawerList.setAdapter(mDrawerAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClicked());
        mDrawerLayout=(DrawerLayout)this.findViewById(R.id.drawer_layout);
        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                drawerArrow, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                mListView.setEnabled(true);
                Log.i(getClass().getSimpleName(),"DrawerClosed");
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                mListView.setEnabled(false);
                Log.i(getClass().getSimpleName(),"DrawerOpened");
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }


    private class ItemClicked implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this,ScoreActivity.class);
            intent.putExtra("time",mTimes.get(position).get("frequency").toString());
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mLayout)) {
                mDrawerLayout.closeDrawer(mLayout);

                mListView.setEnabled(true);

            } else {
                mDrawerLayout.openDrawer(mLayout);

                mListView.setEnabled(false);

            }
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private class DrawerItemClicked implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            intent=new Intent();
            switch (position){
                //个人信息
                case 0:
                    intent.setClass(MainActivity.this,UserInfoActivity.class);
                    startActivity(intent);
                    break;
                //成绩汇总
                case 1:
                    intent.setClass(MainActivity.this,SummaryActivity.class);
                    startActivity(intent);
                    break;
                //注销
                case 2:
                    mMaterialDialog=new MaterialDialog(MainActivity.this);
                    mMaterialDialog.setTitle("Exit?");
                    mMaterialDialog.setMessage(R.string.dialog_message_exit);
                    mMaterialDialog.setPositiveButton(R.string.dialog_button_ok,new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent();
                            intent.setClass(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();

                        }
                    });
                    mMaterialDialog.setNegativeButton(R.string.dialog_button_cancel,new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();
                        }
                    });
                    mMaterialDialog.show();

                    break;
            }

        }
    }

    /**
     * 接收网络请求发出的广播
     */
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Config.GETSCORE_SUCCESS.equals(intent.getAction())){
                String mScore=intent.getStringExtra(Config.GETSCORE_SUCCESS);
                mTimes=JsonTools.getCount(mScore);
                mDao.setTimes(mTimes);
                mAdapter.setData(mDao.getTimes());
                mAdapter.notifyDataSetChanged();
                Log.i(getClass().getSimpleName(),mScore);
                mRefresh.setRefreshing(false);
                Toast.makeText(context, R.string.refresh_success, Toast.LENGTH_SHORT).show();
            }else if(Config.GETSCORE_FAIL.equals(intent.getAction())){
                Toast.makeText(context, R.string.refresh_error, Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 取消广播注册
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


}
