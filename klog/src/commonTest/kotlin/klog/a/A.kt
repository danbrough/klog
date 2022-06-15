package klog.a



import klog.klog


class A {
  private val log = klog()

  fun test() {
    log.trace("testing")
    log.debug("more testing")
    log.info("finished testing")
  }
}