package com.androidbook.contacts;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class ProfileRawContactFunctionTester 
extends AggregatedContactFunctionTester 
{
	public ProfileRawContactFunctionTester(Context ctx, IReportBack target)
	{
		super(ctx, target);
	}
	public void showAllRawContacts()
	{
		Cursor c = null;
		try
		{
			c = this.getACursor(getRawContactsUri(), null);
			this.printRawContacts(c);
		}
		finally
		{
			if (c!=null) c.close();
		}
	}
	public void showRawContactsForFirstAggregatedContact()
	{
		AggregatedContact ac = getFirstContact();
		this.mReportTo.reportBack(tag, ac.displayName + ":" + ac.id);
		
		Cursor c = null;
		
		try
		{
			c = this.getACursor(getRawContactsUri(), getClause(ac.id));
			this.printRawContacts(c);
		}
		finally
		{
			if (c!=null) c.close();
		}
	}
	private void printRawContacts(Cursor c)
	{
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			RawContact rc = new RawContact();
			rc.fillinFrom(c);
			this.mReportTo.reportBack(tag, rc.toString());
		}
	}
	public void showRawContactsCursor()
	{
		AggregatedContact ac = getFirstContact();
		this.mReportTo.reportBack(tag, ac.displayName + ":" + ac.id);
		
		Cursor c = null;
		
		try
		{
			c = this.getACursor(getRawContactsUri(),null);
			this.printCursorColumnNames(c);
		}
		finally
		{
			if (c!=null) c.close();
		}
	}
	private Uri getRawContactsUri()
	{
		//return ContactsContract.RawContacts.CONTENT_URI;
		Uri prcuri = ContactsContract.Profile.CONTENT_RAW_CONTACTS_URI;
		this.mReportTo.reportBack(tag,prcuri.toString());
		return prcuri;
	}
	private String getClause(String contactId)
	{
		return "contact_id = " + contactId;
	}
}
