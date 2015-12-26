package com.androidbook;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MappingOverlayActivity extends MapActivity {
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mapview);

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);

        Drawable marker=getResources().getDrawable(R.drawable.mapmarker); 
        marker.setBounds((int)(-marker.getIntrinsicWidth()/2),
        		-marker.getIntrinsicHeight(),
        		(int) (marker.getIntrinsicWidth()/2), 
        		0);
        
        InterestingLocations funPlaces = new InterestingLocations(marker);
        mapView.getOverlays().add(funPlaces);
        
        GeoPoint pt = funPlaces.getCenterPt();
        int latSpan = funPlaces.getLatSpanE6();
        int lonSpan = funPlaces.getLonSpanE6();
        Log.v("Overlays", "Lat span is " + latSpan);
        Log.v("Overlays", "Lon span is " + lonSpan);

        MapController mc = mapView.getController();
        mc.setCenter(pt);
        mc.zoomToSpan((int)(latSpan*1.5), (int)(lonSpan*1.5));
    }

    @Override
    protected boolean isLocationDisplayed() {
        return false;
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    class InterestingLocations extends ItemizedOverlay {
        private ArrayList<OverlayItem> locations = new ArrayList<OverlayItem>();
        private GeoPoint center = null;

        public InterestingLocations(Drawable marker)
        {
            super(marker);
            
            // create locations of interest
            GeoPoint disneyMagicKingdom = new 
                GeoPoint((int)(28.418971*1000000),(int)(-81.581436*1000000));
            GeoPoint disneySevenLagoon = new 
                GeoPoint((int)(28.410067*1000000),(int)(-81.583699*1000000));

            locations.add(new OverlayItem(disneySevenLagoon, 
                   "Seven Seas Lagoon", "Seven Seas Lagoon"));
            locations.add(new OverlayItem(disneyMagicKingdom, 
                   "Magic Kingdom", "Magic Kingdom"));

            populate();
        }

        //  We added this method to find the middle point of the cluster
        //  Start each edge on its opposite side and move across with each point.
        //  The top of the world is +90, the bottom -90,
        //  the west edge is -180, the east +180
        public GeoPoint getCenterPt() {
            if(center == null) {
                int northEdge = -90000000;   // i.e., -90E6 microdegrees
                int southEdge = 90000000;
                int eastEdge = -180000000;
                int westEdge = 180000000;
                Iterator<OverlayItem> iter = locations.iterator();
                while(iter.hasNext()) {
                    GeoPoint pt = iter.next().getPoint();
                    if(pt.getLatitudeE6() > northEdge) northEdge = pt.getLatitudeE6();
                    if(pt.getLatitudeE6() < southEdge) southEdge = pt.getLatitudeE6();
                    if(pt.getLongitudeE6() > eastEdge) eastEdge = pt.getLongitudeE6();
                    if(pt.getLongitudeE6() < westEdge) westEdge = pt.getLongitudeE6();
                }
                center = new GeoPoint((int)((northEdge + southEdge)/2),
                        (int)((westEdge + eastEdge)/2));
            }
            return center;
        }

        @Override
        public void draw(Canvas canvas, MapView mapview, boolean shadow) {
        	// Here is where we can eliminate shadows by setting to false
        	super.draw(canvas, mapview, shadow);
        }

        @Override
        protected OverlayItem createItem(int i) {
            return locations.get(i);
        }

        @Override
        public int size() {
            return locations.size();
        }
    }
}
