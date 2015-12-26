package com.androidbook.salbcr;

public class Test60SecBCR
extends ALongRunningReceiver
{
	@Override
	public Class getLRSClass() 
	{
		Utils.logThreadSignature("Test60SecBCR");
		return Test60SecBCRService.class;
	}
}
