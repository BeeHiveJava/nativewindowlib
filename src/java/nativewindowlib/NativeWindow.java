package nativewindowlib;

import java.awt.Rectangle;
import java.io.File;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;


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
	 */
	public void close() {
		WindowUtil.DestroyWindow(hwnd);
	}
	
	/**
	 * Closes this window, setting its placement to minimized.
	 * 
	 */
	public void minimize() {
		WindowUtil.CloseWindow(hwnd);
	}
	
	/**
	 * Retrieves the placement of this window and checks if it is equal to {@link WindowConstants#SW_MINIMIZE}.
	 * 
	 * @return true if, and only if, {@link #getWindowPlacement()} equals {@link WindowConstants#SW_MINIMIZE}.
	 */
	public boolean isMinimized() {
		return WindowUtil.IsIconic(hwnd);
	}
	
	/**
	 * Shows this window, settings its placement to maximized.
	 * 
	 */
	public void maximize() {
		WindowUtil.ShowWindow(hwnd, WindowConstants.SW_MAXIMIZE);
	}
	
	/**
	 * Retrieves the placement of this window and checks if it is equal to {@link WindowConstants#SW_MAXIMIZE}.
	 * 
	 * @return true if, and only if, {@link #getWindowPlacement()} equals {@link WindowConstants#SW_MAXIMIZE}.
	 */
	public boolean isMaximized() {
		return WindowUtil.IsZoomed(hwnd);
	}
	
	/**
	 * Brings this window to the front.
	 * 
	 */
	public void bringToFront() {
		WindowUtil.SetForegroundWindow(hwnd);
	}
	
	/**
	 * Retrieves the current active window handle and checks if it is equal to this window's handle.
	 * 
	 * @return true if this window is active.
	 */
	public boolean isActive() {
		return WindowUtil.GetForegroundWindow() == hwnd;
	}
	
	/**
	 * Shows this window, setting its placement to {@link WindowConstants#SW_HIDE}.
	 * 
	 */
	public void hide() {
		WindowUtil.ShowWindow(hwnd, WindowConstants.SW_HIDE);
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
	 */
	public void show() {
		WindowUtil.ShowWindow(hwnd, WindowConstants.SW_SHOW);
	}
	
	/**
	 * Checks if this window is visible.
	 * 
	 * @return true if, and only if, this window is visible.
	 */
	public boolean isVisible() {
		return WindowUtil.IsWindowVisible(hwnd);
	}
	
	/**
	 * Restores this window, if it was minimized, setting its previous size and position, it then activates this window.
	 * 
	 */
	public void restore() {
		WindowUtil.OpenIcon(hwnd);
	}
	
	/**
	 * Disables this window so that no input can be sent to it anymore.
	 * 
	 */
	public void disable() {
		WindowUtil.EnableWindow(hwnd, false);
	}
	
	/**
	 * Checks whether input to this window is blocked.
	 * 
	 * @return true if no input can be sent to this window.
	 */
	public boolean isDisabled() {
		return !WindowUtil.IsWindowEnabled(hwnd);
	}
	
	/**
	 * Enables this window so that input can be sent to it again.
	 * 
	 */
	public void enable() {
		WindowUtil.EnableWindow(hwnd, true);
	}
	
	/**
	 * Checks whether input can be sent to this window or not.
	 * 
	 * @return true if input can be sent to this window.
	 */
	public boolean isEnabled() {
		return WindowUtil.IsWindowEnabled(hwnd);
	}
	
	/**
	 * Moves, changes the size and repaints this window.
	 * 
	 * @param rect a {@link Rectangle} containing this window's new bounds.
	 */
	public void setBounds(Rectangle rect) {
		WindowUtil.MoveWindow(hwnd, rect.x, rect.y, rect.width, rect.height, true);
	}
	
	/**
	 * Gets the position and size of this window.
	 * 
	 * @return a {@link Rectangle} containing the bounds of this window.
	 */
	public Rectangle getBounds() {
		return WindowUtil.GetWindowRect(hwnd);
	}
	
	/**
	 * Changes the title of this window <b>ONLY ANSI ENCODING</b>.
	 * 
	 * @param title the new title of this window.
	 */
	public void setTitle(String title) {
		WindowUtil.SetWindowTextA(hwnd, title);
	}
	
	/**
	 * Gets the title of this window <b>ONLY ANSI ENCODING</b>.
	 * 
	 * @return the title of this window.
	 */
	public String getTitle() {
		return WindowUtil.GetWindowTextA(hwnd);
	}
	
	/**
	 * Determines whether the system considers that a specified application is not responding.
	 * An application is considered to be not responding if it is not waiting for input,
	 * is not in startup processing, and has not called PeekMessage within the
	 * internal timeout period of 5 seconds.
	 * 
	 * @return true if, and only if, this window is responding.
	 */
	public boolean isResponding() {
		return !WindowUtil.IsHungAppWindow(hwnd);
	}
	
	/**
	 * Gets the process attached to this window.
	 * 
	 * @return the process that owns this window.
	 */
	public String getProcess() {
		return WindowUtil.GetProcess(hwnd);
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
		builder.append(", Responding=").append(isResponding());
		builder.append(", Minimized=").append(isMinimized());
		builder.append(", Maximized=").append(isMaximized());
		builder.append(", Bounds=").append(getBounds());
		builder.append(", Visible=").append(isVisible());
		builder.append(", Hidden=").append(isHidden());
		builder.append("]");
		
		return builder.toString();
	}

}
