// EditTextPreferenceActivity.java

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class EditTextPreferenceActivity extends PreferenceActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        addPreferencesFromResource(R.xml.packagepref);
    }

}

