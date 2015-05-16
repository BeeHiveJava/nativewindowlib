package nativewindowlib;

import com.sun.jna.win32.StdCallLibrary;

interface WindowEnumProcessor extends StdCallLibrary.StdCallCallback {

	public abstract boolean callback(int hWnd, int lParam);
	
}
