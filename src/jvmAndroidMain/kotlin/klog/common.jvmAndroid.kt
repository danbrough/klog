package klog

internal actual fun getenv(name: String) =
  System.getenv(name)

actual fun getClassName(o: Any): String? = o::class.qualifiedName!!