@file:Suppress("unused")

package org.danbrough.klog

import kotlin.reflect.KClass


val kLogRegistry = createKLogRegistry()


typealias KLogWriter = (String) -> Unit



typealias LogMessageFunction = () -> String
typealias KLogFormatter = (String, Level, String, Throwable?, KLogMessageContext) -> String

enum class Level {
  ALL, TRACE, DEBUG, INFO, WARN, ERROR, NONE;
}




/*
@return Simple class name on JS and the fully qualified elsewhere
 */
expect fun KClass<*>.klogName(): String

inline fun <reified T : Any> T.klog(): KLog {
  return kLogRegistry[this::class.klogName()]
}

inline fun klog(tag: String): KLog = kLogRegistry[tag]
inline fun klog(clazz: KClass<*>): KLog = kLogRegistry[clazz.klogName()]



