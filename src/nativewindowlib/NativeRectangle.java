package nativewindowlib;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Structure;

public class NativeRectangle extends Structure {

	public int left;
	public int right;
	public int top;
	public int bottom;

	@Override
	protected List<String> getFieldOrder() {
		List<String> list = new ArrayList<String>();
		list.add("left");
		list.add("top");

		list.add("right");
		list.add("bottom");

		return list;
	}
}