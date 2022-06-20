import org.danbrough.klog.klog
import kotlin.test.Test

class MacOSTests {
  val log = klog()

  @Test
  fun test1() {
    log.info("MacOS test working")

  }
}