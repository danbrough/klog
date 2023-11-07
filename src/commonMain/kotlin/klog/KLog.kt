package klog

import klog.Level
import klog.ROOT_TAG

interface KLog {
  val context: Context

  fun log(level:Level, message: String, error:Throwable?)
}

interface Context {
  val level: Level
  val tag: String

  interface Section

  val sections: Map<String, Section>
  fun toMutableContext(): MutableContext
}

data class MutableContext(
  override var level: Level = Level.TRACE,
  override var tag: String = ROOT_TAG,
  override val sections: MutableMap<String, Context.Section> = mutableMapOf()
) : Context {
  override fun toMutableContext() = copy()
}



interface FormattingOptions : Context.Section {
  val colored: Boolean
}

data class FormattingOptionsData(override var colored: Boolean = false) : FormattingOptions

const val SECTION_FORMATTING = "formatting"
fun MutableContext.formatting(conf: FormattingOptionsData.() -> Unit) {
  val section = sections[SECTION_FORMATTING] as? FormattingOptionsData ?: FormattingOptionsData()
  sections[SECTION_FORMATTING] = section.apply(conf)
}




