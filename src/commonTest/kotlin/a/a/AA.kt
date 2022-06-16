package a.a

import org.danbrough.klog.klog
import org.danbrough.klog.klogName


class AA {
  //have to provide the fully qualified name for the JS platform
  //otherwise could just use `klog.klog()`
  private val log = klog(AA::class)

  fun test() {
    if (!log.isTraceEnabled) log.warn("TRACING DISABLED")
    if (!log.isDebugEnabled) log.warn("DEBUG DISABLED")
    if (!log.isInfoEnabled) log.warn("INFO DISABLED")

    log.trace("testing ${this::class.klogName()} ")
    log.debug("more testing")
    log.info("finished testing")
    log.warn("${this::class.klogName()} done")
    log.warn("")
  }
}