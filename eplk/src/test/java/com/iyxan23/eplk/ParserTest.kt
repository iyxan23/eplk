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
    }

    @Test
    fun unaryTest() {
        val code = "-1 + 2"
        val tokens = Lexer(filename, code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(!result.hasError)
    }

    @Test
    fun parenthesesTest() {
        val code = "(1 + 2) * 3"
        val tokens = Lexer(filename, code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(!result.hasError)
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