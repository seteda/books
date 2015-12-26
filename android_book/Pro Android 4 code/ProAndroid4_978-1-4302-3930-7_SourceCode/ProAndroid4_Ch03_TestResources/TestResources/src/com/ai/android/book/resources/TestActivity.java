package com.ai.android.book.resources;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class TestActivity extends MonitoredDebugActivity
implements IReportBack
{
	public static final String tag="HelloWorld";
	
	public TestActivity()
	{
		super(R.menu.main_menu, tag);
	}
	private ResourceTester resourceTester = null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resourceTester = new ResourceTester(this,this);
    }
	
    protected boolean onMenuItemSelected(MenuItem item)
    {
    	Log.d(tag,item.getTitle().toString());
    	if (item.getItemId() == R.id.menu_test_strings)
    	{
    		resourceTester.testEnStrings();
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_test_arrays)
    	{
    		resourceTester.testStringArray();
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_test_layout)
    	{
    		Intent i = new Intent(this,HelloWorldActivity.class);
    		startActivity(i);
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_test_color_drawables)
    	{
    		this.testColorDrawables();
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_test_colors)
    	{
    		resourceTester.testColor();
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_test_image)
    	{
    		this.testImage();
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_test_shape)
    	{
    		this.testShape();
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_test_string_variations)
    	{
    		resourceTester.testStringVariations();
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_test_xml)
    	{
    		resourceTester.testXML();
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_test_rawfile)
    	{
    		resourceTester.testRawFile();
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_test_assets)
    	{
    		resourceTester.testAssets();
    		return true;
    	}
    	return true;
    }
    private void testImage()
    {
    	//Call getDrawable to get the image
    	Drawable d = getResources().getDrawable(R.drawable.sample_image);
    	//You can use the drawable then to set the background
    	this.getTextView().setBackgroundDrawable(d);  	
    	//or you can set the background directly from the Resource Id
    	this.getTextView().setBackgroundResource(R.drawable.sample_image);
    }
    private void testColorDrawables()
    {
    	// Get a drawable
    	ColorDrawable redDrawable = 
    	(ColorDrawable)
    	getResources().getDrawable(R.drawable.red_rectangle);

    	//Set it as a background to a text view
    	this.getTextView().setBackgroundDrawable(redDrawable);
    }
    private void testShape()
    {
    	// Get a drawable
    	GradientDrawable roundedRectangle = 
    	(GradientDrawable)
    	getResources().getDrawable(R.drawable.my_rounded_rectangle);

    	//Set it as a background to a text view
    	this.getTextView().setBackgroundDrawable(roundedRectangle);
    	
    }
}