import klog.*
import kotlin.test.Test

/**
 * Created top level logger for package "a"
 */
private val aLog = klog("a") {
  writer = KLogWriters.stdOut
  messageFormatter = KMessageFormatters.colored(KMessageFormatters.verbose)
  level = Level.TRACE
}


class CommonTests {


  private val log = klog()

  @Test
  fun test1() {
    aLog.info("test1()")
    log.debug("klogname: ${this::class.klogName()}")
    log.trace {
      "A lazy trace message"
    }
    log.warn("warning message")
    log.error("An error occurred", Exception("Error message"))

    a.A().test()

    log.trace("setting aLog level to INFO")
    aLog.conf.level = Level.INFO

    a.A().test()
  }


}