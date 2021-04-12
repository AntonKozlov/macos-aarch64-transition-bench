
#include "org_openjdk_MyBenchmark.h"

JNIEXPORT jboolean JNICALL
JVM_SupportsCX8(void);

JNIEXPORT jlong JNICALL
JVM_NanoTime(JNIEnv *env, jclass ignored);

static jfieldID field;

JNIEXPORT void JNICALL Java_org_openjdk_MyBenchmark_nativeInit
  (JNIEnv *env, jclass cls, jclass dummy)
{
    field = (*env)->GetFieldID(env, dummy, "field", "I");
}

JNIEXPORT void JNICALL Java_org_openjdk_MyBenchmark_jniCall
  (JNIEnv *env, jclass cls)
{
}

JNIEXPORT void JNICALL Java_org_openjdk_MyBenchmark_nativeWX
  (JNIEnv *env, jclass cls)
{
    JVM_SupportsCX8();
}

JNIEXPORT void JNICALL Java_org_openjdk_MyBenchmark_nativeNanoTime
  (JNIEnv *env, jclass cls)
{
    JVM_NanoTime(env, cls);
}

JNIEXPORT void JNICALL Java_org_openjdk_MyBenchmark_nativeTestGetField
  (JNIEnv *env, jclass cls, jobject dummy, jint loopCnt)
{
    for (int i = 0; i < loopCnt; ++i) {
        (*env)->GetIntField(env, dummy, field);
    }
}
