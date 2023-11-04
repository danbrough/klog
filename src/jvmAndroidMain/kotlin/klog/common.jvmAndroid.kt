package klog

internal actual fun getenv(name: String) =
  System.getenv(name)
