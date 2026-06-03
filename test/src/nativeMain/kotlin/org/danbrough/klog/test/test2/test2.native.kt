package org.danbrough.klog.test.test2

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString

@OptIn(ExperimentalForeignApi::class)
actual fun getEnv(name: String): String? = platform.posix.getenv(name)?.toKString()