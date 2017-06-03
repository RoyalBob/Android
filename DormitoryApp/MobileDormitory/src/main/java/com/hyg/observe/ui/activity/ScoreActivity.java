package com.hyg.observe.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.hyg.activity.R;
import com.hyg.observe.ui.fragment.ChartFragment;
import com.hyg.observe.ui.fragment.ScoreFragment;
import com.hyg.observe.utils.AppTools;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hyg on 2015/4/16.
 * 成绩展示
 *
 */
public class ScoreActivity extends ActionBarActivity{
    private Toolbar mToolbar;
    private PagerSlidingTabStrip mTabStrip;
    private ViewPager mViewPager;
    private String[] mTitles;
    private ChartFragment mChart;
    private ScoreFragment mScore;
    private List<Fragment> mList;
    private String mTime;
    private Intent intent;
    private Bundle mBundle;
    private AppTools appTools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        initData();
        initView();

    }

    private void initData(){
        appTools=new AppTools(this);
        mTitles=getResources().getStringArray(R.array.titles);
        mTime=getIntent().getStringExtra("time");
        mScore=new ScoreFragment();
        mBundle=new Bundle();
        mBundle.putString("time",mTime);
        mScore.setArguments(mBundle);
        mChart=new ChartFragment();
        mChart.setArguments(mBundle);
        mList=new ArrayList<>();
        mList.add(mScore);
        mList.add(mChart);
    }

    private void initView() {
        initToolbar();
        initPager();
        initTab();
    }

    private void initToolbar() {
        mToolbar=(Toolbar)this.findViewById(R.id.toolbar);
        mToolbar.setTitle(appTools.flagToCount(mTime));
        setSupportActionBar(mToolbar);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){

                }
                return true;
            }
        });
        mToolbar.setNavigationIcon(R.drawable.header_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScoreActivity.this.finish();
            }
        });
    }

    private void initPager(){
        mViewPager=(ViewPager)this.findViewById(R.id.pager);
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

    }
    private void initTab(){
        mTabStrip=(PagerSlidingTabStrip)this.findViewById(R.id.tabs);
        // 底部游标颜色
        mTabStrip.setIndicatorColor(Color.BLUE);
        // tab的分割线颜色
        mTabStrip.setDividerColor(Color.TRANSPARENT);
        // tab背景
        mTabStrip.setBackgroundColor(Color.WHITE);
        // tab底线高度
        mTabStrip.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                1, getResources().getDisplayMetrics()));
        // 游标高度
        mTabStrip.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                5, getResources().getDisplayMetrics()));
        // 文字颜色
        mTabStrip.setTextColor(Color.BLACK);
        mTabStrip.setShouldExpand(true);
        mTabStrip.setViewPager(mViewPager);
        mTabStrip.setOnPageChangeListener(new PageChangeListener());
    }

    private class PagerAdapter extends FragmentPagerAdapter{

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }


    private class PageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
