package com.androidbook.touch.dragdemo;

// This file is Dot.java
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class Dot extends View {
	private static final String TAG = "TouchDrag";
    private float left = 0;
    private float top = 0;
    private float radius = 20;
	private float offsetX;
	private float offsetY;
	private Paint myPaint;
	private Context myContext;

	public Dot(Context context, AttributeSet attrs) {
		super(context, attrs);

		// Save the context (the activity)
		myContext = context;

	    myPaint = new Paint();
	    myPaint.setColor(Color.WHITE);
	    myPaint.setAntiAlias(true);
	}

	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float eventX = event.getX();
		float eventY = event.getY();
		switch(action) {
		case MotionEvent.ACTION_DOWN:
			// First make sure the touch is on our dot,
			// since the size of the dot's view is
			// technically the whole layout. If the
			// touch is *not* within, then return false
			// indicating we don't want any more events.
			if( !(left-20 < eventX && eventX < left+radius*2+20 &&
				top-20 < eventY && eventY < top+radius*2+20))
				return false;

			// Remember the offset of the touch as compared
			// to our left and top edges.
			offsetX = eventX - left;
			offsetY = eventY - top;
			break;
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			left = eventX - offsetX;
			top = eventY - offsetY;
			if(action == MotionEvent.ACTION_UP) {
				checkDrop(eventX, eventY);
			}
			break;
		}
        invalidate();
		return true;
	}

    private void checkDrop(float x, float y) {
		// See if the x,y of our drop location is near to
    	// one of our counters. If so, increment it, and
    	// reset the dot back to its starting position
    	Log.v(TAG, "checking drop target for " + x + ", " + y);
		int viewCount = ((MainActivity)myContext).counterLayout.getChildCount();
		for(int i = 0; i<viewCount; i++) {
			View view = ((MainActivity)myContext).counterLayout.getChildAt(i);
			if(view.getClass() == TextView.class){
				Log.v(TAG, "Is the drop to the right of " + (view.getLeft()-20));
				Log.v(TAG, "  and vertically between " + (view.getTop()-20)
						+ " and " + (view.getBottom()+20) + "?");
				if(x > view.getLeft()-20 &&
						view.getTop()-20 < y &&
						y < view.getBottom()+20) {
					Log.v(TAG, "     Yes. Yes it is.");
					
					// Increase the count value in the TextView by one
					int count =
						Integer.parseInt(((TextView)view).getText().toString());
					((TextView)view).setText(String.valueOf( ++count ));
					
					// Reset the dot back to starting position
					left = top = 0;
					break;
				}
			}
		}
	}

	public void draw(Canvas canvas) {
        canvas.drawCircle(left + radius, top + radius, radius, myPaint);
    }
}
