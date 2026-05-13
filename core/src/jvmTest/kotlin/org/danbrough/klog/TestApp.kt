package org.danbrough.klog

import org.danbrough.klog.installLogging
import org.danbrough.klog.logger
import org.danbrough.klog.stdout.StdoutLogging

class TestApp {
  companion object {
    init {
      installLogging(StdoutLogging) {
      }
    }

    val log by logger()

    @JvmStatic
    fun main(args: Array<String>) {
      log.info { "main() running" }
      runCatching {
        TestApp().methodThrowingError()
      }.exceptionOrNull()?.also { err ->
        log.error(err) { err.message ?: "" }
      }
    }
  }

  fun methodThrowingError() {
    log.debug { "methodThrowingError()" }
    error("Test error")
  }


}
