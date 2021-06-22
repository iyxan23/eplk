package com.iyxan23.eplk

import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.Lexer
import com.iyxan23.eplk.nodes.*
import com.iyxan23.eplk.nodes.variable.VarDeclarationNode
import com.iyxan23.eplk.objects.EplkBoolean
import com.iyxan23.eplk.objects.EplkFloat
import com.iyxan23.eplk.objects.EplkInteger
import com.iyxan23.eplk.objects.EplkVoid
import com.iyxan23.eplk.parser.Parser
import org.junit.Test

class NodesTest {

    private val filename = "<TEST>"

    @Test
    fun testUnaryOpNode() {
        val lexerResult = Lexer(filename, "-1").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as UnaryOpNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val resultVisit = parseResult.visit(Scope(filename))

        assert(!resultVisit.hasError)
        assert(resultVisit.value is EplkInteger)
        assert((resultVisit.value as EplkInteger).value == -1)
    }

    @Test
    fun testUnaryOpNode2() {
        val lexerResult = Lexer(filename, "-(-(-(-1)))").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as UnaryOpNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val resultVisit = parseResult.visit(Scope(filename))

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkInteger)
        assert((resultVisit.value as EplkInteger).value == 1)
    }

    @Test
    fun testUnaryOpNode3() {
        val lexerResult = Lexer(filename, "!!!!false").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as UnaryOpNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val resultVisit = parseResult.visit(Scope(filename))

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkBoolean)
        assert(!(resultVisit.value as EplkBoolean).value)
    }

    @Test
    fun testBinOpNode() {
        val lexerResult = Lexer(filename, "1 + 1").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as BinOpNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val resultVisit = parseResult.visit(Scope(filename))

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkInteger)
        assert((resultVisit.value as EplkInteger).value == 2)
    }

    @Test
    fun testBinOpNode2() {
        val lexerResult = Lexer(filename, "1 + 2 * 3 / 4").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as BinOpNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val resultVisit = parseResult.visit(Scope(filename))

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkFloat)
        println((resultVisit.value as EplkFloat).value)
        assert((resultVisit.value as EplkFloat).value == 2.5f)
    }

    @Test
    fun testBinOpNode3() {
        val lexerResult = Lexer(filename, "3 ^ 3").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as BinOpNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val resultVisit = parseResult.visit(Scope(filename))

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkFloat)
        println((resultVisit.value as EplkFloat).value)
        assert((resultVisit.value as EplkFloat).value == 27f)
    }

    @Test
    fun testBinOpNode4() {
        val lexerResult = Lexer(filename, "3 ^ (1 + 2)").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as BinOpNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val resultVisit = parseResult.visit(Scope(filename))

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkFloat)
        println((resultVisit.value as EplkFloat).value)
        assert((resultVisit.value as EplkFloat).value == 27f)
    }

    @Test
    fun testBinOpNode5() {
        val lexerResult = Lexer(filename, "true && false || !true").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as BinOpNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val resultVisit = parseResult.visit(Scope(filename))

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkBoolean)
        assert(!(resultVisit.value as EplkBoolean).value)
    }

    @Test
    fun testBinOpNode6Comparison() {
        val lexerResult = Lexer(filename, "true == true && !(false != true)").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as BinOpNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val resultVisit = parseResult.visit(Scope(filename))

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkBoolean)
        assert(!(resultVisit.value as EplkBoolean).value)
    }

    @Test
    fun testBinOpNodeParentheses() {
        val lexerResult = Lexer(filename, "(1 + 2) * 3").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as BinOpNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val resultVisit = parseResult.visit(Scope(filename))

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkInteger)
        assert((resultVisit.value as EplkInteger).value == 9)
    }

    @Test
    fun variableDeclarationTest() {
        val lexerResult = Lexer(filename, "var hello_world = 10 + 10 / 10").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as VarDeclarationNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val scope = Scope(filename)

        val resultVisit = parseResult.visit(scope)

        // Check if the variable hello_world is in the scope's symbol table
        assert(scope.symbolTable.variables.containsKey("hello_world"))
        assert(scope.symbolTable.variables["hello_world"] is EplkFloat)
        assert((scope.symbolTable.variables["hello_world"] as EplkFloat).value == 11f)

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkFloat)
        assert((resultVisit.value as EplkFloat).value == 11f)
    }

    @Test
    fun variableAccessTest() {
        val lexerResult = Lexer(filename, "5 + hello_world").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as BinOpNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val scope = Scope(filename)

        // Set the variable hello_world to be 15
        scope.symbolTable.variables["hello_world"] = EplkInteger(15, scope)

        val resultVisit = parseResult.visit(scope)

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkInteger)
        assert((resultVisit.value as EplkInteger).value == 20)
    }

    @Test
    fun ifTest() {
        val lexerResult = Lexer(filename, "if (1 == 1) 5 else 0").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as IfNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val resultVisit = parseResult.visit(Scope(filename))

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkInteger)
        assert((resultVisit.value as EplkInteger).value == 5)
    }

    @Test
    fun elifTest() {
        val lexerResult = Lexer(filename, "if (1 == 2) 5 elif (1 == 1) 7 else 0").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as IfNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val resultVisit = parseResult.visit(Scope(filename))

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkInteger)
        assert((resultVisit.value as EplkInteger).value == 7)
    }

    @Test
    fun multipleElifTest() {
        val lexerResult = Lexer(filename, "if (1 == 2) 5 elif (1 == 3) 7 elif (1 == 1) 9 else 0").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as IfNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val resultVisit = parseResult.visit(Scope(filename))

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkInteger)
        assert((resultVisit.value as EplkInteger).value == 9)
    }

    @Test
    fun elseTest() {
        val lexerResult = Lexer(filename, "if (1 == 2) 5 elif (1 == 3) 7 elif (1 == 4) 9 else 0").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as IfNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val resultVisit = parseResult.visit(Scope(filename))

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkInteger)
        assert((resultVisit.value as EplkInteger).value == 0)
    }

    @Test
    fun forTest() {
        val lexerResult = Lexer(filename, "for (var index = 0; index < 10; var index = index + 1) var number = number + 10").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as ForNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val scope = Scope(filename)

        scope.symbolTable.variables["number"] = EplkInteger(0, scope)

        val resultVisit = parseResult.visit(scope)

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkVoid)

        assert((scope.symbolTable.variables["number"] as EplkInteger).value == 100)
    }

    @Test
    fun whileTest() {
        val lexerResult = Lexer(filename, "while (number < 100) var number = number ^ 2").doLexicalAnalysis()
        val parseResult = Parser(lexerResult.tokens!!).parse().node as WhileNode

        println("Lexer result: $lexerResult")
        println("Parse result: $parseResult")

        val scope = Scope(filename)

        scope.symbolTable.variables["number"] = EplkInteger(2, scope)

        val resultVisit = parseResult.visit(scope)

        assert(!resultVisit.hasError) { println(resultVisit.error) }
        assert(resultVisit.value is EplkVoid)

        assert((scope.symbolTable.variables["number"] as EplkFloat).value == 256f)
    }
}