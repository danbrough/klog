package klog

import klog.stdout.StdoutLogging
import kotlin.reflect.KClass

actual fun <T : Any> loggerName(clazz: KClass<T>): String =
  clazz.qualifiedName!!.substringBefore(".Companion")

actual fun klogDefaultFactory(): KLogFactory = StdoutLogging