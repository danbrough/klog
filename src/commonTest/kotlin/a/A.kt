package a


import klog.klog
import klog.klogName


class A {
  /**
   *We have to provide a log name for the JS platform
   * otherwise could just use `klog()`
   */

  private val log = klog("a.A")


  fun test() {
    if (!log.isEnabled) log.warn("LOG DISABLED")
    if (!log.isTraceEnabled) log.warn("TRACING DISABLED")
    if (!log.isDebugEnabled) log.warn("DEBUG DISABLED")
    if (!log.isInfoEnabled) log.warn("INFO DISABLED")
    if (!log.isWarnEnabled) log.warn("WARN DISABLED")
    if (!log.isErrorEnabled) log.warn("ERROR DISABLED")

    log.trace("A.test(): testing ${this::class.klogName()} ")
    log.debug("more testing")
    log.info("finished testing")
    log.warn(this::class.klogName())
    log.error("error message")
  }
}