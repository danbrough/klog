package klog.a.a

import klog.klog


class AA {
  private val log = klog()

  fun test() {
    log.trace("testing")
    log.debug("more testing")
    log.info("finished testing")
  }
}