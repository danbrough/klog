package klog

import klog.stdout.StdoutLogging
import kotlin.reflect.KClass


var loggerName = "klog"

actual fun klogDefaultFactory(): KLogFactory = StdoutLogging

actual fun <T : Any> loggerName(clazz: KClass<T>): String = "KOTLIN"