package org.danbrough.klog

import org.danbrough.klog.stdout.StdoutLogging

actual object Utils : BaseUtilsJvm() {
  actual override fun defaultLogFactory(): KLogFactory = AndroidLogging
}