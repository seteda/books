package com.androidbook.actionbar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

public class SimpleSpinnerArrayAdapter 
extends ArrayAdapter<String>
implements SpinnerAdapter
{
	public SimpleSpinnerArrayAdapter(Context ctx)
	{
		super(ctx,
		  android.R.layout.simple_spinner_item, 
		  new String[]{"one","two"});
		
		this.setDropDownViewResource(
		  android.R.layout.simple_spinner_dropdown_item);
	}
	public View getDropDownView(
	  int position, View convertView, ViewGroup parent)
	{
		return super.getDropDownView(
		  position, convertView, parent);
	}
}
