package klog.stdout

actual fun printMethodStderr(): Printer = {
  System.err.println(it?.toString())
}
