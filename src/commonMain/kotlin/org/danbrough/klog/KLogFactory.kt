package org.danbrough.klog


@Suppress("LeakingThis", "MemberVisibilityCanBePrivate")
abstract class KLogFactory {

  companion object {
    const val ROOT_LOG_NAME = ""
  }

  abstract operator fun get(tag: String): KLog

  abstract fun getLogs(): Set<KLog>

  abstract fun applyToBranch(name: String, toApply: KLog.() -> Unit)

}


//@Suppress("LeakingThis")
@Suppress("LeakingThis")
open class DefaultLogFactory(
  level: Level = Level.WARN,
  formatter: KMessageFormatter = KMessageFormatters.simple,
  writer: KLogWriter = KLogWriters.noop
) : KLogFactory() {

  private var logs = mutableMapOf<String, KLogImpl>()

  init {
    logs[ROOT_LOG_NAME] = KLogImpl(this, ROOT_LOG_NAME, KLogConf(level,writer,formatter))
  }

  override operator fun get(tag: String): KLog =
    logs[tag] ?: getParent(tag).copy(tag = tag).also {
      logs[tag] = it as KLogImpl
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

