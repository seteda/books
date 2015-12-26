package com.androidbook.contacts;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class URIFunctionTester extends BaseTester 
{
	protected static String tag = "tc>";
	public URIFunctionTester(Context ctx, IReportBack target)
	{
		super(ctx, target);
	}
    protected Cursor getACursor(String uri,String clause)
    {
        // Run query
        Activity a = (Activity)this.mContext;
        return a.managedQuery(Uri.parse(uri), null, clause, null, null);
    }

    protected Cursor getACursor(Uri uri,String clause)
    {
        // Run query
        Activity a = (Activity)this.mContext;
        return a.managedQuery(uri, null, clause, null, null);
    }
	protected void printCursorColumnNames(Cursor c)
	{
		this.mReportTo.reportBack(tag,Utils.getCursorColumnNames(c));
	}
}
