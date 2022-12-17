import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

plugins {
  `kotlin-dsl`
}

repositories {
  gradlePluginPortal()
  mavenCentral()
  google()
}


val props = Properties().apply {
  file("../versions.properties").inputStream().use { load(it) }
}


val androidVersion:String = props.getProperty("plugin.android")

dependencies {
  implementation(kotlin("gradle-plugin"))
  implementation("com.android.tools.build:gradle:$androidVersion")
}

