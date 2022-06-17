@file:Suppress("unused")

package org.danbrough.klog

import kotlin.reflect.KClass


val kLogRegistry = createKLogRegistry()

/*
@return Simple class name on JS and the fully qualified elsewhere
 */
expect fun KClass<*>.klogName(): String
expect fun getTimeMillis(): Long

inline fun <reified T : Any> T.klog(): KLog = kLogRegistry[this::class.klogName()]


inline fun klog(tag: String): KLog = kLogRegistry[tag]

@Suppress("NOTING_TO_INLINE")
inline fun klog(clazz: KClass<*>): KLog = kLogRegistry[clazz.klogName()]


typealias LogMessageFunction = () -> String

enum class Level {
  TRACE, DEBUG, INFO, WARN, ERROR;
}








