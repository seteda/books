package com.androidbook.OpenGL;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class TestOpenGLMainDriverActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){ 
    	super.onCreateOptionsMenu(menu);
 	   	MenuInflater inflater = getMenuInflater(); //from activity
 	   	inflater.inflate(R.menu.main_menu, menu);
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	if (item.getItemId() >= R.id.mid_es20_triangle)
    	{
    		this.invoke20MultiView(item.getItemId());
        	return true;
    	}
    	this.invokeMultiView(item.getItemId());
    	return true;
    }
    private void invokeMultiView(int mid)
    {
		Intent intent = new Intent(this,MultiViewTestHarnessActivity.class);
		intent.putExtra("com.ai.menuid", mid);
		startActivity(intent);
    }
    private void invoke20MultiView(int mid)
    {
		Intent intent = new Intent(this,OpenGL20MultiViewTestHarnessActivity.class);
		intent.putExtra("com.ai.menuid", mid);
		startActivity(intent);
    }
}