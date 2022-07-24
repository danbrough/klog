import org.jetbrains.kotlin.konan.target.KonanTarget

object BuildEnvironment {
  val KonanTarget.platformName: String
    get() = name.split('_').joinToString("") { it.capitalize() }.decapitalize()

}