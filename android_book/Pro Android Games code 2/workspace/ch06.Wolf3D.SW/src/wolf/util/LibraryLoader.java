package wolf.util;

import android.util.Log;

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

public class LibraryLoader {

	static final String TAG = "LibLoader";
	
	static public void load (String name) {
		final String LD_PATH = System.getProperty("java.library.path");
		
		Log.d(TAG, "Trying to load library " + name + " from LD_PATH: " + LD_PATH);
		
		try {
			System.loadLibrary(name);
		} catch (UnsatisfiedLinkError e) {
			Log.e(TAG, e.toString());
		}
		
//		Log.d(TAG, "Trying to load " + name + " using its absolute path.");
//		System.load(name);
	}
}
