package org.danbrough.klog

private object JvmLogRegistry : DefaultLogFactory()

actual fun createKLogRegistry(): KLogFactory = JvmLogRegistry

