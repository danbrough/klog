package  org.danbrough.klog.tests


import a.A
import a.a.AA
import a.b.AB
import org.danbrough.klog.*
import kotlin.test.Test
//import kotlinx.coroutines.runBlocking
//import kotlinx.coroutines.Dispatchers

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

  /*  private val log by lazy {
      println("CREATING A LOG!")
      klog(
        level = Level.TRACE,
        messageFormatter = KMessageFormatters.verbose.colored,
        writer = KLogWriters.stdOut
      )
    }*/
  private val log = klog(Level.TRACE, KLogWriters.stdOut, KMessageFormatters.verbose.colored)


  @Test
  fun test1() {
    println("test1()")
    println("klogname: ${this::class.klogName()}")
    println("LOG: $log")

    log.info("an info message")

    log.trace {
      "A lazy trace message"
    }



/*    runBlocking(Dispatchers.Default) {
      log.info("inside coroutine")
    }*/
  }


  @Test
  fun testTime() {
    log.warn("time is: ${getTimeMillis()}")
  }


}