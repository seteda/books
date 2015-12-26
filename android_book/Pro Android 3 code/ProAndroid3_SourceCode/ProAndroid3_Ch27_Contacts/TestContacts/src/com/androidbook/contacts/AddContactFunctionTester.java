package com.androidbook.contacts;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

public class AddContactFunctionTester extends ContactDataFunctionTester 
{
	public AddContactFunctionTester(Context ctx, IReportBack target)
	{
		super(ctx, target);
	}
	public void addContact()
	{
		long rawContactId = insertRawContact();
		this.mReportTo.reportBack(tag, "RawcontactId:" + rawContactId);
		insertName(rawContactId);
		insertPhoneNumber(rawContactId);
		showRawContactsDataForRawContact(rawContactId);
	}
	private void insertName(long rawContactId)
	{
		ContentValues cv = new ContentValues();
		cv.put(Data.RAW_CONTACT_ID, rawContactId);
		cv.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
		cv.put(StructuredName.DISPLAY_NAME,"John Doe_" + rawContactId);
		this.mContext.getContentResolver().insert(Data.CONTENT_URI, cv); 
	}
	private void insertPhoneNumber(long rawContactId)
	{
		ContentValues cv = new ContentValues();
		cv.put(Data.RAW_CONTACT_ID, rawContactId);
		cv.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		cv.put(Phone.NUMBER,"123 123 " + rawContactId);
		cv.put(Phone.TYPE,Phone.TYPE_HOME);
		this.mContext.getContentResolver().insert(Data.CONTENT_URI, cv); 
	}
	private long insertRawContact()
	{
		ContentValues cv = new ContentValues();
		cv.put(RawContacts.ACCOUNT_TYPE, "com.google");
		cv.put(RawContacts.ACCOUNT_NAME, "satya.komatineni@gmail.com");
		Uri rawContactUri = 
			this.mContext.getContentResolver()
			     .insert(RawContacts.CONTENT_URI, cv); 
		long rawContactId = ContentUris.parseId(rawContactUri);
		return rawContactId;
	}
	private void showRawContactsDataForRawContact(long rawContactId)
	{
		Cursor c = null;
		try
		{
			Uri uri = ContactsContract.RawContactsEntity.CONTENT_URI;
			c = this.getACursor(uri,"_id = " + rawContactId);
			this.printRawContactsData(c);
		}
		finally
		{
			if (c!=null) c.close();
		}
	}
}
