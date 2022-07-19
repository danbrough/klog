import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
}

repositories {
  mavenCentral()
  google()
}

kotlinDslPluginOptions {
  jvmTarget.set(provider { java.targetCompatibility.toString() })
}

dependencies {
  //implementation(gradleKotlinDsl())
  implementation(kotlin("gradle-plugin","1.7.10"))
  implementation("com.android.tools.build:gradle:7.3.0-beta05")
  //compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
  //implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
  /*implementation(kotlin("gradle-plugin","1.6.21"))
  implementation(kotlin("serialization"))
  implementation(gradleApi())
  implementation(gradleKotlinDsl())*/


  //implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:_")


}
tasks.withType(KotlinCompile::class) {
  kotlinOptions {
    this.jvmTarget = "11"
  }
}

kotlin {
/*
  jvmToolchain {
    check(this is JavaToolchainSpec)
    languageVersion.set(JavaLanguageVersion.of(8))

  }
*/

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
tasks.withType(KotlinCompile::class) {

  kotlinOptions {
    listOf(
      "kotlin.RequiresOptIn",
      "kotlin.ExperimentalStdlibApi",
      "kotlin.ExperimentalMultiplatform",

      //  "kotlinx.serialization.InternalSerializationApi",
    //  "kotlinx.serialization.ExperimentalSerializationApi",
      // "kotlinx.coroutines.ExperimentalCoroutinesApi",
      // "kotlin.time.ExperimentalTime",
    ).map { "-Xopt-in=$it" }.also {
      freeCompilerArgs = freeCompilerArgs + it
    }
  }
}
*/

