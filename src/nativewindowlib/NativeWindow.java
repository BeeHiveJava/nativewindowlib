package nativewindowlib;

import java.awt.Rectangle;
import java.io.File;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/**
 * This class represents a window on the Windows operating system.
 * It provides various functions for getting and setting information.
 * 
 * @author redpois0n
 * @author U2 For Me
 *
 */
public class NativeWindow {

	private int hwnd;
	
	/**
	 * Creates a new NativeWindow with the provides window handle.
	 * 
	 * @param hwnd the handle that this window uses.
	 */
	public NativeWindow(int hwnd) {
		this.hwnd = hwnd;
	}
	
	/**
	 * Destroys this window and all its associated children or owned windows.
	 * 
	 * @return true if, and only if, this operation succeeded.
	 */
	public boolean close() {
		return User32.INSTANCE.DestroyWindow(hwnd);
	}
	
	/**
	 * Closes this window, setting its placement to minimized.
	 * 
	 * @return true if, and only if, this operation succeeded.
	 */
	public boolean minimize() {
		return User32.INSTANCE.CloseWindow(hwnd);
	}
	
	/**
	 * Retrieves the placement of this window and checks if it is equal to {@link WindowConstants#SW_MINIMIZE}.
	 * 
	 * @return true if, and only if, {@link #getWindowPlacement()} equals {@link WindowConstants#SW_MINIMIZE}.
	 */
	public boolean isMinimized() {
		return User32.INSTANCE.IsIconic(hwnd);
	}
	
	/**
	 * Shows this window, settings its placement to maximized.
	 * 
	 * @return true if, and only if, this operation succeeded.
	 */
	public boolean maximize() {
		return User32.INSTANCE.ShowWindow(hwnd, WindowConstants.SW_MAXIMIZE);
	}
	
	/**
	 * Retrieves the placement of this window and checks if it is equal to {@link WindowConstants#SW_MAXIMIZE}.
	 * 
	 * @return true if, and only if, {@link #getWindowPlacement()} equals {@link WindowConstants#SW_MAXIMIZE}.
	 */
	public boolean isMaximized() {
		return User32.INSTANCE.IsZoomed(hwnd);
	}
	
	/**
	 * Brings this window to the front.
	 * 
	 * @return true if, and only if, this operation succeeded.
	 */
	public boolean bringToFront() {
		return User32.INSTANCE.SetForegroundWindow(hwnd);
	}
	
	/**
	 * Shows this window, setting its placement to {@link WindowConstants#SW_HIDE}.
	 * 
	 * @return true if, and only if, this operation succeeded.
	 */
	public boolean hide() {
		return User32.INSTANCE.ShowWindow(hwnd, WindowConstants.SW_HIDE);
	}
	
	/**
	 * Retrieves the placement of this window and checks if it is equal to {@link WindowConstants#SW_HIDE}.
	 * 
	 * @return true if, and only if, {@link #getWindowPlacement()} equals {@link WindowConstants#SW_HIDE}.
	 */
	public boolean isHidden() {
		return !isVisible();
	}
	
	/**
	 * Shows this window, setting its placement to {@link WindowConstants#SW_SHOW}.
	 * 
	 * @return true if, and only if, this operation succeeded.
	 */
	public boolean show() {
		return User32.INSTANCE.ShowWindow(hwnd, WindowConstants.SW_SHOW);
	}
	
	/**
	 * Checks if this window is visible.
	 * 
	 * @return true if, and only if, this window is visible.
	 */
	public boolean isVisible() {
		return User32.INSTANCE.IsWindowVisible(hwnd);
	}
	
	/**
	 * Disables this window so that no input can be sent to it anymore.
	 * 
	 * @return false, if this window was disabled.
	 */
	public boolean disable() {
		return User32.INSTANCE.EnableWindow(hwnd, false);
	}
	
	/**
	 * Checks whether input to this window is blocked.
	 * 
	 * @return true if no input can be sent to this window.
	 */
	public boolean isDisabled() {
		return !User32.INSTANCE.IsWindowEnabled(hwnd);
	}
	
	/**
	 * Enables this window so that input can be sent to it again.
	 * 
	 * @return true, if this window was enabled.
	 */
	public boolean enable() {
		return User32.INSTANCE.EnableWindow(hwnd, true);
	}
	
	/**
	 * Checks whether input can be sent to this window or not.
	 * 
	 * @return true if input can be sent to this window.
	 */
	public boolean isEnabled() {
		return User32.INSTANCE.IsWindowEnabled(hwnd);
	}
	
	/**
	 * Moves, changes the size and repaints this window.
	 * 
	 * @param rect a {@link Rectangle} containing this window's new bounds.
	 * @return true if, and only if, this operation succeeded.
	 */
	public boolean setBounds(Rectangle rect) {
		return User32.INSTANCE.MoveWindow(hwnd, rect.x, rect.y, rect.width, rect.height, true);
	}
	
	/**
	 * Gets the position and size of this window.
	 * 
	 * @return a {@link Rectangle} containing the bounds of this window.
	 */
	public Rectangle getBounds() {
		NativeRectangle rect = new NativeRectangle();
		User32.INSTANCE.GetWindowRect(hwnd, rect);

		return new Rectangle(rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top);
	}
	
	/**
	 * Changes the title of this window <b>ONLY ANSI ENCODING</b>.
	 * 
	 * @param title the new title of this window.
	 * @return true if, and only if, this operation succeeded.
	 */
	public boolean setTitle(String title) {
		return User32.INSTANCE.SetWindowTextA(hwnd, title);
	}
	
	/**
	 * Gets the title of this window <b>ONLY ANSI ENCODING</b>.
	 * 
	 * @return the title of this window.
	 */
	public String getTitle() {
		byte[] buffer = new byte[1024];
		User32.INSTANCE.GetWindowTextA(hwnd, buffer, buffer.length);

		return Native.toString(buffer);
	}
	
	/**
	 * Gets the process attached to this window.
	 * 
	 * @return the process that owns this window.
	 */
	public String getProcess() {
		byte[] buffer = new byte[1024];

		Pointer zero = new Pointer(0);
		IntByReference pid = new IntByReference();
		User32.INSTANCE.GetWindowThreadProcessId(hwnd, pid);

		Pointer ptr = Kernel32.INSTANCE.OpenProcess(1040, false, pid.getValue());
		PsAPI.INSTANCE.GetModuleFileNameExA(ptr, zero, buffer, buffer.length);
		
		return Native.toString(buffer);
	}
	
	/**
	 * Returns the icon of the process that owns this window.
	 * 
	 * @return the process file icon.
	 */
	public Icon getIcon() {
		return FileSystemView.getFileSystemView().getSystemIcon(new File(getProcess()));
	}
	
	/**
	 * An integer representing this window's handle.
	 * 
	 * @return this window's handle.
	 */
	public int getHwnd() {
		return hwnd;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NativeWindow[Hwnd=").append(hwnd);
		builder.append(", Title=").append(getTitle());
		builder.append(", Process=").append(getProcess());
		builder.append(", Minimized=").append(isMinimized());
		builder.append(", Maximized=").append(isMaximized());
		builder.append(", Bounds=").append(getBounds());
		builder.append(", Visible=").append(isVisible());
		builder.append(", Hidden=").append(isHidden());
		builder.append("]");
		
		return builder.toString();
	}

}
