package com.androidbook.fragments.bard;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
	
	private int mIndex = 0;

	public static DetailsFragment newInstance(int index) {
        Log.v(MainActivity.TAG, "in DetailsFragment newInstance(" + index + ")");

        DetailsFragment df = new DetailsFragment();

        // Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		df.setArguments(args);
		return df;
	}
	
	public static DetailsFragment newInstance(Bundle bundle) {
		int index = bundle.getInt("index", 0);
        return newInstance(index);
	}

    @Override
    public void onInflate(AttributeSet attrs, Bundle savedInstanceState) {
    	Log.v(MainActivity.TAG,
    			"in DetailsFragment onInflate. AttributeSet contains:");
    	for(int i=0; i<attrs.getAttributeCount(); i++)
            Log.v(MainActivity.TAG, "    " + attrs.getAttributeName(i) +
            		" = " + attrs.getAttributeValue(i));
    	super.onInflate(attrs, savedInstanceState);
    }

	@Override
    public void onAttach(Activity myActivity) {
    	Log.v(MainActivity.TAG, "in DetailsFragment onAttach; activity is: " +
    			myActivity);
    	super.onAttach(myActivity);
    }

    @Override
    public void onCreate(Bundle myBundle) {
    	Log.v(MainActivity.TAG, "in DetailsFragment onCreate. Bundle contains:");
    	if(myBundle != null) {
            for(String key : myBundle.keySet()) {
                Log.v(MainActivity.TAG, "    " + key);
            }
    	}
    	else {
            Log.v(MainActivity.TAG, "    myBundle is null");
    	}
    	super.onCreate(myBundle);

    	mIndex = getArguments().getInt("index", 0);
    }

    public int getShownIndex() {
    	return mIndex;
    }

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
        Log.v(MainActivity.TAG, "in DetailsFragment onCreateView. container = " +
        		container);

        // Don't tie this fragment to anything through the inflater. Android
        // takes care of attaching fragments for us. The container is only
        // passed in so we can know about the container where this View
        // hierarchy is going to go.
		View v = inflater.inflate(R.layout.details, container, false);
		TextView text1 = (TextView) v.findViewById(R.id.text1);
		text1.setText(Shakespeare.DIALOGUE[ mIndex ] );
		return v;
	}

    @Override
    public void onActivityCreated(Bundle savedState) {
    	Log.v(MainActivity.TAG,
    			"in DetailsFragment onActivityCreated. savedState contains:");
    	if(savedState != null) {
            for(String key : savedState.keySet()) {
                Log.v(MainActivity.TAG, "    " + key);
            }
    	}
    	else {
            Log.v(MainActivity.TAG, "    savedState is null");
    	}
        super.onActivityCreated(savedState);
    }

    @Override
    public void onStart() {
    	Log.v(MainActivity.TAG, "in DetailsFragment onStart");
    	super.onStart();
    }

    @Override
    public void onResume() {
    	Log.v(MainActivity.TAG, "in DetailsFragment onResume");
    	super.onResume();
    }

    @Override
    public void onPause() {
    	Log.v(MainActivity.TAG, "in DetailsFragment onPause");
    	super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    	Log.v(MainActivity.TAG, "in DetailsFragment onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
    	Log.v(MainActivity.TAG, "in DetailsFragment onStop");
    	super.onStop();
    }

    @Override
    public void onDestroyView() {
    	Log.v(MainActivity.TAG, "in DetailsFragment onDestroyView, view = " +
    			getView());
    	super.onDestroyView();
    }

    @Override
    public void onDestroy() {
    	Log.v(MainActivity.TAG, "in DetailsFragment onDestroy");
    	super.onDestroy();
    }

    @Override
    public void onDetach() {
    	Log.v(MainActivity.TAG, "in DetailsFragment onDetach");
    	super.onDetach();
    }
}