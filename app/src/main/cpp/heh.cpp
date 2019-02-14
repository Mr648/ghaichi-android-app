
//
// Created by mr-code on 9/27/2018.
//


#include <jni.h>
#include <string>


extern "C"
JNIEXPORT jstring JNICALL
Java_com_sorinaidea_ghaichi_auth_ObscuredSharedPreferences_getSecretKey(
        JNIEnv *env,
        jobject /* this */) {
    std::string heh = "hXrC682CBoFqsbaiVEXZEqjAYY7CEDPodKXZEsgInrirovjupJMaXV+pkCBmlKb9r+qHrCyGeG33Ldr4txok0FTPEdUPV8zAS0cQq4JDuaURDeULwtHhGOZ+1Qa1q3OqkTSGXImkRjtmXqN55LZdrRY=";
    return env->NewStringUTF(heh.c_str());
}



extern "C"
JNIEXPORT jstring JNICALL
Java_com_sorinaidea_ghaichi_util_Security_getSecretKey(JNIEnv *env, jobject instance) {

    std::string heh = "hXrC682CBoFqsbaiVEXZEqjAYY7CEDPodKXZEsgInrirovjupJMaXV+pkCBmlKb9r+qHrCyGeG33Ldr4txok0FTPEdUPV8zAS0cQq4JDuaURDeULwtHhGOZ+1Qa1q3OqkTSGXImkRjtmXqN55LZdrRY=";
    return env->NewStringUTF(heh.c_str());
}



extern "C"
JNIEXPORT jstring JNICALL
Java_com_sorinaidea_ghaichi_util_Security_getUserAgent(JNIEnv *env, jobject instance) {

    std::string userAgent = "GHAICHI-APPLICATION-USER";
    return env->NewStringUTF(userAgent.c_str());
}