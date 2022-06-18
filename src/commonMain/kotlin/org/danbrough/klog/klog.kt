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

inline fun <reified T : Any> T.klog(
  level: Level? = null,
  noinline formatter: KLogFormatter? = null,
  noinline writer: KLogWriter? = null
): KLog = kLogRegistry.get(this::class.klogName(), level, formatter, writer)


@Suppress("NOTING_TO_INLINE")
inline fun klog(
  name: String,
  level: Level? = null,
  noinline formatter: KLogFormatter? = null,
  noinline writer: KLogWriter? = null
): KLog = kLogRegistry.get(name, level, formatter, writer)


@Suppress("NOTING_TO_INLINE")

inline fun klog(
  name: String
): KLog = kLogRegistry[name]

@Suppress("NOTING_TO_INLINE")
inline fun klog(
  clazz: KClass<*>, level: Level? = null,
  noinline formatter: KLogFormatter? = null,
  noinline writer: KLogWriter? = null
): KLog = kLogRegistry.get(clazz.klogName(), level, formatter, writer)


typealias LogMessageFunction = () -> String

enum class Level {
  TRACE, DEBUG, INFO, WARN, ERROR;
}








