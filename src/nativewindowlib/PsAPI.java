package nativewindowlib;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;

interface PsAPI extends StdCallLibrary {
	
	static final PsAPI INSTANCE = (PsAPI) Native.loadLibrary("psapi", PsAPI.class);

	int GetModuleFileNameExA(Pointer process, Pointer hModule, byte[] lpString, int nMaxCount);

};
