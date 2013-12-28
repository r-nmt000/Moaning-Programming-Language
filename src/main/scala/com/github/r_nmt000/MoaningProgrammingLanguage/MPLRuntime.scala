package com.github.r_nmt000.MoaningProgrammingLanguage
import java.io._
import org.apache.commons.io.FileUtils

case class BrainfuckException(msg:String) extends RuntimeException

class BrainfuckRuntime(parser: BrainfuckParser, size: Int) {
  require(size > 0)

  val memory = Array.fill(size)(0)

  var pointer = 0

  var counter = 0

  val evaluator = new Evaluator()

  def this(parser: BrainfuckParser) = this (parser, 3000)

  // Brainfuckのスクリプトを実行する
  def execute(filename: String) {
    try{
      val source = FileUtils.readFileToString( new File(filename))
      val parseResult = parser.parse(source)
      if (parseResult.successful) {
        evaluateExpressions(parseResult.get)
        println
      } else {
        throw new BrainfuckException("parse error")
      }
    }catch{
      case e: FileNotFoundException => println("file not found")
    }
  }

  // ASTを評価するビジター
  class Evaluator extends ExpressionVisitor {

    override def visit(expression: Expression): Unit = expression match {
      case IncrementPointerExpression() => incrementPointer
      case DecrementPointerExpression() => decrementPointer
      case IncrementMemoryAtPointerExpression() => incrementMemoryAtPointer
      case DecrementMemoryAtPointerExpression() => decrementMemoryAtPointer
      case OutputMemoryAtPointerExpression() => outputMemoryAtPointer
      case InputMemoryAtPointerExpression() => inputMemoryAtPointer
      case LoopExpression(expressions: List[Expression]) => loop(expressions)
  }

    }

    // 以下、ランタイムが持つAPI

    private def validateRange =
      if (counter > size) throw new BrainfuckException("limit over")

    private def readMemory = {
      validateRange
      memory(pointer)
  }

    private def writeMemory(b: Int) = {
      validateRange
      memory(pointer) = b
    }


    private def incrementPointer {
      validateRange
      pointer += 1
      counter += 1
    }

    private def decrementPointer {
      validateRange
      pointer -= 1
      counter += 1
    }

    private def incrementMemoryAtPointer {
      writeMemory(readMemory + 1)
    }

    private def decrementMemoryAtPointer {
      writeMemory(readMemory - 1)
    }

    private def outputMemoryAtPointer {
      print(readMemory.toChar)
    }

    private def inputMemoryAtPointer {
      writeMemory(readChar.toInt)
    }


    private def loop(expressions: List[Expression]) {
      while (readMemory != 0) {
        evaluateExpressions(expressions)
    }
      }

    private def evaluateExpressions(expressions: List[Expression]) {
      expressions.foreach(_.accept(evaluator))
    }


    }
