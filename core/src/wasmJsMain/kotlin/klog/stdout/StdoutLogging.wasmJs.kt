package klog.stdout

actual fun printMethodStderr(): Printer = { kotlin.js.console(it?.toString()) }


