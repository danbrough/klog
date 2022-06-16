@file:Suppress("MemberVisibilityCanBePrivate")

import Build_version_gradle.BuildVersion.buildVersion

//@file:Suppress("MemberVisibilityCanBePrivate")


object BuildVersion {


  var buildVersion = 0
  var buildVersionOffset: Int = 0
  var buildVersionFormat: String = "0.0.1-alpha%02d"


  val buildVersionName: String
    get() = buildVersionFormat.format(buildVersion)


  private fun Project.getProjectProperty(key: String, defValue: String): String =
    this.rootProject.findProperty(key)?.toString()?.trim() ?: run {
      rootProject.file("gradle.properties").appendText("\n$key=$defValue\n")
      defValue
    }


  fun init(project: Project) {

    buildVersion = project.getProjectProperty("build.version", "0").toInt()
    buildVersionFormat = project.getProjectProperty("build.version.format", "0.0.1-alpha%02d")
    buildVersionOffset = project.getProjectProperty("build.version.offset", "0").toInt()

  }
}

class BuildVersionPlugin @javax.inject.Inject constructor() : Plugin<Project> {

  override fun apply(project: Project) {

    println("APPLYING BUILD VERSON PLUGIN")
    BuildVersion.init(project)


    project.tasks.register("buildVersionIncrement") {
      println("RUNNING BUILD VERSION INCREMENT")
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
      // buildVersionName()
      // nextBuildVersionName()
    }


  }
}


/*private fun Project.buildVersionName() = tasks.register("buildVersionName") {
  println(buildVersionName)
}

private fun Project.nextBuildVersionName() = tasks.register("nextBuildVersionName") {
  println(buildVersionFormat.format(buildVersion + 1))
}*/

/*
fun Project.buildVersionTasks() {
  println("REGISTRING BUILD VERSION INCREMENT")
  tasks.register("buildVersionIncrement") {
    println("RUNNING BUILD VERSION INCREMENT")
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
    // buildVersionName()
    // nextBuildVersionName()
  }


}

*/
