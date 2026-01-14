package klog

import klog.stdout.StdoutLogging
import kotlinx.coroutines.runBlocking
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

actual fun klogDefaultFactory(): KLogFactory = StdoutLogging()

actual fun <T : Any> loggerName(clazz: KClass<T>): String = unwrapCompanionClass(clazz.java).name

private fun <T : Any> unwrapCompanionClass(clazz: Class<T>): Class<*> {
  return clazz.enclosingClass?.let { enclosingClass ->
    runBlocking {

    }
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


actual fun getEnv(name: String): String? = System.getenv(name)