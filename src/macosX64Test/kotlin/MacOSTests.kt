import org.danbrough.klog.*
import kotlin.test.Test

class MacOSTests {
  private val log = klog(level = Level.TRACE, messageFormatter = KMessageFormatters.verbose.colored)

  @Test
  fun test1() {
    log.info("MacOS test working")

  }
}