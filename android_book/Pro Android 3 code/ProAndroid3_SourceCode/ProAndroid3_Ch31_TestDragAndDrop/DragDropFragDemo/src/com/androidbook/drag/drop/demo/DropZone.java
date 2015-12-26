package com.androidbook.drag.drop.demo;

// This file is DropZone.java
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.TextView;

public class DropZone extends Fragment {
 
    private View dropTarget;
    private TextView dropMessage;

    @Override
    public View onCreateView(LayoutInflater inflater,
    		ViewGroup container, Bundle icicle)
    {
    	View v = inflater.inflate(R.layout.dropzone, container, false);

    	dropMessage = (TextView)v.findViewById(R.id.dropmessage);

    	dropTarget = (View)v.findViewById(R.id.droptarget);
    	dropTarget.setOnDragListener(new View.OnDragListener() {
    	    private static final String DROPTAG = "DropTarget";
    		private int dropCount = 0;
			private ObjectAnimator anim;

			public boolean onDrag(View v, DragEvent event) {
				int action = event.getAction();
				boolean result = true;
				switch(action) {
				case DragEvent.ACTION_DRAG_STARTED:
					Log.v(DROPTAG, "drag started in dropTarget");
		            break;
				case DragEvent.ACTION_DRAG_ENTERED:
					Log.v(DROPTAG, "drag entered dropTarget");
				    anim = ObjectAnimator.ofFloat((Object)v, "alpha", 1f, 0.5f);
				    anim.setInterpolator(new CycleInterpolator(40));
				    anim.setDuration(30*1000); // 30 seconds
				    anim.start();
		            break;
				case DragEvent.ACTION_DRAG_EXITED:
					Log.v(DROPTAG, "drag exited dropTarget");
					if(anim != null) {
						anim.end();
						anim = null;
					}
		            break;
				case DragEvent.ACTION_DRAG_LOCATION:
					Log.v(DROPTAG, "drag proceeding in dropTarget: " +
							event.getX() + ", " + event.getY());
		            break;
				case DragEvent.ACTION_DROP:
					Log.v(DROPTAG, "drag drop in dropTarget");
					if(anim != null) {
						anim.end();
						anim = null;
					}

					ClipData data = event.getClipData();
					Log.v(DROPTAG, "Item data is " + data.getItemAt(0).getText());

					dropCount++;
					String message = dropCount + " drop";
					if(dropCount > 1)
						message += "s";
					dropMessage.setText(message);
		            break;
				case DragEvent.ACTION_DRAG_ENDED:
					Log.v(DROPTAG, "drag ended in dropTarget");
					if(anim != null) {
						anim.end();
						anim = null;
					}
		            break;
		        default:
		        	Log.v(DROPTAG, "other action in dropzone: " + action);
				    result = false;
				}
				return result;
			}
		});
    	return v;
    }
}
