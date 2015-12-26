package apt.tutorial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnAlarmReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context ctxt, Intent intent) {
    Intent i=new Intent(ctxt, AlarmActivity.class);
    
    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    
    ctxt.startActivity(i);
  }
}