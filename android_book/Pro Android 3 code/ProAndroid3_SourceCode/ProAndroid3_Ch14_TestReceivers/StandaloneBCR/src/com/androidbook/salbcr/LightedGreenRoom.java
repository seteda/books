package com.androidbook.salbcr;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

/*
 * What is a Lighted Green Room?
 * ****************************************************
 * A green room is a room that allows visitors.
 * A green room starts out dark.
 * The first one to "enter" turn on the lights.
 * Subsequent visitors have no effect if the lights
 * are already on.
 * The last visitor to leave will turn off the lights.
 * The methods are synchronized to keep state.
 * "enter" and "leave" could happen between multiple threads.
 * It is called a green room because it uses energy efficiently.
 * Lame, but I think gets the point across.
 * 
 * What is a lighted green room
 * ****************************************************
 * Unlike a green room that starts out lights off,
 * a lighted green room starts out with the lights on.
 * The last one to leave will turn off the lights.
 * 
 * This will be useful if there are two entry points. 
 * If one of the entry points is a delayed entry, 
 * the room not knowing that someone is scheduled to come,
 * might turn the lights off.
 * 
 * The control is taken away from the one that is "entering"
 * the room.
 * Instead the room is already lighted.
 * The "enter" is only tracked to see if the 
 * last one has exited.
 * 
 */
public class LightedGreenRoom 
{
	//debug tag
	private static String tag="LightedGreenRoom";
	
	//*************************************************
	//* A Public static interface
	//*   static members: Purely helper methods
	//*   Delegates to the underlying singleton object
	//*************************************************
	public static void setup(Context inCtx)
	{
		if (s_self == null)
		{
			Log.d(LightedGreenRoom.tag,"Creating green room and lighting it");
			s_self = new LightedGreenRoom(inCtx);
			s_self.turnOnLights();
		}
	}
	public static boolean isSetup(){
		return (s_self != null) ? true: false; 
	}
	public static int s_enter() {
		assertSetup();
		return s_self.enter();
	}
	public static int s_leave(){
		assertSetup();
		return s_self.leave();
	}
	//Dont directly call this method
	//probably will be deprecated.
	//Call register and unregister client methods instead
	public static void ds_emptyTheRoom(){
		assertSetup();
		s_self.emptyTheRoom();
		return;
	}
	public static void s_registerClient(){
		assertSetup();
		s_self.registerClient();
		return;
	}
	public static void s_unRegisterClient(){
		assertSetup();
		s_self.unRegisterClient();
		return;
	}
	private static void assertSetup(){
		if (LightedGreenRoom.s_self == null)
		{
			Log.w(LightedGreenRoom.tag,"You need to call setup first");
			throw new RuntimeException("You need to setup GreenRoom first");
		}
	}

	//*************************************************
	//* A pure private implementation
	//*************************************************
	
	//Keep count of visitors to know the last visitor.
	//On destory set the count to zero to clear the room.
	private int count;
	
	//Needed to create the wake lock
	private Context ctx = null;
	
	//Our switch
	PowerManager.WakeLock wl = null;
	
	//Multi-client support
	private int clientCount = 0;
	
	/*
	 * This is expected to be a singleton.
	 * One could potentially make the constructor
	 * private I suppose.
	 */
	private LightedGreenRoom(Context inCtx)
	{
		ctx = inCtx;
		wl = this.createWakeLock(inCtx);
	}
	
	/*
	 * Setting up the green room using a static method.
	 * This has to be called before calling any other methods.
	 * what it does:
	 * 		1. Instantiate the object
	 * 		2. acquire the lock to turn on lights
	 * Assumption:
	 * 		It is not required to be synchronized
	 * 		because it will be called from the main thread.
	 * 		(Could be wrong. need to validate this!!)
	 */
	private static LightedGreenRoom s_self = null;
	
	/*
	 * The methods "enter" and "leave" are 
	 * expected to be called in tandem.
	 * 
	 * On "enter" increment the count.
	 * 
	 * Do not turn the lights or off 
	 * as they are already turned on.
	 * 
	 * Just increment the count to know
	 * when the last visitor leaves.
	 * 
	 * This is a synchronized method as 
	 * multiple threads will be entering and leaving.
	 * 
	 */
	synchronized private int enter()
	{
		count++;
		Log.d(tag,"A new visitor: count:" + count);
		return count;
	}
	/*
	 * The methods "enter" and "leave" are 
	 * expected to be called in tandem.
	 * 
	 * On "leave" decrement the count.
	 * 
	 * If the count reaches zero turn off the lights. 
	 * 
	 * This is a synchronized method as 
	 * multiple threads will be entering and leaving.
	 * 
	 */
	synchronized private int leave()
	{
		Log.d(tag,"Leaving room:count at the call:" + count);
		//if the count is already zero
		//just leave.
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
	/*
	 * Right now no one is using this method.
	 * Just in case.
	 */
	synchronized private int getCount()
	{
		return count;
	}
	
	/*
	 * acquire the wake lock to turn the lights on
	 * it is upto other synchronized methods to call 
	 * this at the appropriate time.
	 */
	private void turnOnLights()
	{
		Log.d(tag, "Turning on lights. Count:" + count);
		this.wl.acquire();
	}
	
	/*
	 * Release the wake lock to turn the lights off.
	 * it is upto other synchronized methods to call 
	 * this at the appropriate time.
	 */
	private void turnOffLights()
	{
		if (this.wl.isHeld())
		{
			Log.d(tag,"Releasing wake lock. No more visitors");
			this.wl.release();
		}
	}
	/*
	 * Standard code to create a partial wake lock
	 */
	private PowerManager.WakeLock createWakeLock(Context inCtx)
	{
		PowerManager pm = 
			(PowerManager)inCtx.getSystemService(Context.POWER_SERVICE); 

		PowerManager.WakeLock wl = pm.newWakeLock
		     (PowerManager.PARTIAL_WAKE_LOCK, tag);
		return wl;
	}
	
	private int registerClient()
	{
		Utils.logThreadSignature(tag);
		this.clientCount++;
		Log.d(tag,"registering a new client:count:" + clientCount);
		return clientCount;
	}
	
	private int unRegisterClient()
	{
		Utils.logThreadSignature(tag);
		Log.d(tag,"un registering a new client:count:" + clientCount);
		if (clientCount == 0)
		{
			Log.w(tag,"There are no clients to unregister.");
			return 0;
		}
		//clientCount is not zero
		clientCount--;
		if (clientCount == 0)
		{
			emptyTheRoom();
		}
		return clientCount;
	}
	synchronized private void emptyTheRoom()
	{
		Log.d(tag, "Call to empty the room");
		count = 0;
		this.turnOffLights();
	}
}
