package org.danbrough.klog

import org.danbrough.klog.std.colored
import kotlin.reflect.KClass


val WasmWasiWriter: KLogWriter =
  { conf: KLogConfiguration, level: Level, name: String, msg: String, t: Throwable? ->
    val message = level.colored(conf.formatMessage(level, name, msg, t))
    println(message)
    /*when (level) {
      Level.TRACE -> if (inNode) consoleDebug(message) else consoleTrace(message)
      Level.DEBUG -> consoleDebug(message)
      Level.INFO -> consoleInfo(message)
      Level.WARN -> consoleWarn(message)
      Level.ERROR -> consoleError(message)
      else -> {}
    }*/
  }

object WasmWasiLoggingFactory : KLogFactory(KLogConfiguration(WasmWasiWriter))

actual object Utils : KLogUtils {
  actual override val environment: Map<String, String?>
    get() = mapOf("KLOG_LEVEL" to "TRACE")


  actual override fun getThreadName(): String = "main"

  actual override val stdoutPrinter: Printer = { println(it?.toString()) }

  actual override val stderrPrinter: Printer = stdoutPrinter

  actual override fun <T : Any> loggerName(clazz: KClass<T>): String = "KLogger"
  override fun defaultLogFactory(): KLogFactory = WasmWasiLoggingFactory


}