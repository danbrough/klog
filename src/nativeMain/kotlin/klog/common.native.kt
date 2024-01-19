package klog

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString


@OptIn(ExperimentalForeignApi::class)
internal actual fun getenv(name: String) = platform.posix.getenv(name)?.toKString()


