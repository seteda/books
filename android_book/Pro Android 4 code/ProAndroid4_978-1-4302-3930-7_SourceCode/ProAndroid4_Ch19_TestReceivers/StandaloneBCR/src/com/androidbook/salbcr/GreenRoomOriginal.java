package com.androidbook.salbcr;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

public class GreenRoomOriginal 
{
	private static String tag="GreenRoom";
	private int count;
	private Context ctx = null;
	PowerManager.WakeLock wl = null;
	
	public GreenRoomOriginal(Context inCtx)
	{
		ctx = inCtx;
		wl = this.createWakeLock(inCtx);
	}
	synchronized public int enter()
	{
		count++;
		//every visitor coming in, turn on lights
		this.turnOnLights();
		Log.d(tag,"Turnning on Lights: count:" + count);
		return count;
	}
	synchronized public void emptyTheRoom()
	{
		Log.d(tag, "Call to empty the room");
		count = 0;
		this.turnOffLights();
	}
	synchronized public int leave()
	{
		if (count == 0) 
		{
			Log.w(tag,"Count is zero.");
			return count;
		}
		count--;
		if (count == 0)
		{
			//Last visitor
			//turn off lights
			turnOffLights();
		}
		return count;
	}
	
	synchronized public int getCount()
	{
		return count;
	}
	private void turnOnLights()
	{
		Log.d(tag, "Turning on lights. Count:" + count);
		this.wl.acquire();
	}
	private void turnOffLights()
	{
		if (this.wl.isHeld())
		{
			Log.d(tag,"Releasing wake lock. No more visitors");
			this.wl.release();
		}
	}
	private PowerManager.WakeLock createWakeLock(Context inCtx)
	{
		PowerManager pm = 
			(PowerManager)inCtx.getSystemService(Context.POWER_SERVICE); 

		PowerManager.WakeLock wl = pm.newWakeLock
		     (PowerManager.PARTIAL_WAKE_LOCK, tag);
		return wl;
	}
	
	//*************************************************
	//*static members: Needed
	//*************************************************
	private static GreenRoomOriginal s_self = null;
	
	public static void setup(Context inCtx)
	{
		if (s_self == null)
		{
			Log.d(GreenRoomOriginal.tag,"Creating green room");
			s_self = new GreenRoomOriginal(inCtx);
		}
	}
	/*
	 * Called by Service "onstart"
	 * to cover the case where the service is woken up
	 * from a previous destory
	 */
	public static boolean isSetup()
	{
		return (s_self != null) ? true: false; 
	}
	//*************************************************
	//*static members: Purely helper methods
	//*************************************************
	private static void assertSetup()
	{
		if (GreenRoomOriginal.s_self == null)
		{
			Log.w(GreenRoomOriginal.tag,"You need to call setup first");
			throw new RuntimeException("You need to setup GreenRoom first");
		}
	}
	public static int s_enter()
	{
		assertSetup();
		return s_self.enter();
	}
	public static int s_leave()
	{
		assertSetup();
		return s_self.leave();
	}
	public static void s_emptyTheRoom()
	{
		assertSetup();
		s_self.emptyTheRoom();
		return;
	}
}
