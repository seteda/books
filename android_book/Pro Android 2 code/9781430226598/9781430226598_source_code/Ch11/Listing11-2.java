// This file is MainActivity.java
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView tv = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tv = (TextView)findViewById(R.id.text1);
        
        setOptionText();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.mainmenu, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
       if (item.getItemId() == R.id.menu_prefs)
       {
           Intent intent = new Intent()
           	    	    .setClass(this, com.syh.FlightPreferenceActivity.class);
           this.startActivityForResult(intent, 0);
       }
       else if (item.getItemId() == R.id.menu_quit)
       {
            finish();
       }
       return true;
    }

    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data)
    {
        super.onActivityResult(reqCode, resCode, data);
        setOptionText();
    }
    
    private void setOptionText()
    {
        SharedPreferences prefs = getSharedPreferences("com.syh_preferences", 0);
        String option = prefs.getString(
        	    	    this.getResources().getString(R.string.selected_flight_sort_option),
        	    	    this.getResources().getString(R.string.flight_sort_option_default_value));
        String[] optionText = this.getResources().getStringArray(R.array.flight_sort_options);
    	
        tv.setText("option value is " + option + " (" + 
        	    	    optionText[Integer.parseInt(option)] + ")");
    }
}

