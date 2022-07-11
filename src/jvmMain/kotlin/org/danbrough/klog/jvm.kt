package org.danbrough.klog

private object JvmLogRegistry : DefaultLogRegistry()

actual fun createKLogRegistry(): KLogRegistry = JvmLogRegistry

