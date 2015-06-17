#include <SDKDDKVer.h>
#include <windows.h>
#include <Psapi.h>
#include "nativewindowlib_WindowUtil.h"
#include "ListAdder.h"

BOOL APIENTRY DllMain(HMODULE hModule, DWORD ul_reason_for_call, LPVOID lpReserved) {
	switch (ul_reason_for_call)	{
	case DLL_PROCESS_ATTACH:
	case DLL_THREAD_ATTACH:
	case DLL_THREAD_DETACH:
	case DLL_PROCESS_DETACH:
		break;
	}
	return TRUE;
}

JNIEXPORT jint JNICALL Java_nativewindowlib_WindowUtil_FindWindowByTitle
(JNIEnv *env, jclass, jstring title) {
	return (int)FindWindowA(NULL, env->GetStringUTFChars(title, NULL));
}

JNIEXPORT void JNICALL Java_nativewindowlib_WindowUtil_ShowWindow
(JNIEnv *, jclass, jint hwnd, jint mode) {
	HWND handle = (HWND)hwnd;
	if (handle == 0) {
		return;
	}

	ShowWindow(handle, mode);
}

JNIEXPORT void JNICALL Java_nativewindowlib_WindowUtil_DestroyWindow
(JNIEnv *, jclass, jint hwnd) {
	HWND handle = (HWND)hwnd;
	if (handle == 0) {
		return;
	}

	SendMessage(handle, WM_CLOSE, 0, 0);
}

JNIEXPORT void JNICALL Java_nativewindowlib_WindowUtil_CloseWindow
(JNIEnv *, jclass, jint hwnd) {
	HWND handle = (HWND)hwnd;
	if (handle == 0) {
		return;
	}

	CloseWindow(handle);
}

JNIEXPORT void JNICALL Java_nativewindowlib_WindowUtil_OpenIcon
(JNIEnv *, jclass, jint hwnd) {
	HWND handle = (HWND)hwnd;
	if (handle == 0) {
		return;
	}

	OpenIcon(handle);
}

JNIEXPORT void JNICALL Java_nativewindowlib_WindowUtil_EnableWindow
(JNIEnv *, jclass, jint hwnd, jboolean enabled) {
	HWND handle = (HWND)hwnd;
	if (handle == 0) {
		return;
	}

	EnableWindow(handle, (BOOL)enabled);
}

JNIEXPORT jboolean JNICALL Java_nativewindowlib_WindowUtil_IsWindowEnabled
(JNIEnv *, jclass, jint hwnd) {
	HWND handle = (HWND)hwnd;
	if (handle == 0) {
		return FALSE;
	}

	return (BOOL)IsWindowEnabled(handle);
}

JNIEXPORT jboolean JNICALL Java_nativewindowlib_WindowUtil_IsIconic
(JNIEnv *, jclass, jint hwnd) {
	HWND handle = (HWND)hwnd;
	if (handle == 0) {
		return FALSE;
	}

	return (BOOL)IsIconic(handle);
}

JNIEXPORT jboolean JNICALL Java_nativewindowlib_WindowUtil_IsZoomed
(JNIEnv *, jclass, jint hwnd) {
	HWND handle = (HWND)hwnd;
	if (handle == 0) {
		return FALSE;
	}

	return (BOOL)IsZoomed(handle);
}

JNIEXPORT jboolean JNICALL Java_nativewindowlib_WindowUtil_IsWindowVisible
(JNIEnv *, jclass, jint hwnd) {
	HWND handle = (HWND)hwnd;
	if (handle == 0) {
		return FALSE;
	}

	return (BOOL)IsWindowVisible(handle);
}

JNIEXPORT jboolean JNICALL Java_nativewindowlib_WindowUtil_IsHungAppWindow
(JNIEnv *, jclass, jint hwnd) {
	HWND handle = (HWND)hwnd;
	if (handle == 0) {
		return FALSE;
	}

	return (BOOL)IsHungAppWindow(handle);
}

JNIEXPORT void JNICALL Java_nativewindowlib_WindowUtil_SetForegroundWindow
(JNIEnv *, jclass, jint hwnd) {
	HWND handle = (HWND)hwnd;
	if (handle == 0) {
		return;
	}

	SetForegroundWindow(handle);
}

JNIEXPORT jint JNICALL Java_nativewindowlib_WindowUtil_GetForegroundWindow
(JNIEnv *, jclass) {
	return (int)GetForegroundWindow();
}

JNIEXPORT void JNICALL Java_nativewindowlib_WindowUtil_MoveWindow
(JNIEnv *, jclass, jint hwnd, jint x, jint y, jint width, jint height, jboolean redraw) {
	HWND handle = (HWND)hwnd;
	if (handle == 0) {
		return;
	}

	MoveWindow(handle, x, y, width, height, (BOOL)redraw);
}

JNIEXPORT jobject JNICALL Java_nativewindowlib_WindowUtil_GetWindowRect
(JNIEnv *env, jclass, jint hwnd) {
	HWND handle = (HWND)hwnd;
	if (handle == 0) {
		return NULL;
	}

	RECT rect;
	GetWindowRect(handle, &rect);

	jclass cls = env->FindClass("java/awt/Rectangle");
	jmethodID constructor = env->GetMethodID(cls, "<init>", "(IIII)V");
	jobject rectangle = env->NewObject(cls, constructor, rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top);
	return rectangle;
}

JNIEXPORT void JNICALL Java_nativewindowlib_WindowUtil_SetWindowTextA
(JNIEnv *env, jclass, jint hwnd, jstring text) {
	SetWindowTextA((HWND)hwnd, env->GetStringUTFChars(text, NULL));
}

JNIEXPORT jstring JNICALL Java_nativewindowlib_WindowUtil_GetWindowTextA
(JNIEnv *env, jclass, jint hwnd) {
	HWND handle = (HWND)hwnd;
	if (handle == 0) {
		return NULL;
	}

	int length = GetWindowTextLengthA(handle);
	if (length <= 0) {
		return NULL;
	}

	LPSTR buffer = (LPSTR)malloc(length * sizeof(TCHAR));
	GetWindowTextA(handle, buffer, length + 1);
	jstring title = env->NewStringUTF(buffer);
	free(buffer);

	return title;
}

JNIEXPORT jstring JNICALL Java_nativewindowlib_WindowUtil_GetProcess
(JNIEnv *env, jclass, jint hwnd) {
	HWND handle = (HWND)hwnd;
	if (handle == 0) {
		return NULL;
	}


	LPSTR buffer = (LPSTR)malloc(1024 * sizeof(TCHAR));
	DWORD processId;
	GetWindowThreadProcessId(handle, &processId);
	HANDLE hndl = OpenProcess(1040, FALSE, processId);
	GetModuleFileNameExA(hndl, 0, buffer, 1024);
	jstring process = env->NewStringUTF(buffer);
	free(buffer);

	return process;
}

static BOOL CALLBACK EnumWindowsCallback(__in HWND HWND, __in LPARAM LPARAM) {
	ListAdder* adder = reinterpret_cast<ListAdder*>(LPARAM);
	adder->add(HWND);
	return TRUE;
}

JNIEXPORT jobject JNICALL Java_nativewindowlib_WindowUtil_FindAllWindows
(JNIEnv *env, jclass, jobject list) {
	ListAdder adder(env, list);
	EnumWindows(EnumWindowsCallback, reinterpret_cast<LPARAM>(&adder));
	return list;
}