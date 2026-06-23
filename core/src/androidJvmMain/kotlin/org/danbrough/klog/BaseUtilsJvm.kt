package org.danbrough.klog

import org.danbrough.klog.std.Printer
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

open class BaseUtilsJvm : KLogUtils {
  override val environment: Map<String, String?> = System.getenv()

  override fun getThreadName(): String = Thread.currentThread().name
  override val stderrPrinter: Printer = System.err::println
  override val stdoutPrinter: Printer = System.out::println
  override fun <T : Any> loggerName(clazz: KClass<T>): String =
    unwrapCompanionClass(clazz.java).name

}


private fun <T : Any> unwrapCompanionClass(clazz: Class<T>): Class<*> {
  return clazz.enclosingClass?.let { enclosingClass ->
    try {
      enclosingClass.declaredFields
        .find { field ->
          field.name == clazz.simpleName &&
              Modifier.isStatic(field.modifiers) &&
              field.type == clazz
        }
        ?.run { enclosingClass }
    } catch (se: SecurityException) {
      // The security manager isn't properly set up, so it won't be possible
      // to search for the target declared field.
      null
    }
  } ?: clazz
}

