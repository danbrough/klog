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

private val debugMessage: (String)->Unit = {}//::println

@Suppress("LeakingThis")
open class DefaultLogRegistry(
  level: Level = Level.WARN,
  formatter: KMessageFormatter = KMessageFormatters.simple,
  writer: KLogWriter = KLogWriters.stdOut
) : KLogRegistry() {

  private var logs = mutableMapOf<String, KLogImpl>()

  init {
    debugMessage("setting root log..")
    logs[ROOT_LOG_NAME] = KLogImpl(this, ROOT_LOG_NAME, level, writer, formatter)
    debugMessage("root log set.")
  }

  override operator fun get(name: String): KLog {
    debugMessage("operator get() $name")
    return logs[name]?.also { debugMessage("found logs[$name]") } ?: getParent(name).let {
      debugMessage("returning copy of ${it.name} with name $name")
      val newLog = it.copy(name = name)
      logs[name] = newLog as KLogImpl
      newLog
    }
  }

  private fun getParent(name: String): KLogImpl {
    debugMessage("getParent() for $name")
    return name.substringBeforeLast('.', ROOT_LOG_NAME).let { parentName ->
      debugMessage("parentName: $parentName")
      logs[parentName]?.also {
        debugMessage("returning log with name: $parentName")
      } ?: getParent(parentName)
    }
  }

  override fun getLogs(): Set<KLog> = logs.values.toSet()

  //invoke [toApply] on all logs with names starting with (and including) [name]
  override fun applyToBranch(name: String, toApply: KLog.() -> Unit) = logs.forEach {
    if (it.key == name || it.key.startsWith("$name.")) it.value.toApply()
  }
}

