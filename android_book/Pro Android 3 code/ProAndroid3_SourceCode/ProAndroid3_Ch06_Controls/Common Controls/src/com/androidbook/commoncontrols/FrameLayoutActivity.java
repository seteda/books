package com.androidbook.commoncontrols;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class FrameLayoutActivity extends Activity{
	private ImageView one = null;
	private ImageView two = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.listing6_46);

	    one = (ImageView)findViewById(R.id.oneImgView);
	    two = (ImageView)findViewById(R.id.twoImgView);

	    one.setOnClickListener(new OnClickListener(){

	    	public void onClick(View view) {
	            two.setVisibility(View.VISIBLE);

	            view.setVisibility(View.GONE);
	        }});

	    two.setOnClickListener(new OnClickListener(){

	        public void onClick(View view) {
	            one.setVisibility(View.VISIBLE);

	            view.setVisibility(View.GONE);
	        }});
	}
}