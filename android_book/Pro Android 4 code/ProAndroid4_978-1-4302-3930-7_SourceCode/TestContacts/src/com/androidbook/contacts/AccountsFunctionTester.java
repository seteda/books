package com.androidbook.contacts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

public class AccountsFunctionTester extends BaseTester 
{
	private static String tag = "tc>";
	public AccountsFunctionTester(Context ctx, IReportBack target)
	{
		super(ctx, target);
	}
	public void testAccounts()
	{
		AccountManager am = AccountManager.get(this.mContext); 
		Account[] accounts = am.getAccounts();
		for(Account ac: accounts)
		{
			String acname=ac.name;
			String actype = ac.type;
			this.mReportTo.reportBack(tag,acname + ":" + actype);
		}
	}
}
