package com.royalbob.threebuttonaction;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by RoyalBob on 2016/7/16.
 */
public class ThreeButton extends LinearLayout{

    Button btnleft,btnmid,btnright;
    Boolean isShow = true;
    OnClickListener leftbuttonListener, middlebuttonListener, rightbuttonListener;

    public ThreeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        btnleft = new Button(context);
        btnmid = new Button(context);
        btnright = new Button(context);

        btnleft.setText("显示");
        btnmid.setText("控制");
        btnright.setText("编辑");

        btnleft.setId(0);
        btnmid.setId(1);
        btnright.setId(2);
        this.setGravity(Gravity.CENTER_HORIZONTAL);
        this.addView(btnleft);
        this.addView(btnmid);
        this.addView(btnright);
        btnleft.setOnClickListener(new ButtonClick(0));
        btnmid.setOnClickListener(new ButtonClick(1));
        btnright.setOnClickListener(new ButtonClick(2));

        btnmid.setOnLongClickListener(new View.OnLongClickListener() {

            public boolean onLongClick(View v) {
                if(isShow)
                {
                    TranslateAnimation mAnimationTranslate = new TranslateAnimation(0, btnleft.getWidth() ,0, 0);
                    mAnimationTranslate.setDuration(600);
                    btnleft.startAnimation(mAnimationTranslate);
                    btnleft.setVisibility(View.INVISIBLE);
                    mAnimationTranslate = new TranslateAnimation(0, -btnright.getWidth() ,0, 0);
                    mAnimationTranslate.setDuration(600);
                    btnright.startAnimation(mAnimationTranslate);
                    btnright.setVisibility(View.INVISIBLE);
                    isShow=false;
                }
                else
                {
                    TranslateAnimation mAnimationTranslate = new TranslateAnimation(btnleft.getWidth(), 0 ,0, 0);
                    mAnimationTranslate.setDuration(600);
                    btnleft.startAnimation(mAnimationTranslate);
                    btnleft.setVisibility(View.VISIBLE);
                    mAnimationTranslate = new TranslateAnimation(-btnright.getWidth(), 0 ,0, 0);
                    mAnimationTranslate.setDuration(600);
                    btnright.startAnimation(mAnimationTranslate);
                    btnright.setVisibility(View.VISIBLE);
                    isShow=true;
                }
                return true;
            }
        });
    }
    class ButtonClick implements View.OnClickListener
    {
        private int id;
        public ButtonClick(int id)
        {
            this.id=id;
        }
        public void onClick(View v) {
            switch(id)
            {
                case 0:
                    leftbuttonListener.onClick(v);
                    break;
                case 1:
                    middlebuttonListener.onClick(v);
                    break;
                case 2:
                    rightbuttonListener.onClick(v);
                    break;
            }
        }
    }
    public void setLeftButtonListener(View.OnClickListener listener)
    {
        leftbuttonListener = listener;
    }
    public void setMiddleButtonListener(View.OnClickListener listener)
    {
        middlebuttonListener = listener;
    }
    public void setRightButtonListener(View.OnClickListener listener)
    {
        rightbuttonListener = listener;
    }
    public void setButtonText(String leftbutton, String midbutton, String rightbutton)
    {
        btnleft.setText(leftbutton);
        btnmid.setText(midbutton);
        btnright.setText(rightbutton);
    }
}
