package org.danbrough.klog


@Suppress("LeakingThis", "MemberVisibilityCanBePrivate")
abstract class KLogRegistry {

  companion object {
    const val ROOT_LOG_NAME = ""
  }

  abstract operator fun get(name: String): KLog

  @Suppress("NOTING_TO_INLINE")
  inline fun get(
    name: String,
    level: Level? = null,
    noinline formatter: KMessageFormatter? = null,
    noinline writer: KLogWriter? = null
  ): KLog = this[name].also {
    applyToBranch(name) {
      if (level != null) this.level = level
      if (formatter != null) this.formatter = formatter
      if (writer != null) this.writer = writer
    }
  }


  abstract fun getLogs(): Set<KLog>

  abstract fun applyToBranch(name: String, toApply: KLog.() -> Unit)

}


@Suppress("LeakingThis")
open class DefaultLogRegistry(
  level: Level = Level.WARN,
  formatter: KMessageFormatter = KLogFormatters.simple,
  writer: KLogWriter = KLogWriters.stdOut
) : KLogRegistry() {

  private var logs = mutableMapOf<String, KLog>()

  init {
    logs[ROOT_LOG_NAME] = KLog(this, ROOT_LOG_NAME, KLogOptions(level, formatter, writer))
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
    if (it.key == name || it.key.startsWith("$name.")) it.value.toApply()
  }

}
