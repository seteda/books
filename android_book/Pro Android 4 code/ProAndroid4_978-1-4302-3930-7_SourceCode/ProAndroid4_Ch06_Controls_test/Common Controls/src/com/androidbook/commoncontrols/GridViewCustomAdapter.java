package com.androidbook.commoncontrols;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewCustomAdapter extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridviewcustom);
        
        GridView gv = (GridView)findViewById(R.id.gridview);

        ManateeAdapter adapter = new ManateeAdapter(this);

        gv.setAdapter(adapter);
    }
    
    public static class ManateeAdapter extends BaseAdapter {
        private static final String TAG = "ManateeAdapter";
        private static int convertViewCounter = 0;
		private Context mContext;
        private LayoutInflater mInflater;

        static class ViewHolder {
            ImageView image;
        }
        
        private int[] manatees = {
                R.drawable.manatee00, R.drawable.manatee01, R.drawable.manatee02,
                R.drawable.manatee03, R.drawable.manatee04, R.drawable.manatee05,
                R.drawable.manatee06, R.drawable.manatee07, R.drawable.manatee08,
                R.drawable.manatee09, R.drawable.manatee10, R.drawable.manatee11,
                R.drawable.manatee12, R.drawable.manatee13, R.drawable.manatee14,
                R.drawable.manatee15, R.drawable.manatee16, R.drawable.manatee17,
                R.drawable.manatee18, R.drawable.manatee19, R.drawable.manatee20,
                R.drawable.manatee21, R.drawable.manatee22, R.drawable.manatee23,
                R.drawable.manatee24, R.drawable.manatee25, R.drawable.manatee26,
                R.drawable.manatee27, R.drawable.manatee28, R.drawable.manatee29,
                R.drawable.manatee30, R.drawable.manatee31, R.drawable.manatee32,
                R.drawable.manatee33 };
        
        private Bitmap[] manateeImages = new Bitmap[manatees.length];
        private Bitmap[] manateeThumbs = new Bitmap[manatees.length];

        public ManateeAdapter(Context context) {
        	Log.v(TAG, "Constructing ManateeAdapter");
        	this.mContext = context;
        	mInflater = LayoutInflater.from(context);
        	
        	for(int i=0; i<manatees.length; i++) {
        		manateeImages[i] = BitmapFactory.decodeResource(
        				context.getResources(), manatees[i]);
        		manateeThumbs[i] = Bitmap.createScaledBitmap(manateeImages[i],
        				100, 100, false);
        	}
        }
        
    	public int getCount() {
    		Log.v(TAG, "in getCount()");
    		return manatees.length;
    	}

    	public int getViewTypeCount() {
    		Log.v(TAG, "in getViewTypeCount()");
    		return 1;
    	}
    	
    	public int getItemViewType(int position) {
    		Log.v(TAG, "in getItemViewType() for position " + position);
    		return 0;
    	}

    	public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            
            Log.v(TAG, "in getView for position " + position + 
            		", convertView is " +
            		((convertView == null)?"null":"being recycled"));

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.gridimage, null);
                convertViewCounter++;
                Log.v(TAG, convertViewCounter + " convertViews have been created");

                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.gridImageView);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.image.setImageBitmap(manateeImages[position]);

            return convertView;
        }

    	public Object getItem(int position) {
    		Log.v(TAG, "in getItem() for position " + position);
    		return manateeImages[position];
    	}

    	public long getItemId(int position) {
    		Log.v(TAG, "in getItemId() for position " + position);
    		return position;
    	}
    }
}
