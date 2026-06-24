package org.danbrough.klog

import org.danbrough.klog.std.Printer
import org.danbrough.klog.std.StandardLogFactory
import org.danbrough.klog.std.colorString
import kotlin.reflect.KClass

val inNode: Boolean = js("typeof process === 'object'")

private fun getEnvJS(name: String): String? = if (inNode) js("process.env[name]") else null


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

actual object Utils : KLogUtils() {
  actual override val environment: Map<String, String?> =
    object : Map<String, String?> by mapOf("KLOG_LEVEL" to "TRACE") {
      override fun get(key: String): String? = if (key == "KLOG_LEVEL") "TRACE" else getEnvJS(key)
    }

  actual override fun getThreadName(): String = "main"

  actual override val stdoutPrinter: Printer = console::info

  actual override val stderrPrinter: Printer = stdoutPrinter

  actual override fun <T : Any> loggerName(clazz: KClass<T>): String = "KLogger"

  actual fun standardLogFactory(): KLogFactory = JSLoggingFactory

}