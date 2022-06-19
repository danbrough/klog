import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.danbrough.klog.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AndroidTests {
/*
  companion object {
    init {
      kLogRegistry.initRegistry(
        Level.TRACE,
        KLogFormatters.verbose.colored(),
        KLogWriters.androidLog
      )
    }
  }*/

  private val log = klog(Level.TRACE,KLogFormatters.verbose.colored)

  @Test
  fun test() {
    Log.i("TEST", "############################################################################")

    log.trace("trace")
    log.debug("debug")
    log.info("info")
    log.warn("warn")
    log.error("error")
    log.error("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$")

  }
}