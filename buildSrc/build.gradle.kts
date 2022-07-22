import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
 // kotlin("multiplatform") version "1.7.10"

}

repositories {
  gradlePluginPortal()
  mavenCentral()
  google()
}

dependencies {
  implementation(kotlin("gradle-plugin", "1.7.10"))
  implementation("com.android.tools.build:gradle:7.2.1")
  implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.7.10")

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
