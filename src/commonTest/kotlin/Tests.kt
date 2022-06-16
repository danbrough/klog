import a.A
import a.a.AA
import a.b.AB
import org.danbrough.klog.*
import kotlin.test.Test

class Tests {


  companion object {
    init {
      kLogRegistry.initRegistry(
        Level.TRACE,
        KLogFormatters.colored(KLogFormatters.verbose),
        KLogWriters.stdOut
      )
    }

    //have to provide the fully qualified name for the JS platform
    //otherwise could just use `klog.klog()`
    private val log = klog("Tests")

  }

  private fun runLogTest() {
    A().test()
    AA().test()
    AB().test()
  }

  @Test
  fun testLogConf() {

    kLogRegistry.initRegistry(
      Level.TRACE,
      KLogFormatters.colored(KLogFormatters.verbose),
      KLogWriters.stdOut
    )

    log.info("testings logs ..")
    runLogTest()

    log.trace("setting level on class a.A to DEBUG")
    kLogRegistry["a.A"].level = Level.DEBUG

    log.trace("setting level on package a.a to INFO")
    kLogRegistry["a.a"].level = Level.INFO

    runLogTest()

  }

  @Test
  fun test() {
    println("test()")

    log.trace { "trace with lazy message" }
    log.debug("debug message")
    log.info { "INFO MESSAGE" }
    log.warn {
      "A warning!"
    }
    val err = Exception("The exception message")
    log.error(err.message, err)

  }

  @Test
  fun testTime() {
    log.warn("time is: ${getTimeMillis()}")
  }

}