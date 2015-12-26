package com.androidbook.contacts;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.util.Log;

public class AggregatedContactFunctionTester extends URIFunctionTester 
{
	public AggregatedContactFunctionTester(Context ctx, IReportBack target)
	{
		super(ctx, target);
	}
	/*
	 * Get a cursor of all contacts
	 * No where clause
	 * Don't use it on a large set
	 */
    private Cursor getContacts()
    {
        // Run query
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME 
                             + " COLLATE LOCALIZED ASC";
        Activity a = (Activity)this.mContext;
        return a.managedQuery(uri, null, null, null, sortOrder);
    }
    
    public void printVariousUris()
    {
    	show("Data.Content_uri:" + Data.CONTENT_URI);
    	
    	show("ContactsContract.Profile.CONTENT_RAW_CONTACTS_URI:" 
    			+ ContactsContract.Profile.CONTENT_RAW_CONTACTS_URI);
    	
    	show("ContactsContract.Profile.CONTENT_URI:" 
    			+ ContactsContract.Profile.CONTENT_URI);
    	
    	show("ContactsContract.RawContacts.CONTENT_URI:" 
    			+ ContactsContract.RawContacts.CONTENT_URI);
    	
    	show("ContactsContract.RawContactsEntity.PROFILE_CONTENT_URI:" 
    			+ ContactsContract.RawContactsEntity.PROFILE_CONTENT_URI);
    	    	
    	show("ContactsContract.RawContactsEntity.CONTENT_URI:" 
    			+ ContactsContract.RawContactsEntity.CONTENT_URI);
    }
    protected void show(String x)
    {
		this.mReportTo.reportBack(tag, x);
    }
    protected void show(String tag, String x)
    {
		this.mReportTo.reportBack(tag, x);
    }
    /*
     * Use the getContacts above
     * to list the set of columns in the cursor
     */
	public void listContactCursorFields()
	{
		Cursor c = null;
		try
		{
			c = getContacts();
			int i = c.getColumnCount();
			this.mReportTo.reportBack(tag, "Number of columns:" + i);
			this.printCursorColumnNames(c);
		}
		finally
		{
			if (c!= null) c.close();
		}
	}

	/*
	 * Given a cursor worth of contacts
	 * Print the contact names followed by
	 * their look up keys
	 */
	private void printLookupKeys(Cursor c)
	{
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			String name=this.getContactName(c);
			String lookupKey = this.getLookupKey(c);
			String luri = this.getLookupUri(lookupKey);
			this.mReportTo.reportBack(tag, name + ":" + lookupKey);
			this.mReportTo.reportBack(tag, name + ":" + luri);
		}
	}

	/*
	 * Use the getContacts() function
	 * to get a cursor and print all 
	 * the contact names followed by look up keys
	 * uses the printLookyupKeus() function
	 */
	public void listContacts()
	{
		Cursor c = null;
		try
		{
			c = getContacts();
			int i = c.getColumnCount();
			this.mReportTo.reportBack(tag, "Number of columns:" + i);
			this.printLookupKeys(c);
		}
		finally
		{
			if (c!= null) c.close();
		}
	}
	
	/*
	 * A utility function to retrieve the
	 * look up key from a contact cursor
	 */
	private String getLookupKey(Cursor cc)
    {
    	int lookupkeyIndex = cc.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY);
    	return cc.getString(lookupkeyIndex);
    }
	
	/*
	 * A utility function to retrieve the
	 * display name from a contact cursor
	 */
    private String getContactName(Cursor cc)
    {
    	return Utils.getColumnValue(cc,ContactsContract.Contacts.DISPLAY_NAME);
    }

    /**
     * Construct a look up URI based on the 
     * Contacts URI and a lookup key
     */
    private String getLookupUri(String lookupkey)
    {
    	String luri = ContactsContract.Contacts.CONTENT_LOOKUP_URI + "/" + lookupkey;
    	return luri;
    }
    
    /**
     * Use the lookup uri 
     * to retrieve a single aggregated contact
     */
    private Cursor getASingleContact(String lookupUri)
    {
        // Run query
        Activity a = (Activity)this.mContext;
        return a.managedQuery(Uri.parse(lookupUri), null, null, null, null);
    }

    /*
     * A function to see if the URI constructed by the lookup
     * uri returns a cursor that has a different set of columns.
     * It returns a similar cursor with similar columns
     * as one would expect.
     */
	public void listLookupUriColumns()
	{
		Cursor c = null;
		try
		{
			c = getContacts();
			String firstContactLookupUri = getFirstLookupUri(c);
			printLookupUriColumns(firstContactLookupUri);
		}
		finally
		{
			if (c!= null) c.close();
		}
	}
	
	public void printLookupUriColumns(String lookupuri)
	{
		Cursor c = null;
		try
		{
			c = getASingleContact(lookupuri);
			int i = c.getColumnCount();
			this.mReportTo.reportBack(tag, "Number of columns:" + i);
			int j = c.getCount();
			this.mReportTo.reportBack(tag, "Number of rows:" + j);
			this.printCursorColumnNames(c);
		}
		finally
		{
			if (c!=null)c.close();
		}
	}

	/*
	 * Take a list of contacts
	 * look up the first contact
	 * return null if there are no contacts
	 */
	private String getFirstLookupUri(Cursor c)
	{
		c.moveToFirst();
		if (c.isAfterLast())
		{
			Log.d(tag,"No rows to get the first contact");
			return null;
		}
		//There is a row
		String lookupKey = this.getLookupKey(c);
		String luri = this.getLookupUri(lookupKey);
		return luri;
	}

	/*
	 * Take a list of contacts
	 * look up the first contact and return it
	 * as an object AggregatedContact.
	 */
	protected AggregatedContact getFirstContact()
	{
		Cursor c=null;
		try
		{
			c = getContacts();
			c.moveToFirst();
			if (c.isAfterLast())
			{
				Log.d(tag,"No contacts");
				return null;
			}
			//contact is there
			AggregatedContact firstcontact = new AggregatedContact();
			firstcontact.fillinFrom(c);
			return firstcontact;
		}
		finally
		{
			if (c!=null) c.close();
		}
	}
}
