package com.example.mycalendar;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyCalendar extends LinearLayout{
	
	public MyCalendar(Context context) {
		super(context);
		this.setOrientation(LinearLayout.VERTICAL);
		
		Calendar date = Calendar.getInstance();
		TextView t1 = new TextView(this.getContext());
		TextView t2 = new TextView(this.getContext());
		t1.setText(String.valueOf(date.get(Calendar.MONTH)+1)+"ÔÂ");
		t1.setTextColor(Color.WHITE);
		t1.setTextSize(20);
		t2.setText(String.valueOf(date.get(Calendar.DAY_OF_MONTH)));
		t2.setTextColor(Color.BLACK);
		t2.setTextSize(30);
		
		LinearLayout toplly = new LinearLayout(context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(90,40);
		toplly.setBackgroundDrawable(getResources().getDrawable(R.drawable.layout_border2_corner10_bkff0000));
		toplly.setGravity(Gravity.CENTER_HORIZONTAL);
		toplly.addView(t1);
		this.addView(toplly,lp);
		
		LinearLayout bottomlly = new LinearLayout(context);
		lp = new LinearLayout.LayoutParams(90,70);
		lp.topMargin = -2;
		bottomlly.setBackgroundDrawable(getResources().getDrawable(R.drawable.layout_border2_bkcecece));
		bottomlly.setGravity(Gravity.CENTER_HORIZONTAL);
		bottomlly.addView(t2);
		this.addView(bottomlly,lp);
	}


}
