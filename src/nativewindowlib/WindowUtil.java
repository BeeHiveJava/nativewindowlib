package nativewindowlib;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class WindowUtil {

	static {
		String os = System.getProperty("os.name");
		if (!os.toLowerCase().contains("windows")) {
			throw new RuntimeException("Unsupported OS: " + os);
		}

		boolean is64bit = System.getenv("ProgramFiles(x86)") != null;

		try {
			File file = File.createTempFile("nativewindowlib", ".tmp");
			FileOutputStream out = new FileOutputStream(file);

			byte[] buffer = new byte[4096];
			InputStream in = WindowUtil.class.getResourceAsStream("/nativewindowlib/lib/NativeWindowLib" + (is64bit ? "64" : "32") + ".dll");
			if (in == null) {
				out.close();
				file.delete();
				throw new IOException("Unable to locate native library.");
			}

			int read;
			while((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			
			out.close();
			in.close();
			file.deleteOnExit();
			System.load(file.getPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<NativeWindow> getAllWindows() {
		List<Integer> list = FindAllWindows(new ArrayList<Integer>());
		List<NativeWindow> windows = new ArrayList<NativeWindow>();
		
		for (Integer i : list) {
			windows.add(new NativeWindow(i));
		}

		return Collections.unmodifiableList(windows);
	}

	public static List<NativeWindow> getVisibleWindows() {
		List<NativeWindow> visible = new ArrayList<NativeWindow>();
		List<NativeWindow> windows = getAllWindows();

		for (NativeWindow w : windows) {
			String title = w.getTitle();
			if (w.isVisible() && title != null && title.length() > 0) {
				visible.add(w);
			}
		}

		return Collections.unmodifiableList(visible);
	}

	public static NativeWindow getWindowByTitle(String title) {
		return new NativeWindow(FindWindowByTitle(title));
	}

	public static NativeWindow getActiveWindow() {
		return new NativeWindow(GetForegroundWindow());
	}

	private static native int FindWindowByTitle(String title);

	private static native List<Integer> FindAllWindows(List<Integer> list);
	

	static native void ShowWindow(int hwnd, int mode);

	static native void DestroyWindow(int hwnd);

	static native void CloseWindow(int hwnd);

	static native void OpenIcon(int hwnd);

	static native void EnableWindow(int hwnd, boolean enabled);
	

	static native boolean IsWindowEnabled(int hwnd);

	static native boolean IsIconic(int hwnd);

	static native boolean IsZoomed(int hwnd);

	static native boolean IsWindowVisible(int hwnd);

	static native boolean IsHungAppWindow(int hwnd);
	

	static native void SetForegroundWindow(int hwnd);

	static native int GetForegroundWindow();
	

	static native void MoveWindow(int hwnd, int x, int y, int width, int height, boolean redraw);

	static native Rectangle GetWindowRect(int hwnd);
	

	static native void SetWindowTextA(int hwnd, String text);

	static native String GetWindowTextA(int hWnd);

	static native String GetProcess(int hwnd);
	

	private WindowUtil() {
	}

}
