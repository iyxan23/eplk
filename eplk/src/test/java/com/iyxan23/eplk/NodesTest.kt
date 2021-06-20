package com.iyxan23.eplk

import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.Lexer
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.nodes.BinOpNode
import com.iyxan23.eplk.nodes.UnaryOpNode
import com.iyxan23.eplk.nodes.variable.VarDeclarationNode
import com.iyxan23.eplk.objects.EplkFloat
import com.iyxan23.eplk.objects.EplkInteger
import com.iyxan23.eplk.parser.Parser
import org.junit.Test

class NodesTest {

    val filename = "<TEST>"

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
}