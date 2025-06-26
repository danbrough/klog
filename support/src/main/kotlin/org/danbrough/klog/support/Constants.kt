package org.danbrough.klog.support

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget


object Constants {
  const val KLOG_PACKAGE = "org.danbrough.klog"
  val JAVA_VERSION = JavaVersion.VERSION_11
  val JVM_TARGET = JvmTarget.JVM_11

  object Android {
    const val COMPILE_SDK = 36
    const val MIN_SDK = 19
  }
}


