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
    noinline writer: KLogWriter? = null,
    noinline messageFormatter: KMessageFormatter? = null,
    noinline nameFormatter: KNameFormatter? = null,

    ): KLog = this[name].also {
    if (level != null) it.level = level
    if (messageFormatter != null) it.messageFormatter = messageFormatter
    if (writer != null) it.writer = writer
    if (nameFormatter != null) it.nameFormatter = nameFormatter
  }

  abstract fun getLogs(): Set<KLog>

  abstract fun applyToBranch(name: String, toApply: KLog.() -> Unit)

}


@Suppress("LeakingThis")
open class DefaultLogRegistry(
  level: Level = Level.WARN,
  formatter: KMessageFormatter = KLogMessageFormatters.simple,
  writer: KLogWriter = KLogWriters.stdOut
) : KLogRegistry() {

  private var logs = mutableMapOf<String, KLogImpl>()

  init {
    logs[ROOT_LOG_NAME] = KLogImpl(this, ROOT_LOG_NAME, level, writer, formatter)
  }

  override operator fun get(name: String): KLog =
    logs[name] ?: getParent(name).copy(name = name).also {
      logs[name] = it as KLogImpl
    }

  private fun getParent(name: String): KLogImpl =
    name.substringBeforeLast('.', ROOT_LOG_NAME).let { parentName ->
      logs[parentName] ?: getParent(parentName)
    }

  override fun getLogs(): Set<KLog> = logs.values.toSet()

  //invoke [toApply] on all logs with names starting with (and including) [name]
  override fun applyToBranch(name: String, toApply: KLog.() -> Unit) = logs.forEach {
    if (it.key == name || it.key.startsWith("$name.")) it.value.toApply()
  }

}
