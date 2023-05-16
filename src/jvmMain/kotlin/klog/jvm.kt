package klog

private object JvmLogRegistry : DefaultLogFactory()

actual fun createKLogFactory(): KLogFactory = JvmLogRegistry

