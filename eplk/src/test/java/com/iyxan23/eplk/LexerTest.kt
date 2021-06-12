package com.iyxan23.eplk

import org.junit.Test
import org.junit.Assert.*

class LexerTest {
    @Test
    fun stringTest() {
        val code = "\"Hello World\""
        val result = Lexer(code).doLexicalAnalysis()

        val shouldBe = arrayListOf(Token(Tokens.STRING_LITERAL, "Hello World"))

        println(result.error)
        assert(result.error == null)
        println(result.tokens)
        assert(result.tokens == shouldBe)
    }

    @Test
    fun stringTest2() {
        val code = " \n    \t\"Hello \\\"World\"\n    \t\n"
        val result = Lexer(code).doLexicalAnalysis()

        val shouldBe = arrayListOf(Token(Tokens.STRING_LITERAL, "Hello \"World"))

        println(result.error)
        assert(result.error == null)
        println(result.tokens)
        assert(result.tokens == shouldBe)
    }
}