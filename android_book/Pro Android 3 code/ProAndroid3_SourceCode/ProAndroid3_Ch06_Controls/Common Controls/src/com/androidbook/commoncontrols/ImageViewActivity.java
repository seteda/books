package com.androidbook.commoncontrols;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageViewActivity extends Activity {
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.imageview);
	        
	        ImageView imgView = (ImageView)findViewById(R.id.image3);
	        
	        imgView.setImageResource( R.drawable.icon );

	        imgView.setImageBitmap(BitmapFactory.decodeResource(
                                   this.getResources(), R.drawable.manatee14) );
	        
	        imgView.setImageDrawable(
                        Drawable.createFromPath("/mnt/sdcard/dave2.jpg") );

	        imgView.setImageURI(Uri.parse("file://mnt/sdcard/dave2.jpg"));
	    }
}
