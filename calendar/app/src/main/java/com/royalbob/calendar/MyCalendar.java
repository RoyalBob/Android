package com.royalbob.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by RoyalBob on 2016/7/16.
 */
public class MyCalendar extends LinearLayout{

    public MyCalendar(Context context) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);

        Calendar date = Calendar.getInstance();
        TextView t1 = new TextView(this.getContext());
        TextView t2 = new TextView(this.getContext());
        t1.setText(String.valueOf(date.get(Calendar.MONTH)+1)+"月");
        t1.setTextColor(Color.WHITE);
        t1.setTextSize(20);
        t2.setText(String.valueOf(date.get(Calendar.DAY_OF_MONTH)));
        t2.setTextColor(Color.parseColor("#99CC33"));
        t2.setTextSize(30);

        LinearLayout toplly = new LinearLayout(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150,80);
        toplly.setBackgroundDrawable(getResources().getDrawable(R.drawable.layout_border2_corner10_bkff0000));
        toplly.setGravity(Gravity.CENTER_HORIZONTAL);
        toplly.addView(t1);
        this.addView(toplly,lp);

        LinearLayout bottomlly = new LinearLayout(context);
        lp = new LinearLayout.LayoutParams(150,130);
        lp.topMargin = -2;
        bottomlly.setBackgroundDrawable(getResources().getDrawable(R.drawable.layout_border2_bkcecece));
        bottomlly.setGravity(Gravity.CENTER_HORIZONTAL);
        bottomlly.addView(t2);
        this.addView(bottomlly,lp);
    }
}
