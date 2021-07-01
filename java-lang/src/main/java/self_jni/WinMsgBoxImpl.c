
#include <stdio.h>
#include <self_jni_WinMsgBox.h>
JNIEXPORT void JNICALL Java_self_1jni_WinMsgBox_showMsgBox(JNIEnv *env,jobject obj, jstring str){
 printf("hello world");
}