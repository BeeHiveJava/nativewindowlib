#pragma once
class ListAdder {
public:
	ListAdder(JNIEnv *env, jobject list);
	void add(HWND hwnd);

private:
	JNIEnv *env;
	jobject list;
	jclass cls;
	jmethodID method;

	jobject NewInteger(jint value);
};