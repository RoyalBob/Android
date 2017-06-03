package com.royalbob.gesture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

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
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

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
}
