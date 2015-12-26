package com.ai.android.frameanimation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ai.android.frameanimation.R;

public class FrameAnimationActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_animations_layout);
        this.setupButton();
    }

    private void setupButton()
    {
       Button b = (Button)this.findViewById(R.id.startFAButtonId);
       b.setOnClickListener(
             new Button.OnClickListener(){
                public void onClick(View v)
                {
                   parentButtonClicked(v);
                }
             });
    }
    private void parentButtonClicked(View v)
    {
    	animate();
    }    
    private void animate()
    {
    	 ImageView imgView = (ImageView)findViewById(R.id.imageView);
    	 imgView.setVisibility(ImageView.VISIBLE);
    	 imgView.setBackgroundResource(R.drawable.frame_animation);

    	 AnimationDrawable frameAnimation = 
    		 (AnimationDrawable) imgView.getBackground();

    	 if (frameAnimation.isRunning())
    	 {
        	 frameAnimation.stop();
    	 }
    	 else
    	 {
        	 frameAnimation.stop();
        	 frameAnimation.start();
    	 }
    }
}//eof-class