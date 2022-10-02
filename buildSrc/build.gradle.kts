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
  implementation(kotlin("gradle-plugin", "1.7.10"))
  implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.7.10")
  implementation("com.android.tools.build:gradle:7.3.0")

  //implementation("org.jetbrains.dokka:dokka-gradle-plugin:_")
}


/*
kotlinDslPluginOptions {
  jvmTarget.set(provider { java.targetCompatibility.toString() })
}
*/

/*


kotlin {


  sourceSets.all {
    languageSettings {
      listOf(
        "kotlin.RequiresOptIn",
        "kotlin.ExperimentalStdlibApi",
        "kotlin.io.path.ExperimentalPathApi",
      ).forEach {
        optIn(it)
      }
    }
  }


}
*/


/*
tasks.withType(KotlinCompile::class) {
  this.kotlinOptions {
    this.jvmTarget = "11"

  }
}

*/
