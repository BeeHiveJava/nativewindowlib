# Deprecated
Check out this respository([here] (https://github.com/redpois0n/nativewindowlib)) for a much better solution using Java and C. Working for both Linux and Windows.

# nativewindowlib

A library to work with native windows on the Windows operating system. It was first started by redpois0n, I decided to continue on it and add more features. I also removed the JNA dependency and made it run using JNI. Let's hope we can merge the two repositories in the future ;). I aimed this to be as simple as possible, as many people struggle using native code with Java. Just add this jar to your classpath and start using it!

# Download
The download link can be found [here] (https://github.com/U2ForMeJava/nativewindowlib/releases/download/1.0/1.0.jar).

## Examples

Loop through windows
```java
List<NativeWindow> windows = WindowUtil.getVisibleWindows();
		    
for (NativeWindow window : windows) {
	System.out.println(window.isVisible());	
}		    
```

Get active window
```java
NativeWindow window = WindowUtil.getActiveWindow();		    
```

Get window by title
```java
NativeWindow window = WindowUtil.getWindowByTitle("Notepad");		    
```

Minimizing, maximizing, closing, visibility, foreground, enabling, location and size, title
```java
NativeWindow window = WindowUtil.getActiveWindow();
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
NativeWindow window = WindowUtil.getActiveWindow();
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
window.getHwnd();
```
