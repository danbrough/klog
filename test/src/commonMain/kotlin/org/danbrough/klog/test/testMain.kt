package org.danbrough.klog.test


expect fun test()

/*
val log = kloggingStandard {
  formatter = defaultMessageFormatter
  coloredOutput = true
}.let { logger("TEST") }
*/


/*suspend fun coroutineTest() {
  log.debug { "coroutineTEst(): running at ${Clock.System.now()} thread:${Utils.getThreadName()}" }
  val scope = CoroutineScope(Dispatchers.Default)
  scope.launch {
    log.debug { "launched coroutine" }
    delay(2.seconds)
    log.debug { "launched coroutine finished thread:${Utils.getThreadName()}" }
  }

  delay(5.seconds)
}*/

interface Named {

  val name: String
  fun createCopy(newName: String): Named
}

abstract class PropertyResolver<T : Named>(
  val root: T, protected val nameDelimiter: String = "."
) {


  abstract operator fun get(name: String): T?
  abstract operator fun set(name: String, value: T)

  fun resolve(name: String): T {
    //println("resolve: $name")
    get(name)?.also {
      //println("got value: $it")
      return it
    }
    val names = name.split(nameDelimiter).toMutableList()
    val parentName = if (names.size > 1) names.let {
      it.removeLast()
      it.joinToString(nameDelimiter)
    } else return root
    //println("$name: parentName: $parentName")

    return resolve(parentName)
  }
}

open class TestResolver<T : Named>(root: T, nameDelimiter: String, val creator: (T, String) -> T) :
  PropertyResolver<T>(root, nameDelimiter) {
  private val cache = mutableMapOf<String, T>()
  override fun get(name: String): T? = cache[name]
  override fun set(name: String, value: T) {
    cache[name] = value
  }

  @Suppress("UNCHECKED_CAST")
  fun resolveOrCreate(name: String): T = get(name) ?: super.resolve(name).let {
    if (it.name != name) creator(it, name).also { copy ->
      set(copy.name, copy)
    } else it
  }/*
    override fun resolve(name: String, copier: (Named, String) -> T): Named =

  */


}

fun testMain(args: Array<String>) {
  println("running testMain() args: ${args.joinToString(",")}")

  var id = 0

  data class Thang(override val name: String, val age: Int = id++) : Named {
    override fun createCopy(newName: String): Named = copy(name = newName, age = this.age + 1)
  }

  val props = TestResolver(Thang("ROOT", 0), "_") { parent, name ->
    parent.createCopy(name) as Thang
  }
  props["ROOT_C"] = Thang("ROOT_C", 100)
  props["ANOTHER_ROOT"] = Thang("ANOTHER_ROOT", 1000)

  var key = "ROOT"
  println("$key = ${props.resolveOrCreate(key)}")
  key = "ROOT_A"
  println("$key = ${props.resolveOrCreate(key)}")
  key = "ROOT_A_B"
  println("$key = ${props.resolveOrCreate(key)}")
  key = "ROOT_A"
  println("$key = ${props.resolveOrCreate(key)}")
  key = "ROOT_C_A"
  println("$key = ${props.resolveOrCreate(key)}")
  key = "ANOTHER_ROOT_A_B"
  println("$key = ${props.resolveOrCreate(key)}")
  key = "ANOTHER_ROOT_A_B_C"
  println("$key = ${props.resolveOrCreate(key)}")
  key = "ANOTHER_ROOT_A"
  println("$key = ${props.resolveOrCreate(key)}")

  /*  log.trace { "trace()" }
    log.debug { "debug()" }
    log.info { "info()" }
    log.warn { "warn()" }
    log.error { "error()" }
    test()

    log.debug { $$"$HOME is $${Utils.environment["HOME"]}" }
    log.debug { $$"$HOMEZ is $${Utils.environment["HOMEZ"]}" }*/


}