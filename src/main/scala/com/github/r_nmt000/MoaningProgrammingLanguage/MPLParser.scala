package com.github.r_nmt000.MoaningProgrammingLanguage

import util.parsing.combinator._

// 式を訪問するビジター
trait ExpressionVisitor {
  def visit(e: Expression): Unit
}

// 式を表すトレイト
trait Expression {
  def accept(visitor: ExpressionVisitor): Unit = {
    visitor.visit(this)
  }
}

// 式の実装 これを使ってASTを構築する
case class IncrementPointerExpression extends Expression
case class DecrementPointerExpression extends Expression
case class IncrementMemoryAtPointerExpression extends Expression
case class DecrementMemoryAtPointerExpression extends Expression
case class OutputMemoryAtPointerExpression extends Expression
case class InputMemoryAtPointerExpression extends Expression
case class LoopExpression(expressions:List[Expression]) extends Expression

// Brainfuckパーサ
class BrainfuckParser extends RegexParsers {

  def parse(source:String) = parseAll(brainfuck, source)

  def brainfuck: Parser[List[Expression]] = rep(instruction)

  def instruction: Parser[Expression] = loop | token

  def token: Parser[Expression] = incrementPointer ||| decrementPointer |||
    incrementMemoryAtPointer ||| decrementMemoryAtPointer ||| outputMemoryAtPointer ||| inputMemoryAtPointer

  def incrementPointer: Parser[Expression] = "yeah!" ^^ {
    case ops => IncrementPointerExpression()
  }

  def decrementPointer: Parser[Expression] = "f**k!" ^^ {
    case ops => DecrementPointerExpression()
  }

  def incrementMemoryAtPointer: Parser[Expression] = "ah," ^^ {
    case ops => IncrementMemoryAtPointerExpression()
  }

  def decrementMemoryAtPointer: Parser[Expression] = "oh," ^^ {
    case ops => DecrementMemoryAtPointerExpression()
  }

  def outputMemoryAtPointer: Parser[Expression] = "comeon!" ^^ {
    case ops => OutputMemoryAtPointerExpression()
  }
  
  def inputMemoryAtPointer: Parser[Expression] = "mm..." ^^ {
    case ops => InputMemoryAtPointerExpression()
  }

  def loop: Parser[Expression] = "ohmygod!"~>brainfuck<~"god!" ^^ {
    case exprs => LoopExpression(exprs)
  }

}
