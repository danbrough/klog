@file:OptIn(ExperimentalWasmJsInterop::class)

package org.danbrough.klog

import org.danbrough.klog.std.colored
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.js
import kotlin.reflect.KClass

val inNode: Boolean = js("typeof process === 'object'")


private fun jsEnv(name: String): String? = js("process.env[name]")
private fun getEnvJS(name: String): String? = if (inNode) jsEnv(name) else null


/*
@Suppress("RedundantNullableReturnType")
private fun getEnvJS(name: String): String? =
  js("typeof process === 'object' ? process.env[name] : null")

private fun console(s: String?): Unit = js("console.info(s)")

actual object Utils : KLogUtils() {
  @OptIn(ExperimentalWasmJsInterop::class)
  actual override val environment: Map<String, String?> =
    object : Map<String, String?> by emptyMap() {
      override fun get(key: String): String? = if (key == "KLOG_LEVEL") "TRACE" else getEnvJS(key)
    }

  actual override fun getThreadName(): String = ""

  actual override val stderrPrinter: Printer = { console("$it") }
  actual override val stdoutPrinter: Printer = stderrPrinter
  actual override fun <T : Any> loggerName(clazz: KClass<T>): String = "KLogger"

}

private object JSLoggingFactory : StandardLogFactory() {


  init {

    log = { level, name, message, t ->
      @Suppress("UNCHECKED_CAST") val printMethod: Printer = when (level) {
        Level.TRACE -> { o: Any? ->
          if (inNode) js("console.debug(o)")
          else js("console.trace(o)")
        }

        Level.DEBUG -> { o: Any? ->
          js("console.debug(o)")
        }

        Level.INFO -> { o: Any? ->
          js("console.info(o)")
        }

        Level.WARN -> { o: Any? ->
          js("console.warn(o)")
        }

        Level.ERROR -> { o: Any? ->
          js("console.error(o)")
        }

        Level.NONE -> {}
      } as Printer
      if (level != Level.NONE) {
        printMethod(formatter.invoke(this@JSLoggingFactory, level, name, message))
        if (t != null) printer(colorString(Level.ERROR, t.stackTraceToString()))
      }
    }
  }
}
*/

private fun consoleInfo(s: String?): Unit = js("console.info(s)")
private fun consoleDebug(s: String?): Unit = js("console.debug(s)")
private fun consoleTrace(s: String?): Unit = js("console.trace(s)")
private fun consoleWarn(s: String?): Unit = js("console.warn(s)")
private fun consoleError(s: String?): Unit = js("console.error(s)")


val JSLogWriter: KLogWriter =
  { conf: KLogConfiguration, level: Level, name: String, msg: String, t: Throwable? ->
    val message = level.colored(conf.formatMessage(level, name, msg, t))
    when (level) {
      Level.TRACE -> if (inNode) consoleDebug(message) else consoleTrace(message)
      Level.DEBUG -> consoleDebug(message)
      Level.INFO -> consoleInfo(message)
      Level.WARN -> consoleWarn(message)
      Level.ERROR -> consoleError(message)
      else -> {}
    }
  }

object JSLoggingFactory : KLogFactory(KLogConfiguration(JSLogWriter))
actual object Utils : KLogUtils {
  actual override val environment: Map<String, String?> =
    object : Map<String, String?> by mapOf("KLOG_LEVEL" to "TRACE") {
      override fun containsKey(key: String): Boolean = when(key) {
        "KLOG_COLOR" -> inNode
        else -> false
      }
      override fun get(key: String): String? = if (key == "KLOG_LEVEL") "TRACE" else getEnvJS(key)
    }

  actual override fun getThreadName(): String = "main"

  actual override val stdoutPrinter: Printer = { println(it?.toString()) }

  actual override val stderrPrinter: Printer = stdoutPrinter

  actual override fun <T : Any> loggerName(clazz: KClass<T>): String = "KLogger"

  override fun defaultLogFactory(): KLogFactory = JSLoggingFactory

}