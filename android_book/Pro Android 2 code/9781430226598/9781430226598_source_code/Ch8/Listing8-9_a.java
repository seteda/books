// BackgroundService.java

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BackgroundService extends Service
{
    private NotificationManager notificationMgr;

    @Override
    public void onCreate() {
        super.onCreate();

        notificationMgr =(NotificationManager)getSystemService(
       NOTIFICATION_SERVICE);

        displayNotificationMessage("starting Background Service");

        Thread thr = new Thread(null, new ServiceWorker(), "BackgroundService");
        thr.start();

    }

    class ServiceWorker implements Runnable
    {
        public void run() {
            // do background processing here...
        
            // stop the service when done...
            // BackgroundService.this.stopSelf();
        }
    }

    @Override
    public void onDestroy()
    {
        displayNotificationMessage("stopping Background Service");
        super.onDestroy();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void displayNotificationMessage(String message)
    {

        Notification notification = new Notification(R.drawable.note, 
message,System.currentTimeMillis());

        PendingIntent contentIntent = 
PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        notification.setLatestEventInfo(this, "Background Service",message, 
contentIntent);

        notificationMgr.notify(R.id.app_notification_id, notification);
    }
}
