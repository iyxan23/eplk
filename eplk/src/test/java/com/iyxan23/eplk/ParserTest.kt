package com.iyxan23.eplk

import com.iyxan23.eplk.lexer.Lexer
import com.iyxan23.eplk.parser.Parser
import org.junit.Test

class ParserTest {
    @Test
    fun test1() {
        val code = "1 + 2"
        val tokens = Lexer("TEST", code).doLexicalAnalysis().tokens!!
        assert(Parser(tokens).parse().error == null)
    }
}