package com.example.myball;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MyBayMax extends ImageView{

	private int beginX,beginY,endX,endY,deltaX,deltaY,ballX,ballY;
	
	public MyBayMax(Context context) {
		super(context);
		this.setBackgroundDrawable(getResources().getDrawable(R.drawable.baymax));
		// TODO Auto-generated constructor stub
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
