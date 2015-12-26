package com.androidbook.drag.drop.demo;

// This file is Dot.java
import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

public class Dot extends View 
    implements View.OnDragListener
{
	private static final int DEFAULT_RADIUS = 20;
	private static final int DEFAULT_COLOR = Color.WHITE;
	private static final int SELECTED_COLOR = Color.MAGENTA;
	protected static final String DOTTAG = "DragDot";
	private Paint mNormalPaint;
	private Paint mDraggingPaint;
	private int mColor = DEFAULT_COLOR;
	private int mRadius = DEFAULT_RADIUS;
	private boolean inDrag;

	public Dot(Context context, AttributeSet attrs) {
		super(context, attrs);

        // Apply attribute settings from the layout file.
    	// Note: these could change on a reconfiguration
    	// such as a screen rotation.
        TypedArray myAttrs = context.obtainStyledAttributes(attrs,
                R.styleable.Dot);

        final int numAttrs = myAttrs.getIndexCount();
        for (int i = 0; i < numAttrs; i++) {
            int attr = myAttrs.getIndex(i);
            switch (attr) {
            case R.styleable.Dot_radius:
                mRadius = myAttrs.getDimensionPixelSize(attr, DEFAULT_RADIUS);
                break;
            case R.styleable.Dot_color:
            	mColor = myAttrs.getColor(attr, DEFAULT_COLOR);
                break;
            }
        }
        myAttrs.recycle();

        // Setup paint colors
	    mNormalPaint = new Paint();
	    mNormalPaint.setColor(mColor);
	    mNormalPaint.setAntiAlias(true);

	    mDraggingPaint = new Paint();
	    mDraggingPaint.setColor(SELECTED_COLOR);
	    mDraggingPaint.setAntiAlias(true);

	    // Start a drag on a long click on the dot
	    setOnLongClickListener(lcListener);
	    setOnDragListener(this);
    }
	
	private static View.OnLongClickListener lcListener =
		new View.OnLongClickListener() {
        private boolean mDragInProgress;

		public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("DragData", (String)v.getTag());
            mDragInProgress = v.startDrag(data, new View.DragShadowBuilder(v),
                    (Object)v, 0);
            Log.v((String) v.getTag(), "starting drag? " + mDragInProgress);
            return true;
        }
    };

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int size = 2*mRadius + getPaddingLeft() + getPaddingRight();
        setMeasuredDimension(size, size);
    }

    // The dragging functionality
    public boolean onDrag(View v, DragEvent event) {
    	String dotTAG = (String) getTag();
    	// Only worry about drag events if this is us being dragged
    	if(event.getLocalState() != this) {
    		Log.v(dotTAG, "This drag event is not for us");
    		return false;
    	}
		boolean result = true;

		// get event values to work with
		int action = event.getAction();
		float x = event.getX();
		float y = event.getY();

		switch(action) {
		case DragEvent.ACTION_DRAG_STARTED:
			Log.v(dotTAG, "drag started. X: " + x + ", Y: " + y);
			inDrag = true; // used in draw() below to change color
			break;
		case DragEvent.ACTION_DRAG_LOCATION:
			Log.v(dotTAG, "drag proceeding... At: " + x + ", " + y);
			break;
		case DragEvent.ACTION_DRAG_ENTERED:
			Log.v(dotTAG, "drag entered. At: " + x + ", " + y);
			break;
		case DragEvent.ACTION_DRAG_EXITED:
			Log.v(dotTAG, "drag exited. At: " + x + ", " + y);
			break;
		case DragEvent.ACTION_DROP:
			Log.v(dotTAG, "drag dropped. At: " + x + ", " + y);
			// Return false because we don't accept the drop in Dot.
			result = false;
			break;
		case DragEvent.ACTION_DRAG_ENDED:
			Log.v(dotTAG, "drag ended. Success? " + event.getResult());
			inDrag = false; // change color of original dot back
			break;
        default:
        	Log.v(dotTAG, "some other drag action: " + action);
        	result = false;
        	break;
		}
		return result;
	}

    // Here is where we draw our dot, and where we change the color if
    // we're in the process of being dragged. Note: the color change affects
    // the original dot only, not the shadow.
	public void draw(Canvas canvas) {
		float cx = this.getWidth()/2 + getLeftPaddingOffset();
		float cy = this.getHeight()/2 + getTopPaddingOffset();
		Paint paint = mNormalPaint;
		if(inDrag)
			paint = mDraggingPaint;
		canvas.drawCircle(cx, cy, mRadius, paint);
		invalidate();
	}
}
