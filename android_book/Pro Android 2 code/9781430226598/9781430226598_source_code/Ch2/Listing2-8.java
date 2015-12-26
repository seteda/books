public class MyApplication extends Application 
{
    // global variable
    private static final String myGlobalVariable;

    @Override
    public void onCreate() 
    {
        super.onCreate();
        //... initialize global variables here
        myGlobalVariable = loadCacheData();
    }

    public static String getMyGlobalVariable() {
        return myGlobalVariable;
    }

}

