import org.danbrough.klog.*
import kotlin.test.Test


class CommonTests {
  companion object {
    /**
     * Create a root level log
     */
    private val rootLog =
      klog(name = "", Level.TRACE, KLogWriters.stdOut, KMessageFormatters.verbose.colored)
  }

  /**
   * Inherits configuration from the root level log
   */
  private val log = klog()

  @Test
  fun test1() {
    log.info("test1()")
    log.debug("klogname: ${this::class.klogName()}")
    log.trace {
      "A lazy trace message"
    }
    log.warn("warning message")
    log.error("An error occurred", Exception("Error message"))

    a.A().test()

    rootLog.level = Level.INFO

    a.A().test()
  }


}