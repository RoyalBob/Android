package com.example.gesture;

import android.os.Bundle;
import android.app.Activity;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnGestureListener{

	GestureDetector detector;
	ImageView imageview;
	float CurrentScale;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		detector = new GestureDetector(this);
		imageview = (ImageView) findViewById(R.id.imageView1);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		return detector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float velocityX,
			float velocityY) {
		velocityX = velocityX > 4000 ? 4000 : velocityX;
		velocityX = velocityX <-4000 ? -4000 : velocityX;
		CurrentScale += velocityX / 4000.0f;
		CurrentScale = CurrentScale > 0.1f ? CurrentScale : 0.1f ;
		imageview.layout(imageview.getLeft(), 
						 imageview.getTop(), 
						 (int)(imageview.getLeft() + 80 * CurrentScale), 
						 (int)(imageview.getTop() + 60 * CurrentScale));
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
