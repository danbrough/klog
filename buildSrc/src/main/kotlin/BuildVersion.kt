import org.gradle.api.Project

object BuildVersion {

  val Project.buildVersion: Int
    get() = rootProject.findProperty("build.version")?.toString()?.trim()?.toInt() ?: run {
      rootProject.file("gradle.properties").appendText("\nbuild.version=1\n")
      1
    }

  val Project.buildVersionOffset: Int
    get() = rootProject.findProperty("build.version.offset")?.toString()?.trim()?.toInt() ?: run {
      rootProject.file("gradle.properties").appendText("\nbuild.version.offset=0\n")
      0
    }

  val Project.buildVersionFormat: String
    get() = rootProject.findProperty("build.version.format")?.toString()?.trim() ?: run {
      rootProject.file("gradle.properties").appendText("\nbuild.version.format=0.0.0-beta%02d\n")
      "0.0.0-beta%02d"
    }


  val Project.buildVersionName: String
    get() = buildVersionFormat.format(buildVersion)


  fun Project.buildVersionIncrement() = tasks.register("buildVersionIncrement") {
    val currentVersion = buildVersion
    println("current version $currentVersion")
    rootProject.file("gradle.properties").readLines().map {
      if (it.contains("build.version="))
        "build.version=${currentVersion + 1}"
      else it
    }.also { lines ->
      rootProject.file("gradle.properties").writer().use { writer ->
        lines.forEach {
          writer.write("$it\n")
        }
      }
    }
  }

  fun Project.buildVersionName() = tasks.register("buildVersionName") {
    println(buildVersionName)
  }
}