import androidx.test.ext.junit.runners.AndroidJUnit4
import org.danbrough.klog.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AndroidTests {
  private val log =
    kLogRegistry.initRegistry(Level.TRACE, KLogFormatters.verbose.colored(), KLogWriters.androidLog)

  @Test
  fun test() {
    log.trace("trace")
    log.debug("debug")
    log.info("info")
    log.warn("warn")
    log.error("error")

  }
}