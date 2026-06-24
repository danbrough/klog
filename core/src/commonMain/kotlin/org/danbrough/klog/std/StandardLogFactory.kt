@file:Suppress("MemberVisibilityCanBePrivate")

package org.danbrough.klog.std

import org.danbrough.klog.KLogConfiguration
import org.danbrough.klog.KLogFactory
import org.danbrough.klog.KLogWriter
import org.danbrough.klog.Level


fun KLogConfiguration.colorString(level: Level, message: String): String =
  if (coloredOutput) level.colored(message) else message


val StdOutWriter = object : KLogWriter {
  override fun writeLog(
    conf: KLogConfiguration, level: Level, name: String, message: String, t: Throwable?
  ) {
    println(conf.colorString(level, "$level $name $message conf: $conf"))
  }
}


object StandardLogFactory : KLogFactory(KLogConfiguration(StdOutWriter))
