package org.danbrough.klog

/*
//typealias LogWriter = (level: Level, name: String, message: String, t: Throwable?) -> Unit
interface KLogWriter {
  fun writeLog(conf: KLogConfiguration, level: Level, name: String, message: String, t: Throwable?)
*/
//}


typealias KLogWriter = (conf: KLogConfiguration, level: Level, name: String, message: String, t: Throwable?) -> Unit