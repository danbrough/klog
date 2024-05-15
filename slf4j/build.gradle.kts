import org.danbrough.klog.support.Constants
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
  id("java-library")
  alias(libs.plugins.kotlin.jvm)
  `maven-publish`
  signing
}

java {
  sourceCompatibility = Constants.JAVA_VERSION
  targetCompatibility = Constants.JAVA_VERSION
}

kotlin {

  compilerOptions {
    languageVersion = KotlinVersion.KOTLIN_1_9
    apiVersion = KotlinVersion.KOTLIN_1_9
    jvmTarget = Constants.JVM_TARGET
    //freeCompilerArgs = listOf("-Xexpect-actual-classes")
  }

}
repositories {
  mavenCentral()
}

dependencies {
  implementation(libs.slf4j.api)
  implementation(project(":core"))
  implementation(kotlin("stdlib-jdk8"))
  testImplementation(kotlin("test"))
}


publishing {
  publications {
    create<MavenPublication>("slf4j") {
      from(components["java"])
    }
  }
}