package org.danbrough.klog

actual object Utils : BaseUtilsJvm() {
  actual fun standardLogFactory(): KLogFactory = AndroidLogging
}