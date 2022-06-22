import org.danbrough.klog.KMessageFormatters
import org.danbrough.klog.Level
import org.danbrough.klog.colored
import org.danbrough.klog.klog
import kotlin.test.Test

class MacOSTests {
  private val log = klog(level = Level.TRACE, messageFormatter = KMessageFormatters.verbose.colored)

  @Test
  fun test1() {
    log.info("MacOS test working")

  }
}