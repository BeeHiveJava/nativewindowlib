package nativewindowlib;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;

interface Kernel32 extends StdCallLibrary {
	
	static final Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);

	Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, int dwProcessId);

	int GetTickCount();
	
};
