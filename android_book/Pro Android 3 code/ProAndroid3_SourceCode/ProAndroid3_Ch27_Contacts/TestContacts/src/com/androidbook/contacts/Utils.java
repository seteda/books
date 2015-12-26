package com.androidbook.contacts;

import android.database.Cursor;

public class Utils 
{
    public static String getColumnValue(Cursor cc, String cname)
    {
    	int i = cc.getColumnIndex(cname);
    	return cc.getString(i);
    }
    
	protected static String getCursorColumnNames(Cursor c)
	{
		int count = c.getColumnCount();
		StringBuffer cnamesBuffer = new StringBuffer();
		for (int i=0;i<count;i++)
		{
			String cname = c.getColumnName(i);
			cnamesBuffer.append(cname).append(';');
		}
		return cnamesBuffer.toString();
	}
}
