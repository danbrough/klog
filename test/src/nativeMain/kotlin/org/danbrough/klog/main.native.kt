package org.danbrough.klog

actual fun test() {
  log.debug { "doing native test" }
}