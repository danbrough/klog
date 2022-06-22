import org.danbrough.klog.KMessageFormatter
import org.danbrough.klog.Level
import org.danbrough.klog.klog
import kotlin.test.Test

class MacOSTests {
  private val log = klog(level = Level.TRACE, formatters = KMessageFormatters.verbose.colored)

  @Test
  fun test1() {
    log.info("MacOS test working")

  }
}