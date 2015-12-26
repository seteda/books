package ch04.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class AsteroidsActivity extends Activity {
	View view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LayoutInflater factory = LayoutInflater.from(this);
        view = factory.inflate(R.layout.asteroids, null);
        setContentView(view);
        
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
        
	}

    
    @Override
    protected void onStop() {
    	super.onStop();
    	((ArcadeGame)view).halt();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	onStop();
    }
    
    @Override
    protected void onRestart() {
    	super.onRestart();
    	((ArcadeGame)view).resume();
    }
    
}
