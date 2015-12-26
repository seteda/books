package wolf.util;

import game.wolfsw.R;
import game.wolfsw.WolfLauncher;
import game.wolfsw.WolfLauncher.eNavMethod;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/*******************************************************************************
 * Copyright (c) 2006 Vladimir Silva and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Vladimir Silva - initial API and implementation
 *******************************************************************************/

public class DialogTool 
{

	/**
	 * Select a nav method dialog
	 * @param ctx
	 */
	static public void showNavMethodDialog(final Context ctx) { //, final SensorManager mSensorManager, final SensorListener mSensor) {
		final String[] mListItems = new String[] {"Keyboard: 1AQW, Shift=Run, Alt=Strafe", "Game Pad" };
		
		AlertDialog dialog = new AlertDialog.Builder(ctx)
	        .setIcon(R.drawable.icon)
	        .setTitle("Navigation Method")
	        .setItems(mListItems, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    final View v = ((Activity)ctx).findViewById(R.id.snes);
                    //final View v1 = ((Activity)ctx).findViewById(R.id.other_ctls);
                    
                    boolean port = ((WolfLauncher)ctx).isPortrait();
                    
                    switch (which) {
                    	case 0:
                    		// KBD Hide ctls
	                		v.setVisibility(View.GONE);
	                		WolfLauncher.mNavMethod = eNavMethod.KBD;
	                		
                    		break;
                    		
						case 1:
	                		// Panel Arrows
	                		// Unregister accelerometer
//	                		if ( mSensorManager != null)
//	                			mSensorManager.unregisterListener(mSensor);
	                		
	                		if ( !port ) {
	                			Toast(ctx, "Try in portrait mode");
	                			return;
	                		}
	                		
	                		WolfLauncher.mNavMethod = eNavMethod.PANEL;
	                		
	                		// show panel ctls
	                		v.setVisibility(View.VISIBLE);
	                		break;
/*	                		
	                	case 2:
	                		// Accelerometer
	            			// TODO Sensor sim test (remove this)
	            			// now connect to simulator 
	            			//org.openintents.hardware.SensorManagerSimulator.connectSimulator(); 
	            			
	            			// now enable the new sensors
	            			if (!mSensorManager.registerListener(mSensor, SensorManager.SENSOR_ACCELEROMETER)) {
	            				MessageBox(ctx, "Unable to register the accelerometer.");
	            				return;
	            			}
	            			
	            			WolfLauncher.mNavMethod = eNavMethod.ACC;
	            			
	            			// Hide arrows
	            			v.setVisibility(View.GONE);
	            			
	            			Toast(ctx, "Pitch controls Left/Right. Roll controls Up/Down");
	                		
	                		break;
*/	                		
					}
                }
            })
            .create();
		dialog.show();
	}
	
	/**
	 * Load spinner
	 * @param ctx
	 * @param spinner
	 * @param resID
	 * @param idx
	 */
	public static void loadSpinner(Context ctx, Spinner spinner, int resID, int idx) {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ctx, resID
				, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(idx);
	}
	
	/**
	 * MessageBox
	 * @param ctx
	 * @param text
	 */
	public static void MessageBox (Context ctx, String text) {
		MessageBox(ctx, ctx.getString(game.wolfsw.R.string.app_name), text);
	}

	/**
	 * Message box
	 * @param text
	 */
	
	public static  void MessageBox (Context ctx, String title, String text) {
		AlertDialog d = createAlertDialog(ctx
				, title
				, text);
			
		d.setButton("Dismiss", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                /* User clicked OK so do some stuff */
            }
        });
		d.show();
	}
	
	/**
	 * Messagebox
	 * @param ctx
	 * @param text
	 */
	public static void PostMessageBox (final Context ctx, final String text) {
		WolfLauncher.mHandler.post(new Runnable() {
			public void run() {
				MessageBox(ctx, ctx.getString(R.string.app_name), text);
			}
		});
	}
	
    /**
     * Create an alert dialog
     * @param ctx App context
     * @param message Message
     * @return
     */
    static public AlertDialog createAlertDialog (Context ctx, String title, String message) {
        return new AlertDialog.Builder(ctx)
	        .setIcon(game.wolfsw.R.drawable.icon)
	        .setTitle(title)
	        .setMessage(message)
	        .create();
    }

    static public AlertDialog createAlertDialog (Context ctx, String title, String message, View view) {
        return new AlertDialog.Builder(ctx)
	        .setIcon(game.wolfsw.R.drawable.icon)
	        .setTitle(title)
	        .setView(view)
	        .setMessage(message)
	        .create();
    }
    
    /**
     * Launch web browser
     */
    static public void launchBrowser(Context ctx, String url) {
    	ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));     	
    }
	
    public static void Toast( final Context ctx, final String text) {
	    Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
    }

    public static void Toast( Handler handler ,final Context ctx, final String text) {
    	handler.post(new Runnable() {
			public void run() {
				Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
			}
		});
    }
    
}
