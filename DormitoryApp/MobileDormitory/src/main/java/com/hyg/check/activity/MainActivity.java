package com.hyg.check.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hyg.check.Utils.AppTools;
import com.hyg.check.Utils.SharedPreferenceUtil;
import com.hyg.activity.R;
import com.hyg.check.app.AppApplication;
import com.hyg.check.fragment.CheckFragment;
import com.hyg.check.fragment.ReviewFragment;
import com.hyg.check.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * 功能描述：主界面，包括主页，复查和设置，菜单键的界面切换
 */
public class MainActivity extends ActionBarActivity {
    private Toolbar mToolbar;
    private ViewPager mPager;
    private TextView mTitle;
    private CheckFragment mIndex;
    private SettingFragment mSetting;
    private ReviewFragment mReview;
    private List<Fragment> list;
    private RadioButton mIndexBtn;
    private RadioButton mSetBtn;
    private RadioButton mReviewBtn;
    private RadioGroup mGroup;
    private ColorStateList mColor;
    private AppApplication application;
    private SharedPreferenceUtil spUtil;
    private AppTools appTools;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initFragment();
        initView();
        initToolbar();
    }

    private void initData() {
        //获取application实例
        application=(AppApplication)getApplication();
        //获取SharedPreferenceUtil实例
        spUtil=application.getSpUtil();
        appTools=new AppTools(this);
    }

    /**
     * 初始化3个fragment
     */
    private void initFragment() {
        mIndex=new CheckFragment();
        mSetting=new SettingFragment();
        mReview=new ReviewFragment();
        list=new ArrayList<Fragment>();
        list.add(mIndex);
        list.add(mReview);
        list.add(mSetting);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mIndexBtn=(RadioButton)this.findViewById(R.id.menu_index);
        mSetBtn=(RadioButton)this.findViewById(R.id.menu_setting);
        mReviewBtn=(RadioButton)this.findViewById(R.id.menu_review);
        mGroup=(RadioGroup)this.findViewById(R.id.main_menu);
        mPager=(ViewPager)this.findViewById(R.id.main_viewpager);
        mToolbar=(Toolbar)this.findViewById(R.id.main_toolbar);
        mPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mPager.setOnPageChangeListener(new PagerChangeListener());
        mGroup.setOnCheckedChangeListener(new OnCheckedListener());
        mGroup.check(mIndexBtn.getId());
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        Toolbar.LayoutParams params=new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
        params.gravity= Gravity.CENTER;
        mTitle=new TextView(this);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        mTitle.setText(R.string.menu_title_index);
        mToolbar.addView(mTitle, params);

        mToolbar.inflateMenu(R.menu.menu_main);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //显示选取楼层所对应的房间信息
                mIndex.showRoomInfo(menuItem);
                return true;
            }
        });
    }

    /**
     * 底部导航按钮的点击事件
     */
    private class OnCheckedListener implements RadioGroup.OnCheckedChangeListener{


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
           mColor=MainActivity.this.getResources().getColorStateList(R.color.radiobutton_color);
           switch(checkedId){
                case R.id.menu_index:
                    //显示对应的fragment
                    mPager.setCurrentItem(0);
                    //修改导航按钮的颜色
                    mIndexBtn.setTextColor(mColor);
                    //设置toolbar的标题
                    mToolbar.setTitle(appTools.flagToArea(spUtil.getArea()));
                    //设置toolbar的副标题
                    mToolbar.setSubtitle(appTools.flagToBuild(spUtil.getBuild()));
                    break;
                case R.id.menu_review:
                    mPager.setCurrentItem(1);
                    mReviewBtn.setTextColor(mColor);
                    mToolbar.setTitle("");
                    mToolbar.setSubtitle("");
                    break;
                case R.id.menu_setting:
                    mPager.setCurrentItem(2);
                    mSetBtn.setTextColor(mColor);
                    mToolbar.setTitle("");
                    mToolbar.setSubtitle("");
                    break;


            }
        }
    }




    /**
     * pagerview滑动监听
     */
    private class PagerChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    //设置标题
                    mTitle.setText(R.string.menu_title_index);
                    //导航键显示对应的页面
                    mGroup.check(mIndexBtn.getId());
                    //清除menu
                    mToolbar.getMenu().clear();
                    //显示menu
                    mToolbar.inflateMenu(R.menu.menu_main);
                    //设置toolbar标题
                    mToolbar.setTitle(appTools.flagToArea(spUtil.getArea()));
                    //设置toolbar副标题
                    mToolbar.setSubtitle(appTools.flagToBuild(spUtil.getBuild()));
                    break;
                case 1:
                    mTitle.setText(R.string.menu_title_review);
                    mGroup.check(mReviewBtn.getId());
                    mToolbar.getMenu().clear();
                    mToolbar.setTitle("");
                    mToolbar.setSubtitle("");
                    break;
                case 2:
                    mTitle.setText(R.string.menu_title_setting);
                    mGroup.check(mSetBtn.getId());
                    mToolbar.getMenu().clear();
                    mToolbar.setTitle("");
                    mToolbar.setSubtitle("");
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    /**
     * viewpager适配器
     */
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }



}
