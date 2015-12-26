package com.androidbook.contacts;

import android.database.Cursor;
import android.provider.ContactsContract;

public class AggregatedContact 
{
	public String id;
	public String lookupUri;
	public String lookupKey;
	public String displayName;
	
	public void fillinFrom(Cursor c)
	{
		id = Utils.getColumnValue(c,"_ID");
		lookupKey = Utils.getColumnValue(c,ContactsContract.Contacts.LOOKUP_KEY);
    	lookupUri = ContactsContract.Contacts.CONTENT_LOOKUP_URI + "/" + lookupKey;
    	displayName = Utils.getColumnValue(c,ContactsContract.Contacts.DISPLAY_NAME);
	}
}

/*
 *****************************************
 *getcontacts will return the following fields in the cursor
 *
times_contacted;
contact_status;
custom_ringtone;
has_phone_number;
phonetic_name;
phonetic_name_style;
contact_status_label;
lookup;
contact_status_icon;
last_time_contacted;
display_name;
sort_key_alt;
in_visible_group;
_id;
starred;
sort_key;
display_name_alt;
contact_presence;
display_name_source;
contact_status_res_package;
contact_status_ts;
photo_id;
send_to_voicemail;
 *****************************************
 */