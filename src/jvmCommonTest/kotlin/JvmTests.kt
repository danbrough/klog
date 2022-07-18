import org.danbrough.klog.*
import kotlin.test.Test

class JvmTests {

  //private val log = klog(Level.TRACE, KLogWriters.stdOut, KMessageFormatters.verbose.colored)

  @Test
  fun listProps() {
    listSysProps()
  }
}
