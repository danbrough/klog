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

  private val log = klog().also {
    it.level = Level.TRACE
    it.formatter = KLogFormatters.verbose.colored()
    it.writer = KLogWriters.stdOut
  }

  @Test
  fun test() {
    Log.i("TEST", "############################################################################")
    kLogRegistry.rootLog.level = Level.DEBUG
    log.trace("trace")
    log.debug("debug")
    log.info("info")
    log.warn("warn")
    log.error("error")
    log.error("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$")

  }
}