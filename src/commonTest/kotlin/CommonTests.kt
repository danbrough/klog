import org.danbrough.klog.*
import kotlin.test.Test


class CommonTests {

  private val log = klog(Level.TRACE, KLogWriters.stdOut, KMessageFormatters.verbose.colored)


  @Test
  fun test1() {
    log.info("test1()")
    log.debug("klogname: ${this::class.klogName()}")
    log.trace {
      "A lazy trace message"
    }
    log.warn("warning message")
    log.error("An error occurred",Exception("Error message"))
  }


}