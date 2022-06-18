package org.danbrough.klog


@Suppress("LeakingThis", "MemberVisibilityCanBePrivate")
abstract class KLogRegistry {

  companion object {
    const val ROOT_LOG_NAME = ""
  }

  var rootLog: KLog
    get() = this[ROOT_LOG_NAME]
    set(value) {
      initRegistry(value)
    }


  abstract operator fun get(name: String): KLog

  abstract fun getLogs(): Set<KLog>

  abstract fun initRegistry(rootLog: KLog): KLogRegistry

  fun initRegistry(
    level: Level = Level.TRACE,
    formatter: KLogFormatter = KLogFormatters.simple,
    writer: KLogWriter = KLogWriters.noop
  ) = initRegistry(KLog(this, ROOT_LOG_NAME, level, formatter, writer))

  abstract fun applyToBranch(name: String, toApply: KLog.() -> Unit)

}

expect fun createKLogRegistry(): KLogRegistry

open class DefaultLogRegistry(
  level: Level = Level.WARN,
  formatter: KLogFormatter = KLogFormatters.simple,
  writer: KLogWriter = KLogWriters.stdOut
) : KLogRegistry() {

  private var logs = mutableMapOf<String, KLog>()

  init {
    initRegistry(level, formatter, writer)
  }

  override operator fun get(name: String): KLog {
    val log = logs[name]
    if (log != null) return log
    val parentName = name.substringBeforeLast('.', ROOT_LOG_NAME)
    return get(parentName).copy(name = name).also {
      logs[name] = it
    }
  }

  override fun getLogs(): Set<KLog> = logs.values.toSet()

  //invoke [toApply] on all logs with names starting with (and including) [name]
  override fun applyToBranch(name: String, toApply: KLog.() -> Unit) = logs.forEach {
    if (it.key.startsWith(name)) it.value.toApply()
  }

  override fun initRegistry(rootLog: KLog): KLogRegistry {
    logs.clear()
    logs[ROOT_LOG_NAME] = rootLog.copy(registry = this)
    return this
  }
}
