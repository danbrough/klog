package klog

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LogTests {

  val log = klog("INSTR_TEST") {
    level = Level.TRACE
    messageFormatter = KMessageFormatters.colored(KMessageFormatters.verbose)
  }

  @Test
  fun test1() {
    Log.i("TEST", "Android log test: message: ${getMessage()}")
    log.info("Klog log test")
  }
}