@file:Suppress("unused")

package org.danbrough.klog

import kotlin.reflect.KClass

typealias LogMessageFunction = () -> String

enum class Level {
  TRACE, DEBUG, INFO, WARN, ERROR;
}

expect fun createKLogRegistry(): KLogFactory

//default global KLogRegistry instance
val kLogRegistry = createKLogRegistry()

/*
@return Simple class name on JS and the fully qualified elsewhere
 */
expect fun KClass<*>.klogName(): String

expect fun getTimeMillis(): Long

inline fun <reified T : Any> T.klog(): KLog =
  kLogRegistry[this::class.klogName()]

inline fun <reified T : Any> T.klog(level: Level? = null): KLog =
  kLogRegistry[this::class.klogName()]

inline fun <reified T : Any> T.klog(config: KLog.() -> Unit): KLog =
  kLogRegistry[this::class.klogName()].also(config)

inline fun klog(tag: String): KLog = kLogRegistry[tag]

inline fun klog(clazz: KClass<*>): KLog = kLogRegistry[clazz.klogName()]










