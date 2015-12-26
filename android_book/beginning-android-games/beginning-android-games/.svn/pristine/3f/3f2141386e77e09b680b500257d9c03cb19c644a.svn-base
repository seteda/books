package com.badlogic.androidgames;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class WakeLockTest extends FullScreenTest {
    WakeLock wakeLock;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");        
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onResume() {
        wakeLock.acquire();
        super.onResume();
    }
    
    public void onPause() {
        wakeLock.release();
        super.onPause();
    }
}
