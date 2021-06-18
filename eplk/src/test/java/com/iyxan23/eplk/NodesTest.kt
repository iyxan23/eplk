package com.iyxan23.eplk

import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.Lexer
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.nodes.BinOpNode
import com.iyxan23.eplk.nodes.UnaryOpNode
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

    // FIXME: 6/19/21 This shouldn't return 2
    //  Apparently, there is something wrong with the AST
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
}