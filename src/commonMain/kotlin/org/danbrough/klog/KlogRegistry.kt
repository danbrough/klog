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

  fun createLog(
    name: String,
    level: Level = Level.TRACE,
    formatter: KLogFormatter = KLogFormatters.simple,
    writer: KLogWriter = KLogWriters.noop
  ) = KLog(this, name, level, formatter, writer)

  abstract operator fun get(name: String): KLog

  abstract fun getLogs(): Set<KLog>

  abstract fun initRegistry(rootLog: KLog)

  fun initRegistry(
    level: Level = Level.TRACE,
    formatter: KLogFormatter = KLogFormatters.simple,
    writer: KLogWriter = KLogWriters.noop
  ) = createLog(ROOT_LOG_NAME, level, formatter, writer).apply { initRegistry(this) }

  abstract fun applyToBranch(name: String, toApply: KLog.() -> Unit)

}

expect fun createKLogRegistry(): KLogRegistry

open class DefaultLogRegistry(
  level: Level = Level.TRACE,
  formatter: KLogFormatter = KLogFormatters.simple,
  writer: KLogWriter = KLogWriters.noop
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

  override fun initRegistry(rootLog: KLog) {
    logs.clear()
    logs[ROOT_LOG_NAME] = rootLog.copy(registry = this)
  }
}
