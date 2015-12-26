import android.app.Service;
public class TestService1 extends Service
{
    private static final String TAG = "TestService1";


    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }
}
