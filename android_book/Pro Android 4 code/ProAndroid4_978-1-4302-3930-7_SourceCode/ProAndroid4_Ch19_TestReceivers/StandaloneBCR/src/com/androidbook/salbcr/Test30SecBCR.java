package com.androidbook.salbcr;

public class Test30SecBCR
extends ALongRunningReceiver
{
	@Override
	public Class getLRSClass() 
	{
		Utils.logThreadSignature("Test30SecBCR");
		return Test30SecBCRService.class;
	}
}
