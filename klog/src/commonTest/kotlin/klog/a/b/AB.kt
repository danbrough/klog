package klog.a.b

import klog.klog


class AB {
  private val log = klog()

  fun test() {
    log.trace("testing")
    log.debug("more testing")
    log.info("finished testing")
  }
}