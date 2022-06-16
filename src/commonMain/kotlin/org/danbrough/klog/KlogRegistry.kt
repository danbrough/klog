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
    level: Level = Level.NONE,
    formatter: LogFormatter = LogFormatters.simple,
    writer: LogWriter? = null
  ) = KLog(this, name, level, formatter, writer)

  abstract operator fun get(name: String): KLog

  abstract fun getLogs(): Set<KLog>

  abstract fun initRegistry(rootLog: KLog)

  fun initRegistry(
    level: Level = Level.NONE,
    formatter: LogFormatter = LogFormatters.simple,
    writer: LogWriter? = null
  ) = initRegistry(createLog(ROOT_LOG_NAME, level, formatter, writer))

  abstract fun applyToBranch(name: String, toApply: KLog.() -> Unit)

}

expect fun createKLogRegistry(): KLogRegistry

open class DefaultLogRegistry(
  level: Level = Level.NONE,
  formatter: LogFormatter = LogFormatters.simple,
  writer: LogWriter? = null
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
    logs[ROOT_LOG_NAME] = rootLog
    getLogs().forEach {
      if (it.name != ROOT_LOG_NAME){

      }
    }
  }
}
