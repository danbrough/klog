@file:Suppress("unused")

package org.danbrough.klog

import kotlin.reflect.KClass

typealias LogMessageFunction = () -> String

enum class Level {
  TRACE, DEBUG, INFO, WARN, ERROR;
}

expect fun createKLogRegistry(): KLogRegistry

//default global KLogRegistry instance
val kLogRegistry = createKLogRegistry()

/*
@return Simple class name on JS and the fully qualified elsewhere
 */
expect fun KClass<*>.klogName(): String
expect fun getTimeMillis(): Long

@Suppress("NOTHING_TO_INLINE")

inline fun <reified T : Any> T.klog(): KLog =
  kLogRegistry.get(this::class.klogName(), null, null, null)

@Suppress("NOTHING_TO_INLINE")
inline fun <reified T : Any> T.klog(
  level: Level? = null,
  noinline writer: KLogWriter? = null,
  noinline messageFormatter: KMessageFormatter? = null,
  noinline nameFormatter: KNameFormatter? = null,
): KLog = kLogRegistry.get(this::class.klogName(), level, writer, messageFormatter, nameFormatter)

@Suppress("NOTHING_TO_INLINE")
inline fun <reified T : Any> T.klog(
  config: KLog.() -> Unit
): KLog = kLogRegistry.get(this::class.klogName(), null, null, null).also(config)

@Suppress("NOTHING_TO_INLINE")
inline fun klog(
  name: String,
  level: Level? = null,
  noinline writer: KLogWriter? = null,
  noinline messageFormatter: KMessageFormatter? = null,
  noinline nameFormatter: KNameFormatter? = null,
): KLog = kLogRegistry.get(name, level, writer, messageFormatter, nameFormatter)

@Suppress("NOTHING_TO_INLINE")
inline fun klog(
  name: String
): KLog = kLogRegistry.get(name, null, null, null, null)

@Suppress("NOTHING_TO_INLINE")
inline fun klog(
  clazz: KClass<*>,
  level: Level? = null,
  noinline writer: KLogWriter? = null,
  noinline messageFormatter: KMessageFormatter? = null,
  noinline nameFormatter: KNameFormatter? = null,
): KLog = kLogRegistry.get(clazz.klogName(), level, writer, messageFormatter, nameFormatter)










