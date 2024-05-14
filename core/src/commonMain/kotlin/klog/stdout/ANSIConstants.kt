@file:Suppress("MemberVisibilityCanBePrivate")

package klog.stdout

import klog.Logger

object ANSIConstants {
  const val ESC_START = "\u001b["
  const val ESC_END = "m"
  const val BOLD = "1;"
  const val RESET = "${ESC_START}0${ESC_END}"
  const val BLACK_FG = "30"
  const val RED_FG = "31"
  const val GREEN_FG = "32"
  const val YELLOW_FG = "33"
  const val BLUE_FG = "34"
  const val MAGENTA_FG = "35"
  const val CYAN_FG = "36"
  const val WHITE_FG = "37"
  const val DEFAULT_FG = "39"
}


val Logger.Level.color: String
  get() = when (this) {
    Logger.Level.ERROR -> ANSIConstants.BOLD + ANSIConstants.RED_FG
    Logger.Level.WARN -> ANSIConstants.YELLOW_FG
    Logger.Level.INFO -> ANSIConstants.GREEN_FG
    Logger.Level.DEBUG -> ANSIConstants.CYAN_FG
    Logger.Level.TRACE -> ANSIConstants.MAGENTA_FG
    else -> ANSIConstants.DEFAULT_FG
  }

fun Logger.Level.colored(s: String) =
  "${ANSIConstants.ESC_START}${color}m$s${ANSIConstants.RESET}"
