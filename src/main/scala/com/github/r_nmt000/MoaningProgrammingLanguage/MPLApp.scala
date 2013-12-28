package com.github.r_nmt000.MoaningProgrammingLanguage

object BrainfuckApplication {

  def main(args: Array[String]) =
    try {
      new BrainfuckRuntime(new BrainfuckParser).execute(args(0))
    } catch {
      case BrainfuckException(msg) => println(msg)
    }

}
