package com.iyxan23.eplk

import com.iyxan23.eplk.lexer.Lexer
import com.iyxan23.eplk.parser.Parser
import com.iyxan23.eplk.nodes.Node
import org.junit.Test

class ParserTest {

    private val filename = "<TEST>"

    private fun expectNodes(code: String, expectation: Node) {
        val tokens = Lexer(filename, code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        // TODO: 6/16/21
        //  don't put the entire node tree as the truth data, make a new function that
        //  checks the important part of the node
        assert(result.node == expectation)
    }

    @Test
    fun binOpTest() {
        val code = "1 + 2"
        val tokens = Lexer(filename, code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(!result.hasError)

        println(Utils.prettyPrintNode(result.node!!))
    }

    @Test
    fun unaryTest() {
        val code = "-1 + 2"
        val tokens = Lexer(filename, code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(!result.hasError)

        println(Utils.prettyPrintNode(result.node!!))
    }

    @Test
    fun parenthesesTest() {
        val code = "(1 + 2) * 3"
        val tokens = Lexer(filename, code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(!result.hasError)

        println(Utils.prettyPrintNode(result.node!!))
    }

    @Test
    fun powTest() {
        val code = "3 ^ 3"
        val tokens = Lexer(filename, code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(!result.hasError)

        println(Utils.prettyPrintNode(result.node!!))
    }

    @Test
    fun variableDeclarationTest() {
        val code = "var hello_world = 1 + 1"
        val tokens = Lexer(filename, code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(!result.hasError)

        println(Utils.prettyPrintNode(result.node!!))
    }

    @Test
    fun variableAccessTest() {
        val code = "1 + hello_world"
        val tokens = Lexer(filename, code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(!result.hasError)

        println(Utils.prettyPrintNode(result.node!!))
    }

    @Test
    fun comparisonExpressionTest() {
        val code = "5 + 12 / variable > 10 + 5 * variable"
        val tokens = Lexer(filename, code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(!result.hasError) { println(result.error!!.toString(code)) }

        println(Utils.prettyPrintNode(result.node!!))
    }

    @Test
    fun ifTest() {
        val code = "var hello_world = if (1 == 1) 10 else 0"
        val tokens = Lexer(filename, code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(!result.hasError) { println(result.error!!.toString(code)) }

        println(Utils.prettyPrintNode(result.node!!))
    }

    @Test
    fun ifTest2() {
        val code = "var hello_world = if (1 == 1) 10 elif (2 == 2) 2 else 0"
        val tokens = Lexer(filename, code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(!result.hasError) { println(result.error!!.toString(code)) }

        println(Utils.prettyPrintNode(result.node!!))
    }

    @Test
    fun ifTest3() {
        val code = "var hello_world = if (1 == 1) 10 elif (2 == 2) 2 elif (3 == 3) 3 else 0"
        val tokens = Lexer(filename, code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(!result.hasError) { println(result.error!!.toString(code)) }

        println(Utils.prettyPrintNode(result.node!!))
    }

    @Test
    fun forTest() {
        val code = "for (var b = 0; b < 10; var b = b + 1) 1 + 1"
        val tokens = Lexer(filename, code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(!result.hasError) { println(result.error!!.toString(code)) }

        println(Utils.prettyPrintNode(result.node!!))
    }

    @Test
    fun syntaxErrorTest() {
        val code = "1 2 3 /"
        val tokens = Lexer(filename, code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(result.hasError)
    }

    @Test
    fun syntaxErrorTest2() {
        val code = "-(-1"
        val tokens = Lexer(filename, code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(result.hasError)
    }
}