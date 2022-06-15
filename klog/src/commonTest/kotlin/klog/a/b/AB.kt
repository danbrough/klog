package klog.a.b

import klog.klog
import klog.name


class AB {
  //have to provide the fully qualified name for the JS platform
  //otherwise could just use `klog.klog()`
  private val log = klog("klog.a.b.AB")


  fun test() {
    if (!log.isTraceEnabled) log.warn("TRACING DISABLED")
    if (!log.isDebugEnabled) log.warn("DEBUG DISABLED")
    if (!log.isInfoEnabled) log.warn("INFO DISABLED")

    log.trace("testing ${this::class.name()} ")
    log.debug("more testing")
    log.info("finished testing")
    log.warn("${this::class.name()} done")
  }
}