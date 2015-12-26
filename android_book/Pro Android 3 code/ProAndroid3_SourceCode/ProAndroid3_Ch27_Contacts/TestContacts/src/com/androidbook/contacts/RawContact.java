package com.androidbook.contacts;

import android.database.Cursor;
import android.provider.ContactsContract;

public class RawContact 
{
	public String rawContactId;
	public String aggregatedContactId;
	public String accountName;
	public String accountType;
	public String displayName;
	
	public void fillinFrom(Cursor c)
	{
		rawContactId = Utils.getColumnValue(c,"_ID");
		accountName = Utils.getColumnValue(c,ContactsContract.RawContacts.ACCOUNT_NAME);
		accountType = Utils.getColumnValue(c,ContactsContract.RawContacts.ACCOUNT_TYPE);
		aggregatedContactId = Utils.getColumnValue(c,ContactsContract.RawContacts.CONTACT_ID);
		displayName = Utils.getColumnValue(c,"display_name");
	}
	public String toString()
	{
		return displayName
			+ "/" + accountName + ":" + accountType
			+ "/" + rawContactId
			+ "/" + aggregatedContactId;
	}
}

/*
 *****************************************
 *getcontacts will return the following fields in the cursor
 *
times_contacted;
 phonetic_name;
 phonetic_name_style;
 contact_id;version;
 last_time_contacted;
 aggregation_mode;
 _id;
 name_verified;
 display_name_source;
 dirty;
 send_to_voicemail;
 account_type;
 custom_ringtone;
 sync4;sync3;sync2;sync1;
 deleted;
 account_name;
 display_name;
 sort_key_alt;
 starred;
 sort_key;
 display_name_alt;
 sourceid;

 *****************************************
 */