package apt.tutorial;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.util.Calendar;

public class OnBootReceiver extends BroadcastReceiver {
  public static void setAlarm(Context ctxt) {
    AlarmManager mgr=(AlarmManager)ctxt.getSystemService(Context.ALARM_SERVICE);
    Calendar cal=Calendar.getInstance();
    SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(ctxt);
    String time=prefs.getString("alarm_time", "12:00");
    
    cal.set(Calendar.HOUR_OF_DAY, TimePreference.getHour(time));
    cal.set(Calendar.MINUTE, TimePreference.getMinute(time));
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    
    if (cal.getTimeInMillis()<System.currentTimeMillis()) {
      cal.add(Calendar.DAY_OF_YEAR, 1);
    }
    
    mgr.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                      AlarmManager.INTERVAL_DAY,
                      getPendingIntent(ctxt));
  }
  
  public static void cancelAlarm(Context ctxt) {
    AlarmManager mgr=(AlarmManager)ctxt.getSystemService(Context.ALARM_SERVICE);

    mgr.cancel(getPendingIntent(ctxt));
  }
  
  private static PendingIntent getPendingIntent(Context ctxt) {
    Intent i=new Intent(ctxt, OnAlarmReceiver.class);
    
    return(PendingIntent.getBroadcast(ctxt, 0, i, 0));
  }
  
  @Override
  public void onReceive(Context ctxt, Intent intent) {
android.util.Log.e("****OnBootReceiver", "got here");  
    setAlarm(ctxt);
  }
}