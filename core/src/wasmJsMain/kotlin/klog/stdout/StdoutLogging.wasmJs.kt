package klog.stdout

actual fun printMethodStderr(): Printer = { println(it?.toString()) }

