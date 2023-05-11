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

val javaLangVersion = 17

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(javaLangVersion))
}

kotlin {
  jvmToolchain {
    languageVersion.set(JavaLanguageVersion.of(javaLangVersion))
  }
}



val props = Properties().apply {
  file("../versions.properties").inputStream().use { load(it) }
}


val androidVersion: String = props.getProperty("plugin.android")

dependencies {
  implementation(kotlin("gradle-plugin"))

  implementation("com.android.tools.build:gradle:$androidVersion")

  //implementation(Android.tools.build.gradlePlugin)
}

