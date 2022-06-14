import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.creating
import java.net.URI
import java.io.File

object ProjectProperties {
  const val SDK_VERSION = 31
  const val MIN_SDK_VERSION = 23
  const val BUILD_TOOLS_VERSION = "31.0.0"
  var KOTLIN_VERSION = "1.7.0"
  val JAVA_VERSION = JavaVersion.VERSION_11

  val IDE_ACTIVE = System.getProperty("idea.active","false").toBoolean()

  const val KOTLIN_JVM_VERSION = "11"


  val Project.LOCAL_MAVEN_REPO: URI
    get() = project.findProperty("LOCAL_MAVEN_REPO")?.toString()?.let { URI(it) } ?: project.buildDir.resolve("maven")
      .toURI()


  val Project.projectGroup: String
    get() = rootProject.findProperty("project.group")!!.toString().trim()


}


