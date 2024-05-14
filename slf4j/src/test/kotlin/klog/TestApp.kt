package klog

class TestApp {
  companion object {
    init {
      kloggingSlf4j {

      }
    }

    val log by logger()

    @JvmStatic
    fun main(args: Array<String>) {

      log.info { "main() running" }
      log.trace { "trace" }
      log.debug { "debug" }
      log.info { "info" }
      log.warn { "warn" }
      log.error { "error" }

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
