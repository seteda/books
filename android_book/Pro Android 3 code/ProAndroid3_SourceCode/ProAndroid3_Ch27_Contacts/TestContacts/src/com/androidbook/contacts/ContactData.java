package com.androidbook.contacts;

import android.database.Cursor;
import android.provider.ContactsContract;

public class ContactData 
{
	public String rawContactId;
	public String aggregatedContactId;
	public String dataId;
	public String accountName;
	public String accountType;
	public String mimetype;
	public String data1;
	
	public void fillinFrom(Cursor c)
	{
		rawContactId = Utils.getColumnValue(c,"_ID");
		accountName = Utils.getColumnValue(c,ContactsContract.RawContacts.ACCOUNT_NAME);
		accountType = Utils.getColumnValue(c,ContactsContract.RawContacts.ACCOUNT_TYPE);
		aggregatedContactId = Utils.getColumnValue(c,ContactsContract.RawContacts.CONTACT_ID);
		mimetype = Utils.getColumnValue(c,ContactsContract.RawContactsEntity.MIMETYPE);
		data1 = Utils.getColumnValue(c,ContactsContract.RawContactsEntity.DATA1);
		dataId = Utils.getColumnValue(c,ContactsContract.RawContactsEntity.DATA_ID);
	}
	public String toString()
	{
		return data1 + "/" + mimetype
			+ "/" + accountName + ":" + accountType
			+ "/" + dataId
			+ "/" + rawContactId
			+ "/" + aggregatedContactId;
	}
}

/*
 *****************************************
 *getcontacts will return the following fields in the cursor
 *
data_version;
contact_id;
version;
data12;data11;data10;
mimetype;
res_package;
_id;
data15;data14;data13;
name_verified;
is_restricted;
is_super_primary;
data_sync1;dirty;data_sync3;data_sync2;
data_sync4;account_type;data1;sync4;sync3;
data4;sync2;data5;sync1;
data2;data3;data8;data9;
deleted;
group_sourceid;
data6;data7;
account_name;
data_id;
starred;
sourceid;
is_primary;
 *****************************************
 */