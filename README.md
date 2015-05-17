# nativewindowlib

A library to work with native windows on the Windows operating system. It was first started by redpois0n, I decided to continue on it and add more features. Let's hope we can merge the two repositories in the future ;).

## Dependencies

- [jna](https://github.com/twall/jna)

## Examples

Loop through windows
```java
List<NativeWindow> windows = WindowUtils.getVisibleWindows();
		    
for (NativeWindow window : windows) {
	if (window.getTitle().length() > 0) {
		addToList(window);
	}	
}		    
```

Get active window
```java
NativeWindow window = WindowUtils.getActiveWindow();		    
```

Get window by title
```java
NativeWindow window = WindowUtils.getByTitle("Notepad");		    
```

Minimizing, maximizing, closing, visibility, foreground, enabling, location and size, title
```java
NativeWindow window = WindowUtils.getActiveWindow();
window.maximize();
window.minimize();
window.hide();
window.show();
window.bringToFront();
window.disable();
window.enable();
window.setBounds(new Rectangle(500, 500, 600, 500));
window.setTitle("New title");
window.close();
```

Getting information about the window
```java
NativeWindow window = WindowUtils.getActiveWindow();
window.isMinimized();
window.isMaximized();
window.isActive();
window.isHidden();
window.isVisible();
window.isDisabled();
window.isEnabled();
window.getBounds();
window.getTitle();
window.getProcess();
window.getTitle();
window.getHwnd();
```
