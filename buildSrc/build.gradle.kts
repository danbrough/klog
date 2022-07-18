import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
}

repositories {
  gradlePluginPortal()
  mavenCentral()
  google()
}

dependencies {
  //implementation(kotlin("gradle-plugin", "1.6.21"))

  implementation( "org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")

}


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


kotlinDslPluginOptions {
  jvmTarget.set(provider { java.targetCompatibility.toString() })
}

tasks.withType(KotlinCompile::class) {
  this.kotlinOptions {
    this.jvmTarget = "11"

  }
}

