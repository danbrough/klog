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

val kotlinVersion: String = props.getProperty("version.kotlin")
val androidVersion:String = props.getProperty("plugin.android")

dependencies {
  implementation(kotlin("gradle-plugin", kotlinVersion))
  //implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.7.10")
  implementation("com.android.tools.build:gradle:$androidVersion")
}

