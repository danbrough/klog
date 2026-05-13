package org.danbrough.klog

import kotlin.time.Clock

fun main(args: Array<String>) {
  console.info("Hello world! at ${Clock.System.now()}")
}