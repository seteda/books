package com.androidbook.contacts;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class ContactDataFunctionTester extends RawContactFunctionTester 
{
	public ContactDataFunctionTester(Context ctx, IReportBack target)
	{
		super(ctx, target);
	}
	public void showRawContactsEntityCursor()
	{
		Cursor c = null;
		try
		{
			Uri uri = ContactsContract.RawContactsEntity.CONTENT_URI;
			c = this.getACursor(uri,null);
			this.printCursorColumnNames(c);
		}
		finally
		{
			if (c!=null) c.close();
		}
	}
	public void showRawContactsData()
	{
		Cursor c = null;
		try
		{
			Uri uri = ContactsContract.RawContactsEntity.CONTENT_URI;
			c = this.getACursor(uri,"contact_id in (3,4,5)");
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
