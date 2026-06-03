package org.danbrough.klog.test.test2

actual fun getEnv(name: String): String? = System.getenv(name)