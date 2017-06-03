package com.royalbob.baymax;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by RoyalBob on 2016/7/16.
 */
public class BayMax extends android.support.v7.widget.AppCompatImageView {
    private int beginX,beginY,endX,endY,deltaX,deltaY,ballX,ballY;

    public BayMax(Context context) {
        super(context);
        this.setBackground(getResources().getDrawable(R.drawable.baymax));
        //this.setBackgroundDrawable(getResources().getDrawable(R.drawable.baymax));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                beginX=(int) event.getRawX();
                beginY=(int) event.getRawY();
                ballX=this.getLeft();
                ballY=this.getTop();
                break;
            case MotionEvent.ACTION_MOVE:
                endX=(int) event.getRawX();
                endY=(int) event.getRawY();
                deltaX=endX-beginX;
                deltaY=endY-beginY;
                this.layout(ballX+deltaX,
                        ballY+deltaY,
                        ballX+deltaX+this.getWidth(),
                        ballY+deltaY+this.getHeight());
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
