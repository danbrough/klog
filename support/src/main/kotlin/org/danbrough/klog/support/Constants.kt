package org.danbrough.klog.support

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget


object Constants {
	const val KLOG_PACKAGE = "org.danbrough.klog"
	val JAVA_VERSION = JavaVersion.VERSION_1_8
	val JVM_TARGET = JvmTarget.JVM_1_8

	object Android {
		const val COMPILE_SDK = 34
		const val MIN_SDK = 19
	}
}


