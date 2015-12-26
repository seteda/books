import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationManagerDemoActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LocationManager locMgr = 
(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
Location loc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);

List<String> providerList = locMgr.getAllProviders();

    }
}

