package com.androidbook.contacts;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

public class AddProfileContactFunctionTester 
extends ContactDataFunctionTester 
{
	private static Uri PROFILE_DATA_URI = 
		Uri.parse("content://com.android.contacts/data");
	//private static Uri PROFILE_DATA_URI = 
	//	Uri.parse("content://com.android.contacts/proifle/data");
	public AddProfileContactFunctionTester(Context ctx, IReportBack target)
	{
		super(ctx, target);
	}
	public void addContact()
	{
		long rawContactId = insertRawContact();
		this.mReportTo.reportBack(tag, "RawcontactId:" + rawContactId);
		//insertName(rawContactId);
		insertNickName(rawContactId);
		insertPhoneNumber(rawContactId);
		showRawContactsDataForRawContact(rawContactId);
	}
	private void insertNickName(long rawContactId)
	{
		ContentValues cv = new ContentValues();
		cv.put(Data.RAW_CONTACT_ID, rawContactId);
		//cv.put(Data.IS_USER_PROFILE, "1");
		cv.put(Data.MIMETYPE, CommonDataKinds.Nickname.CONTENT_ITEM_TYPE);
		cv.put(CommonDataKinds.Nickname.NAME,"PJohn Nickname_" + rawContactId);
		//this.mContext.getContentResolver().insert(Data.CONTENT_URI, cv); 
		Uri nickNameUri = this.mContext.getContentResolver().insert(
				AddProfileContactFunctionTester.PROFILE_DATA_URI, cv);
		if (nickNameUri == null)
		{
			this.show("Not able to insert nickname");
		}
		else
		{
			this.show(tag,nickNameUri.toString());
		}
	}
	private void insertName(long rawContactId)
	{
		ContentValues cv = new ContentValues();
		cv.put(Data.RAW_CONTACT_ID, rawContactId);
		//cv.put(Data.IS_USER_PROFILE, "1");
		cv.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
		cv.put(StructuredName.DISPLAY_NAME,"PJohn Doe_" + rawContactId);
		//this.mContext.getContentResolver().insert(Data.CONTENT_URI, cv); 
		Uri nameUri = this.mContext.getContentResolver().insert(
				AddProfileContactFunctionTester.PROFILE_DATA_URI, cv);
		if (nameUri == null)
		{
			show("Not able to insert name");
		}
		else
		{
			this.show(tag,nameUri.toString());
		}
	}
	private void insertPhoneNumber(long rawContactId)
	{
		ContentValues cv = new ContentValues();
		cv.put(Data.RAW_CONTACT_ID, rawContactId);
		//cv.put(Data.IS_USER_PROFILE, "1");
		cv.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		cv.put(Phone.NUMBER,"P123 123 " + rawContactId);
		cv.put(Phone.TYPE,Phone.TYPE_HOME);
		//this.mContext.getContentResolver().insert(Data.CONTENT_URI, cv); 
		Uri puri = this.mContext.getContentResolver().insert(
				AddProfileContactFunctionTester.PROFILE_DATA_URI, cv);
		if (puri == null)
		{
			show("Not able to insert phone number");
		}
		else
		{
			this.show(tag,puri.toString());
		}
	}
	private long insertRawContact()
	{
		ContentValues cv = new ContentValues();
		cv.put(RawContacts.ACCOUNT_TYPE, "com.google");
		cv.put(RawContacts.ACCOUNT_NAME, "satya.komatineni@gmail.com");
		Uri rawContactUri = 
			this.mContext.getContentResolver()
			     .insert(ContactsContract.Profile.CONTENT_RAW_CONTACTS_URI, cv); 
		long rawContactId = ContentUris.parseId(rawContactUri);
		return rawContactId;
	}
	private void showRawContactsDataForRawContact(long rawContactId)
	{
		Cursor c = null;
		try
		{
			Uri uri = ContactsContract.RawContactsEntity.PROFILE_CONTENT_URI;
			c = this.getACursor(uri,"_id = " + rawContactId);
			this.printRawContactsData(c);
		}
		finally
		{
			if (c!=null) c.close();
		}
	}
}
