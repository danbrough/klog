package klog

private object JvmLogRegistry : DefaultLogFactory()

actual fun createKLogRegistry(): KLogFactory = JvmLogRegistry

