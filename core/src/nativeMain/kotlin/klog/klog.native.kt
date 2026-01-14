package klog

import klog.stdout.StdoutLogging
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import kotlin.reflect.KClass

actual fun <T : Any> loggerName(clazz: KClass<T>): String =
  clazz.qualifiedName!!.substringBefore(".Companion")

actual fun klogDefaultFactory(): KLogFactory = StdoutLogging()

@OptIn(ExperimentalForeignApi::class)
actual fun getEnv(name: String): String? = platform.posix.getenv(name)?.toKString()
