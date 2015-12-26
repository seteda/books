package game.controller;

import android.view.KeyEvent;

public interface ControllerListener {
	
	/**
	 * Button maps to {@link KeyEvent} Android keys!
	 * @param btnCode Android Key code: UP,DOWN,LEFT,RIGHT,X,Y,A,B,ENTER,BACK
	 */
	public void ControllerUp(int btnCode);
	public void ControllerDown(int btnCode);
	
}
