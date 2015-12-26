package com.androidbook.contacts;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class ProifleContactDataFunctionTester extends RawContactFunctionTester 
{
	public ProifleContactDataFunctionTester(Context ctx, IReportBack target)
	{
		super(ctx, target);
	}
	public void showRawContactsEntityCursor()
	{
		Cursor c = null;
		try
		{
			Uri uri = ContactsContract.RawContactsEntity.PROFILE_CONTENT_URI;
			c = this.getACursor(uri,null);
			this.printCursorColumnNames(c);
		}
		finally
		{
			if (c!=null) c.close();
		}
	}
	public void showProfileRawContactsData()
	{
		Cursor c = null;
		try
		{
			Uri uri = ContactsContract.RawContactsEntity.PROFILE_CONTENT_URI;
			//c = this.getACursor(uri,"contact_id in (3,4,5)");
			c = this.getACursor(uri,null);
			this.printRawContactsData(c);
		}
		finally
		{
			if (c!=null) c.close();
		}
	}
	protected void printRawContactsData(Cursor c)
	{
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			ContactData dataRecord = new ContactData();
			dataRecord.fillinFrom(c);
			this.mReportTo.reportBack(tag, dataRecord.toString());
		}
	}
}
