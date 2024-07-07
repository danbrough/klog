package klog.stdout

actual fun printMethodStderr(): PrintMethod = {
  System.err.println(it?.toString())
}
