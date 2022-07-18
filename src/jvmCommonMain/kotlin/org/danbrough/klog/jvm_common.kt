package org.danbrough.klog

import kotlin.reflect.KClass


val stackDepth: Int = (runCatching {
  Class.forName("android.util.Log")
  8
}.getOrNull() ?: 7).also {
  println("STACK DEPTH: $it")
}


private val log = klog("KLOG", Level.TRACE, KLogWriters.stdOut, KMessageFormatters.verbose.colored)

fun listSysProps() {
  log.error("JVM NAME: ${System.getProperty("java.vm.name")}")
  log.error("JAVA VENDOR: ${System.getProperty("java.vendor")}")
  System.getProperties().let { props ->
    props.keys.forEach {
      println("PROP: $it:\t${props[it]}")
    }
  }
}

@Suppress("NOTHING_TO_INLINE")
actual inline fun platformStatementContext(): StatementContext =
  Thread.currentThread().let {
    val stackElement = it.stackTrace[stackDepth]
    StatementContext(
      it.name, it.name,
      StatementContext.LineContext(
        stackElement.lineNumber,
        stackElement.methodName,
        stackElement.className,
        stackElement.fileName,
      )
    )
  }


actual fun getTimeMillis(): Long = System.currentTimeMillis()


actual fun KClass<*>.klogName(): String = qualifiedName!!.removeSuffix(".Companion")
