package apt.tutorial;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RemoteViews;

public class WidgetService extends IntentService {
  public WidgetService() {
    super("WidgetService");
  }
    
  @Override
  public void onHandleIntent(Intent intent) {
    ComponentName me=new ComponentName(this, AppWidget.class);
    RemoteViews updateViews=new RemoteViews("apt.tutorial",
                                            R.layout.widget);
    RestaurantHelper helper=new RestaurantHelper(this);
    AppWidgetManager mgr=AppWidgetManager.getInstance(this);
    
    try {
      Cursor c=helper
                .getReadableDatabase()
                .rawQuery("SELECT COUNT(*) FROM restaurants", null);
      
      c.moveToFirst();
      
      int count=c.getInt(0);
      
      c.close();
      
      if (count>0) {
        int offset=(int)(count*Math.random());
        String args[]={String.valueOf(offset)};
        
        c=helper
            .getReadableDatabase()
            .rawQuery("SELECT _ID, name FROM restaurants LIMIT 1 OFFSET ?", args);
        c.moveToFirst();
        updateViews.setTextViewText(R.id.name, c.getString(1));
        
        Intent i=new Intent(this, DetailForm.class);
        
        i.putExtra(LunchList.ID_EXTRA, c.getString(0));
        
        PendingIntent pi=PendingIntent.getActivity(this, 0, i,
                                                    PendingIntent.FLAG_UPDATE_CURRENT);
        
        updateViews.setOnClickPendingIntent(R.id.name, pi);
      }
      else {
        updateViews.setTextViewText(R.id.title,
                                    this.getString(R.string.empty));
      }
    }
    finally {
      helper.close();
    }
    
    Intent i=new Intent(this, WidgetService.class);
    PendingIntent pi=PendingIntent.getService(this, 0, i, 0);
      
    updateViews.setOnClickPendingIntent(R.id.next, pi);
    mgr.updateAppWidget(me, updateViews);
  }
}