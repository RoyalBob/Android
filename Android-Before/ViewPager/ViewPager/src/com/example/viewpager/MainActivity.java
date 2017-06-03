package com.example.viewpager;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.app.Activity;

public class MainActivity extends Activity {
	ViewPager mViewPage;
	RadioGroup mRadioGroup;
	RadioButton RadioButton1, RadioButton2, RadioButton3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		intializePage();
		intializeRadioGroup();
	}
	private void intializeRadioGroup() {
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		RadioButton1 = (RadioButton) findViewById(R.id.radio0);
		RadioButton2 = (RadioButton) findViewById(R.id.radio1);
		RadioButton3 = (RadioButton) findViewById(R.id.radio2);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				switch(arg1)
				{
				case R.id.radio0:
					mViewPage.setCurrentItem(0);
					break;
				case R.id.radio1:
					mViewPage.setCurrentItem(1);
					break;
				case R.id.radio2:
					mViewPage.setCurrentItem(2);
					break;
				}
				
			}
		});
		
	}
	private void intializePage() {
		LayoutInflater inflater = getLayoutInflater().from(this);
		View view1 = inflater.inflate(R.layout.layout1, null);
		View view2 = inflater.inflate(R.layout.layout2, null);
		View view3 = inflater.inflate(R.layout.layout3, null);
		final List<View> viewList = new ArrayList<View>();// 将要分页显示的View装入数组中  
        viewList.add(view1);  
        viewList.add(view2);  
        viewList.add(view3); 
        
        mViewPage = (ViewPager) findViewById(R.id.viewpager1);
		PagerAdapter mpagerAdapter = new PagerAdapter() {
			
			@Override
			public void destroyItem(ViewGroup container, int position,Object object) {
				container.removeView(viewList.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(viewList.get(position));
				return viewList.get(position);
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0==arg1;
			}
			
			@Override
			public int getCount() {
				return viewList.size();
			}
		};
		mViewPage.setAdapter(mpagerAdapter);
		mViewPage.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int id) {
				switch(id)
				{
				case 0:
					mRadioGroup.check(R.id.radio0);
					break;
				case 1:
					mRadioGroup.check(R.id.radio1);
					break;
				case 2:
					mRadioGroup.check(R.id.radio2);		
					break;
				}
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	
}
