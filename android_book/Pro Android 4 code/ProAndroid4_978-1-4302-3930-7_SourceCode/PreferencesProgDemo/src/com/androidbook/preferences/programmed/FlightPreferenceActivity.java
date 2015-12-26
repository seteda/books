package com.androidbook.preferences.programmed;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

/*
 *  This sample exhibits two concepts:
 *      - dynamic specification of entry text and entry values,
 *      - dynamic update of the summary text of the preference
 *  
 *  For simplicity, we're using static values instead of pulling values
 *  from a database, but you could certainly choose to pull your text
 *  and values from wherever you want. Keep in mind that your selected
 *  preference value will be stored away in a preferences file on the
 *  device, so whatever the user has stored must still be that preference
 *  even if the rest of the preference choices change in some way.
 *  
 *  The other thing to note is that the OnSharedPreferenceChangeListener
 *  can be garbage collected unless there's a solid reference to it. In
 *  our case, we're using the preference activity itself. Do not use an
 *  anonymous inner class to create the listener; this won't work. See
 *  http://stackoverflow.com/questions/2542938/sharedpreferences-onsharedpreferencechangelistener-not-being-called-consistently/
 *  for more details.
 */

public class FlightPreferenceActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener
{
	ListPreference listpref;
    public final static String[] optionText = getOptionText();
    public final static String[] optionValues = getOptionValues();

    static String[] getOptionText() {
    	return new String[] {"Food", "Lounge", "Frequent Flier Program"};
    }

    static String[] getOptionValues() {
    	return new String[] {"0", "1", "2"};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.flightoptions);
        listpref = (ListPreference)findPreference("selected_flight_sort_option");

        listpref.setEntryValues(optionValues);
        listpref.setEntries(optionText);
    }

    @Override
    protected void onResume() {
    	super.onResume();
    	getPreferenceScreen().getSharedPreferences()
            .registerOnSharedPreferenceChangeListener(this);
    	setSummary();
    }

    @Override
    protected void onPause() {
    	super.onPause();
    	getPreferenceScreen().getSharedPreferences()
           .unregisterOnSharedPreferenceChangeListener(this);
    }

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if("selected_flight_sort_option".equals(key)) {
			setSummary();
		}
	}

	private void setSummary() {
		listpref.setSummary(optionText[Integer.valueOf(listpref.getValue())]);
	}
}
