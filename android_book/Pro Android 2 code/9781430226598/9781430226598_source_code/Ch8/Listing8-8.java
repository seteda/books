Application  getApplication();
abstract IBinder  onBind(Intent intent);
void onConfigurationChanged(Configuration newConfig);
void    onCreate();
void    onDestroy();
void    onLowMemory();
void    onRebind(Intent intent);
void    onStart(Intent intent, int startId);
boolean  onUnbind(Intent intent);
final void      setForeground(boolean isForeground);
final void      stopSelf();
final void      stopSelf(int startId);
final boolean   stopSelfResult(int startId);

