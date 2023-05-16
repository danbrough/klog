package klog

import klog.KLog.Companion.ROOT_LOG_TAG


@Suppress("LeakingThis", "MemberVisibilityCanBePrivate")
abstract class KLogFactory {


  abstract operator fun get(tag: String): KLog

  abstract fun getLogs(): Set<KLog>

  abstract fun applyToBranch(tag: String, toApply: KLog.() -> Unit)

}


//@Suppress("LeakingThis")
@Suppress("LeakingThis")
open class DefaultLogFactory(
  level: Level = Level.WARN,
  formatter: KMessageFormatter = KMessageFormatters.simple,
  writer: KLogWriter = KLogWriters.noop
) : KLogFactory() {

  private var logs = mutableMapOf<String, KLog>()

  init {
    logs[ROOT_LOG_TAG] = KLog(ROOT_LOG_TAG, KLog.Conf(level, writer, formatter))
  }

  override operator fun get(tag: String): KLog =
    logs[tag] ?: KLog(tag, getParent(tag).conf).also {
      logs[tag] = it
    }


  private fun getParent(tag: String): KLog =
    tag.substringBeforeLast('.', ROOT_LOG_TAG).let { parentTag ->
      logs[parentTag] ?: getParent(parentTag)
    }

  override fun getLogs(): Set<KLog> = logs.values.toSet()

  /**
   *invoke [toApply] on all logs with tags starting with (and including) [tag]
   */
  override fun applyToBranch(tag: String, toApply: KLog.() -> Unit) = logs.forEach {
    if (it.key == tag || it.key.startsWith("$tag.")) it.value.toApply()
  }
}

