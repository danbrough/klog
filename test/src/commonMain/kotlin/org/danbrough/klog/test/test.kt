package org.danbrough.klog.test

import org.danbrough.klog.Utils
import org.danbrough.klog.cached
import org.danbrough.klog.klogFactory
import org.danbrough.klog.klogFactoryStandard
import org.danbrough.klog.propertyResolver


expect fun test()

suspend fun testMain(args: Array<String>) {
  println("running testMain() args: ${args.joinToString(",")}")


  //val cache = mutableMapOf<String, String>()
  val props = propertyResolver("_", {
    println("env lookup: $it")
    Utils.environment[it]
  }).cached()/*  .default { name, parent ->
      (parent ?: "GLOBAL_DEFAULT").also { println("returning default $it") }
    }
  */

  klogFactoryStandard.logger("ROOT").info { "root logger" }
  


  test()

}

