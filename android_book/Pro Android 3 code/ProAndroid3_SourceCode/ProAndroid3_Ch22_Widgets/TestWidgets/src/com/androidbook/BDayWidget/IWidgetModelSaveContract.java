package com.androidbook.BDayWidget;

import java.util.Map;

public interface IWidgetModelSaveContract 
{
	//default exists
    public void setValueForPref(String key, String value);
    
    //you need to do this
	public String getPrefname();
	
	//default exists, returns null
	//return key value pairs you want to be saved
	public Map<String,String> getPrefsToSave();

	//you have to implement
	//gets called after restore
    public void init();
}
