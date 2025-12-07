import org.danbrough.klog.support.Constants
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
  id("java-library")
  alias(libs.plugins.kotlin.jvm)
  `maven-publish`
}


java {
  sourceCompatibility = Constants.JAVA_VERSION
  targetCompatibility = Constants.JAVA_VERSION
  withSourcesJar()
}


kotlin {

  compilerOptions {
    apiVersion = KotlinVersion.KOTLIN_2_1
    jvmTarget = Constants.JVM_TARGET

    /*languageVersion = KotlinVersion.KOTLIN_2_1
    apiVersion = KotlinVersion.KOTLIN_2_1
    jvmTarget = Constants.JVM_TARGET*/
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