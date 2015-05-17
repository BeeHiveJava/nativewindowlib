package nativewindowlib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class WindowUtils {

	/**
	 * https://stackoverflow.com/questions/3188484/windows-how-to-get-a-list-of-all-visible-windows
	 * 
	 * https://stackoverflow.com/questions/7521693/converting-c-sharp-to-java-jna-getmodulefilename-from-hwnd
	 * 
	 * Gets all window handles on this machine.
	 * 
	 * @return a {@link List} containing all windows.
	 */
	public static List<NativeWindow> getWindows() {
		final List<NativeWindow> inflList = new ArrayList<NativeWindow>();
		final List<Integer> order = new ArrayList<Integer>();

		int top = User32.INSTANCE.GetTopWindow(0);

		while (top != 0) {
			order.add(top);
			top = User32.INSTANCE.GetWindow(top, WindowConstants.GW_HWNDNEXT);
		}

		User32.INSTANCE.EnumWindows(new WindowEnumProcessor() {
			public boolean callback(int hWnd, int lParam) {						
				inflList.add(new NativeWindow(hWnd));
				return true;
			}
		}, 0);

		Collections.sort(inflList, new Comparator<NativeWindow>() {
			public int compare(NativeWindow o1, NativeWindow o2) {
				return order.indexOf(o1.getHwnd()) - order.indexOf(o2.getHwnd());
			}
		});

		return inflList;
	}
	
	/**
	 * Finds all windows and checks which are visible
	 * 
	 * @return all visible windows.
	 */
	public static List<NativeWindow> getVisibleWindows() {
		List<NativeWindow> visible = new ArrayList<NativeWindow>();
		List<NativeWindow> windows = WindowUtils.getWindows();

		for (NativeWindow w : windows) {
			if (w.isVisible()) {
				visible.add(w);
			}
		}
		
		return visible;
	}

	/**
	 * Finds a window with matching title
	 * 
	 * @param title the title to search for
	 * @return the window with the title that has been searched for.
	 */
	public static NativeWindow getByTitle(String title) {
		return new NativeWindow(User32.INSTANCE.FindWindowA(null, title));
	}
	
	/**
	 * Finds the window that is currently active
	 * 
	 * @return the window that is currently active
	 */
	public static NativeWindow getActiveWindow() {
		return new NativeWindow(User32.INSTANCE.GetForegroundWindow());
	}
	
	/**
	 * Prevent instantiation
	 */
	private WindowUtils() {
	}

}