package klog


interface KLogRegistry {
  var rootLog: KLog
  operator fun get(name: String): KLog
  operator fun set(name: String, log: KLog)
  fun reset()
}

expect fun createKogRegistry(): KLogRegistry

open class DefaultLogRegistry : KLogRegistry {
  override var rootLog: KLog = KLog("", Level.NONE, LogFormatters.simple, LogWriters.stdOut)
    set(value) {
      field = value
      this[""] = value
    }

  private var logs = mutableMapOf<String, KLog?>()

  override operator fun get(name: String): KLog {
    if (name.isEmpty()) return rootLog
    val log = logs[name]
    if (log != null) return log
    val parentName = name.substringBeforeLast('.', "")
    return get(parentName).copy(name = name).also {
      logs[name] = it
    }
  }

  override operator fun set(name: String, log: KLog) {
    logs = logs.filterKeys { it.startsWith(name) }.toMutableMap()
    logs[name] = log
  }

  override fun reset() {
    val log = rootLog
    logs.clear()
    rootLog = log
  }
}
