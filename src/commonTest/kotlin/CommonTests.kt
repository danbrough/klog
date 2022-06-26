import a.A
import a.a.AA
import a.b.AB
import org.danbrough.klog.*
import kotlin.test.Test


class CommonTests {

  private fun runLogTest() {
    A().test()
    AA().test()
    AB().test()
  }

/*  @Test
  fun testLogConf() {
     val log =
      klog("a", Level.TRACE, KLogWriters.stdOut, KMessageFormatters.verbose.colored)

    log.info("testings logs ..")
    runLogTest()

    log.trace("setting level on class a.A to DEBUG")
    kLogRegistry["a.A"].level = Level.DEBUG

    log.trace("setting level on package a.a to INFO")
    kLogRegistry["a.a"].level = Level.INFO

    runLogTest()

  }*/


  @Test
  fun test() {
    println("test()")
    val log =
      klog("a", Level.TRACE, KLogWriters.stdOut, KMessageFormatters.verbose.colored)
    println("GOT LOG $log")
    log.trace("A trace message")
    /*log.trace { "trace with lazy message" }
    log.level = Level.DEBUG
    log.trace {
      throw Error("This should not be called")
    }
    log.debug("debug message")
    log.info { "INFO MESSAGE" }
    log.warn {
      "A warning!"
    }
    val err = Exception("The exception message")
    log.error(err.message, err)*/

  }

  /*
  @Test
  fun testTime() {
    log.warn("time is: ${getTimeMillis()}")
  }
*/

}