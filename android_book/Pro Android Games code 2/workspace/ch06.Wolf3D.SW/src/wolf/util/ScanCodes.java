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

package wolf.util;

import game.wolfsw.WolfLauncher;
import game.wolfsw.WolfLauncher.eNavMethod;
import android.view.KeyEvent;

public class ScanCodes {

	public static final int 	sc_None			= 0;
	public static final int 	sc_Bad			= 0xff;
	public static final int 	sc_Return		= 0x1c;
	public static final int 	sc_Enter		= sc_Return;
	public static final int 	sc_Escape		= 0x01;
	public static final int 	sc_Space		= 0x39;
	public static final int 	sc_BackSpace		= 0x0e;
	public static final int 	sc_Tab			= 0x0f;
	public static final int 	sc_Alt			= 0x38;
	public static final int 	sc_Control		= 0x1d;
	public static final int 	sc_CapsLock		= 0x3a;
	public static final int 	sc_LShift		= 0x2a;
	public static final int 	sc_RShift		= 0x36;

	public static final int 	sc_UpArrow		= 0x48;
	public static final int 	sc_DownArrow		= 0x50;
	public static final int 	sc_LeftArrow		= 0x4b;
	public static final int 	sc_RightArrow		= 0x4d;
	public static final int 	sc_Home			= 0x47;
	public static final int 	sc_End			= 0x4f;
	public static final int 	sc_PgUp			= 0x49;
	public static final int 	sc_PgDn			= 0x51;

	public static final int 	sc_Insert		= 0x52;
	public static final int 	sc_Delete		= 0x53;

	public static final int 	sc_1			= 0x02;
	public static final int 	sc_2			= 0x03;
	public static final int 	sc_3			= 0x04;
	public static final int 	sc_4			= 0x05;
	public static final int 	sc_5			= 0x06;
	public static final int 	sc_6			= 0x07;
	public static final int 	sc_7			= 0x08;
	public static final int 	sc_8			= 0x09;
	public static final int 	sc_9			= 0x0a;
	public static final int 	sc_0			= 0x0b;

	public static final int 	sc_A			= 0x1e;
	public static final int 	sc_B			= 0x30;
	public static final int 	sc_C			= 0x2e;
	public static final int 	sc_D			= 0x20;
	public static final int 	sc_E			= 0x12;
	public static final int 	sc_F			= 0x21;
	public static final int 	sc_G			= 0x22;
	public static final int 	sc_H			= 0x23;
	public static final int 	sc_I			= 0x17;
	public static final int 	sc_J			= 0x24;
	public static final int 	sc_K			= 0x25;
	public static final int 	sc_L			= 0x26;
	public static final int 	sc_M			= 0x32;
	public static final int 	sc_N			= 0x31;
	public static final int 	sc_O			= 0x18;
	public static final int 	sc_P			= 0x19;
	public static final int 	sc_Q			= 0x10;
	public static final int 	sc_R			= 0x13;
	public static final int 	sc_S			= 0x1f;
	public static final int 	sc_T			= 0x14;
	public static final int 	sc_U			= 0x16;
	public static final int 	sc_V			= 0x2f;
	public static final int 	sc_W			= 0x11;
	public static final int 	sc_X			= 0x2d;
	public static final int 	sc_Y			= 0x15;
	public static final int 	sc_Z			= 0x2c;

	public static final int 	key_None		= 0;
	
	public static int keySymToScancode(int keysym)
	{
		switch (keysym) {
			case KeyEvent.KEYCODE_1: 
				return ( WolfLauncher.mNavMethod == eNavMethod.KBD) ? sc_UpArrow : sc_1;
			case KeyEvent.KEYCODE_2:
				return sc_2;
			case KeyEvent.KEYCODE_3:
				return sc_3;
			case KeyEvent.KEYCODE_4:
				return sc_4;
			case KeyEvent.KEYCODE_5:
				return sc_5;
			case KeyEvent.KEYCODE_6:
				return sc_6;
			case KeyEvent.KEYCODE_7:
				return sc_7;
			case KeyEvent.KEYCODE_8:
				return sc_8;
			case KeyEvent.KEYCODE_9:
				return sc_9;
			case KeyEvent.KEYCODE_0:
				return sc_0;
				
			case KeyEvent.KEYCODE_A:
				return ( WolfLauncher.mNavMethod == eNavMethod.KBD) ? sc_DownArrow : sc_A;

				//return sc_A;
			case KeyEvent.KEYCODE_B:
				return sc_B;
			case KeyEvent.KEYCODE_C:
				return sc_C;
			case KeyEvent.KEYCODE_D:
				return sc_D;
			case KeyEvent.KEYCODE_E:
				return sc_E;
			case KeyEvent.KEYCODE_F:
				return sc_F;
			case KeyEvent.KEYCODE_G:
				return sc_G;
			case KeyEvent.KEYCODE_H:
				return sc_H;
			case KeyEvent.KEYCODE_I:
				return sc_I;
			case KeyEvent.KEYCODE_J:
				return sc_J;
			case KeyEvent.KEYCODE_K:
				return sc_K;
			case KeyEvent.KEYCODE_L:
				return sc_L;
			case KeyEvent.KEYCODE_M:
				return sc_M;
			case KeyEvent.KEYCODE_N:
				return sc_N;
			case KeyEvent.KEYCODE_O:
				return sc_O;
			case KeyEvent.KEYCODE_P:
				return sc_P;
			case KeyEvent.KEYCODE_Q:
				return ( WolfLauncher.mNavMethod == eNavMethod.KBD) ? sc_LeftArrow : sc_Q;

				//return sc_Q;
			case KeyEvent.KEYCODE_R:
				return sc_R;
			case KeyEvent.KEYCODE_S:
				return sc_S;
			case KeyEvent.KEYCODE_T:
				return sc_T;
			case KeyEvent.KEYCODE_U:
				return sc_U;
			case KeyEvent.KEYCODE_V:
				return sc_V;
				
			case KeyEvent.KEYCODE_W:
				return ( WolfLauncher.mNavMethod == eNavMethod.KBD) ? sc_RightArrow : sc_W;

				//return sc_W;
			case KeyEvent.KEYCODE_X:
				return sc_X;
			case KeyEvent.KEYCODE_Y:
				return sc_Y;
			case KeyEvent.KEYCODE_Z:
				return sc_Z;
				
			case KeyEvent.KEYCODE_DPAD_LEFT:
				return sc_LeftArrow;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				return sc_RightArrow;
			case KeyEvent.KEYCODE_DPAD_UP:
				return sc_UpArrow;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				return sc_DownArrow;
				
//			case KeyEvent.KEYCODE_Control_L:
//			case KeyEvent.KEYCODE_Control_R:
//				return sc_Control;

			// Running
			case KeyEvent.KEYCODE_SHIFT_LEFT:
			case KeyEvent.KEYCODE_SHIFT_RIGHT:
				return sc_RShift;
				
			// Strafing
			case KeyEvent.KEYCODE_ALT_LEFT:
			case KeyEvent.KEYCODE_ALT_RIGHT:
				return sc_Alt;
				
				
			// ESC, Back
			case KeyEvent.KEYCODE_BACK:
				return sc_Escape;
				
			case KeyEvent.KEYCODE_SPACE:
				return sc_Space;
				
			case KeyEvent.KEYCODE_ENTER:
			case 23:
				return sc_Enter;
				
			case KeyEvent.KEYCODE_TAB:
				return sc_Tab;
				
			case KeyEvent.KEYCODE_DEL:
				return sc_BackSpace;
				
			default:
				// Fire by default
				return sc_Control;
		}
	}
	
	
	/**********************************************************************
	 * ASCII key symbols
	 **********************************************************************/
	public static final int KEY_RIGHTARROW	= 131;
	public static final int KEY_LEFTARROW	= 130;
	public static final int KEY_UPARROW		= 128;
	public static final int KEY_DOWNARROW	= 129;
	
	public static final int KEY_TAB			= 9;
	public static final int KEY_ENTER		= 13;
	public static final int KEY_ESCAPE		= 27;
	public static final int KEY_SPACE		= 32;
	public static final int KEY_COMMA		= 44;
	public static final int KEY_PERIOD		= 46;

	public static final int KEY_BACKSPACE	= 127;

	public static final int KEY_EQUALS		= 0x3d;
	public static final int KEY_MINUS		= 0x2d;

	public static final int KEY_RALT		= 132;
	public static final int KEY_RCTRL		= 133;
	public static final int KEY_RSHIFT		= 134;

	public static final int KEY_LALT		= KEY_RALT;
	public static final int KEY_PAUSE		= 255;
	

	/**
	 * Convert an android key to an ASCII symbol
	 * @param key
	 * @return
	 */
	static public int keyCodeToKeySym( int key ) {
		switch (key) {
		
		// Left
		case 84: // SYM
		case KeyEvent.KEYCODE_DPAD_LEFT:
			return KEY_LEFTARROW;
		
		// Right
		case KeyEvent.KEYCODE_AT:	
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			return KEY_RIGHTARROW;
		
		// Up
		case KeyEvent.KEYCODE_SHIFT_LEFT:	
		case KeyEvent.KEYCODE_DPAD_UP:
			return KEY_UPARROW;
		
		// Down
		case KeyEvent.KEYCODE_ALT_LEFT:
		case KeyEvent.KEYCODE_DPAD_DOWN:
			return KEY_DOWNARROW;
			
		case 23: // DPAD
		case KeyEvent.KEYCODE_ENTER:
			return KEY_ENTER;
			
		case KeyEvent.KEYCODE_SPACE:
			return KEY_SPACE;

		case 4:	// ESC
			return KEY_ESCAPE;
		
		// Doom Map
		case KeyEvent.KEYCODE_ALT_RIGHT:
		case KeyEvent.KEYCODE_TAB:	
			return KEY_TAB;
		
		// Strafe left
		case KeyEvent.KEYCODE_COMMA:
			return KEY_COMMA;

		// Strafe right
		case KeyEvent.KEYCODE_PERIOD:
			return KEY_PERIOD;

		case KeyEvent.KEYCODE_DEL:
			return KEY_BACKSPACE;
			
		default:
			// A..Z
	  		if (key >= 29 && key <= 54) {
	  			key += 68;
	  		}
	    	// 0..9
	  		else if (key >= 7 && key <= 16) {
	  			key += 41;
	  		}
	  		else {
	  			// Fire
	  			key = KEY_RCTRL;
	  		}
			break;
		}
		return key;
	}
	
	
}
