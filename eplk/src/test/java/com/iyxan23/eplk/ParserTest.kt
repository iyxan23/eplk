package com.iyxan23.eplk

import com.iyxan23.eplk.lexer.Lexer
import com.iyxan23.eplk.parser.Parser
import com.iyxan23.eplk.parser.nodes.BinOpNode
import com.iyxan23.eplk.parser.nodes.NumberNode
import org.junit.Test

class ParserTest {
    @Test
    fun binOpTest() {
        val code = "1 + 2"
        val tokens = Lexer("TEST", code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(result.error == null)
    }

    @Test
    fun unaryTest() {
        val code = "-1 + 2"
        val tokens = Lexer("TEST", code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(result.error == null)
    }

    @Test
    fun syntaxErrorTest() {
        val code = "1 2 3 /"
        val tokens = Lexer("TEST", code).doLexicalAnalysis().tokens!!
        val result = Parser(tokens).parse()

        assert(result.error != null)
    }
}