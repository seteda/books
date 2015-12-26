package com.androidbook.BDayWidget;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public abstract class APrefWidgetModel implements IWidgetModelSaveContract
{
	private static String tag = "AWidgetModel";
	public static int STATUS_ACTIVE = 1;
	public static int STATUS_DELETED = 2;
	
	public int iid;
	public int status = STATUS_ACTIVE; 
	public APrefWidgetModel(int instanceId)
	{
		iid = instanceId;
	}
	public void setStatus(int inStatus)
	{
		status = inStatus; 
	}
	public int getStatus()
	{
		return status;
	}
	public void setDeleted()
	{
		status = STATUS_DELETED;
	}
	public boolean isDeleted()
	{
		return (status == STATUS_DELETED) ? true : false;
	}
	
	public abstract String getPrefname();
    public abstract void init();
	
	
	public Map<String,String> getPrefsToSave()
	{
		return null;
	}
    public void savePreferences(Context context) 
    {
    	Map<String,String> keyValuePairs = getPrefsToSave();
    	if (keyValuePairs == null)
    	{
    		return;
    	}
    	//going to save some values
    	
        SharedPreferences.Editor prefs = 
        	context.getSharedPreferences(getPrefname(), 0).edit();

        for(String key: keyValuePairs.keySet())
        {
        	String value = keyValuePairs.get(key);
        	savePref(prefs,key,value);
        }
        //finally commit the values
        prefs.commit();
    }
    
    private void savePref(SharedPreferences.Editor prefs, String key, String value)
    {
    	String newkey = getStoredKeyForFieldName(key);
    	Log.d(tag,"saving:" + newkey + ":" + value);
        prefs.putString(newkey, value);
    }
    private void removePref(SharedPreferences.Editor prefs, String key)
    {
    	String newkey = getStoredKeyForFieldName(key);
    	Log.d(tag,"Removing:" + newkey );
        prefs.remove(newkey);
    }
    protected String getStoredKeyForFieldName(String fieldName)
    {
    	return fieldName + "_" + iid;
    }
    public static void clearAllPreferences(Context context, String prefname) 
    {
    	Log.d(tag,"Clearing all preferences for:" + prefname);
    	SharedPreferences prefs=context.getSharedPreferences(prefname, 0);
    	Log.d(tag,"Number of preferences:" + prefs.getAll().size());
        SharedPreferences.Editor prefsEdit = prefs.edit(); 
        prefsEdit.clear();
        //finally commit the values
        prefsEdit.commit();
    }
    
    public boolean retrievePrefs(Context ctx)
    {
    	Log.d(tag,"Rerieving preferences for widget id:" + iid);
    	SharedPreferences prefs = ctx.getSharedPreferences(getPrefname(), 0);
    	Map<String,?> keyValuePairs = prefs.getAll();
    	Log.d(tag,"Number of keys for all widget ids of this type:" + keyValuePairs.size());
    	boolean prefFound = false;
    	for (String key: keyValuePairs.keySet())
    	{
    		if (isItMyPref(key) == true)
    		{
    			String value = (String)keyValuePairs.get(key);
    			Log.d(tag,"setting value for:" + key + ":" + value);
    			setValueForPref(key,value);
    			prefFound = true;
    		}
    	}
    	return prefFound;
    }
    public void removePrefs(Context context)
    {
		Log.d(tag,"Removing preferences for widget id:" + iid);
    	Map<String,String> keyValuePairs = getPrefsToSave();
    	if (keyValuePairs == null)
    	{
    		return;
    	}
    	//going to save some values
    	
        SharedPreferences.Editor prefs = 
        	context.getSharedPreferences(getPrefname(), 0).edit();

        for(String key: keyValuePairs.keySet())
        {
        	removePref(prefs,key);
        }
        //finally commit the values
        prefs.commit();
    }
    private boolean isItMyPref(String keyname)
    {
    	Log.d(tag,"Examinging keyname:" + keyname);
    	if (keyname.indexOf("_" + iid) > 0)
    	{
    		return true;
    	}
    	return false;
    }
    public void setValueForPref(String key, String value)
    {
    	return;
    }
}
