package a


import org.danbrough.klog.klog
import org.danbrough.klog.klogName


class A {
  //We have to provide the fully qualified name for the JS platform
  //otherwise could just use `klog()`
  private val log = klog("a.A")


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