package klog

internal actual fun getenv(name: String): String? {

  return null
}

actual fun getClassName(o: Any): String? = o::class.toString()