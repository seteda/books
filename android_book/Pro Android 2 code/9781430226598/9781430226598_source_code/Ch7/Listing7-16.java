import java.io.IOException;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class GeocodingDemoActivity extends MapActivity
{
    Geocoder geocoder = null;
    MapView mapView = null;

    @Override
    protected boolean isLocationDisplayed() {
        return false;
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.geocode);
        mapView = (MapView)findViewById(R.id.geoMap);
        mapView.setBuiltInZoomControls(true);

        // lat/long of Jacksonville, FL
        int lat = (int)(30.334954*1000000);
        int lng = (int)(-81.5625*1000000);
        GeoPoint pt = new GeoPoint(lat,lng);
        mapView.getController().setZoom(10);
        mapView.getController().setCenter(pt);

        Button geoBtn =(Button)findViewById(R.id.geocodeBtn);

        geocoder = new Geocoder(this);

        geoBtn.setOnClickListener(new OnClickListener(){

        @Override
        public void onClick(View arg0) {
            try {
                EditText loc = (EditText)findViewById(R.id.location);
                String locationName = loc.getText().toString();

                List<Address> addressList = 
geocoder.getFromLocationName(locationName, 5);
                if(addressList!=null && addressList.size()>0)
                {
                    int lat = (int)(addressList.get(0).getLatitude()*1000000);
                    int lng = (int)(addressList.get(0).getLongitude()*1000000);

                    GeoPoint pt = new GeoPoint(lat,lng);
                    mapView.getController().setZoom(15);
                    mapView.getController().setCenter(pt);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }});

    }
}

