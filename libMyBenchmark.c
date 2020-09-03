
#include "org_openjdk_MyBenchmark.h"

JNIEXPORT jint JNICALL
JVM_GetInterfaceVersion(void);

JNIEXPORT void JNICALL Java_org_openjdk_MyBenchmark_jniCall
  (JNIEnv *env, jclass cls)
{
}

JNIEXPORT void JNICALL Java_org_openjdk_MyBenchmark_nativeWX
  (JNIEnv *env, jclass cls)
{
    JVM_GetInterfaceVersion();
}
