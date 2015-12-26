package com.androidbook.touch.demo2;

// This file is TrueButton.java
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class TrueButton extends Button {
    
	public TrueButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		String myTag = this.getTag().toString();
		Log.v(myTag, "-----------------------------------");
		Log.v(myTag, MainActivity.describeEvent(this, event));
		Log.v(myTag, "super onTouchEvent() returns " + super.onTouchEvent(event));
		Log.v(myTag, "and I'm returning true");
		return(true);
	}
}
