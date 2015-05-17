package nativewindowlib;

import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

interface User32 extends StdCallLibrary {

	static final User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

	boolean EnumWindows(WindowEnumProcessor wndenumproc, int lParam);

	int FindWindowA(String lpClassName, String lpWindowName);
	
	
	boolean IsWindowVisible(int hWnd);
	
	boolean ShowWindow(int hWnd, int nCmdShow);
	
	boolean IsWindowEnabled(int hWnd);
	
	boolean EnableWindow(int hWnd, boolean enable);
	
	boolean IsZoomed(int hWnd);
	
	boolean IsIconic(int hWnd);
	
	boolean CloseWindow(int hWnd);

	boolean DestroyWindow(int hWnd);
	

	boolean MoveWindow(int hWnd, int X, int Y, int nWidth, int nHeight, boolean bRepaint);

	int GetWindowRect(int hWnd, NativeRectangle r);
	

	boolean SetWindowTextA(int hWnd, String text);

	void GetWindowTextA(int hWnd, byte[] buffer, int buflen);


	int GetWindowThreadProcessId(int hWnd);
	
	int GetWindowThreadProcessId(int hWnd, IntByReference pid);
	

	int GetTopWindow(int hWnd);

	int GetWindow(int hWnd, int flag);


	boolean SetForegroundWindow(int hWnd);
	
	int GetForegroundWindow();

	
}