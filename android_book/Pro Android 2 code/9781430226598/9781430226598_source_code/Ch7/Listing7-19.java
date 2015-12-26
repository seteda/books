import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class LocationUpdateDemoActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        LocationManager locMgr = (LocationManager) 
getSystemService(Context.LOCATION_SERVICE);

        LocationListener locListener = new LocationListener()
        {

            public void  onLocationChanged(Location location)
                {
                        if (location != null)
                        {
                                Toast.makeText(getBaseContext(),
                                    "New location latitude [" + 
location.getLatitude() +
                                    "] longitude [" + location.getLongitude()+"]",
                                    Toast.LENGTH_SHORT).show();
                        }
                }

                public void  onProviderDisabled(String provider)
                {
                }

                public void  onProviderEnabled(String provider)
                {
                }

                public void  onStatusChanged(String provider, 
int status, Bundle extras)
                {
                }

        };

        locMgr.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,		// minTime in ms
            0,		// minDistance in meters
            locListener);
    }
}

