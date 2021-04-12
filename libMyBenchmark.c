
#include "org_openjdk_MyBenchmark.h"

JNIEXPORT jboolean JNICALL
JVM_SupportsCX8(void);

JNIEXPORT jlong JNICALL
JVM_NanoTime(JNIEnv *env, jclass ignored);

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
