#include <jni.h>
#include <Windows.h>
#include "ListAdder.h"

ListAdder::ListAdder(JNIEnv *env, jobject list) {
	this->env = env;
	this->list = list;

	this->cls = env->GetObjectClass(list);
	this->method = env->GetMethodID(cls, "add", "(Ljava/lang/Object;)Z");

}

void ListAdder::add(HWND hwnd) {
	env->CallBooleanMethod(list, method, NewInteger((int)hwnd));
}

jobject ListAdder::NewInteger(jint value) {
	jclass cls = env->FindClass("java/lang/Integer");
	jmethodID methodID = env->GetMethodID(cls, "<init>", "(I)V");
	return env->NewObject(cls, methodID, value);
}