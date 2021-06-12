package com.iyxan23.eplk

import org.junit.Test

class LexerTest {

    private fun expectTokens(code: String, shouldBe: ArrayList<Token>) {
        val result = Lexer(code).doLexicalAnalysis()

        println("Error: ${result.error}")
        assert(result.error == null)
        println("Returned Tokens: ${result.tokens}\n")
        assert(result.tokens == shouldBe)
    }

    @Test
    fun stringTest() {
        expectTokens(
                "\"Hello World\"",
                arrayListOf(Token(Tokens.STRING_LITERAL, "Hello World"))
        )
    }

    @Test
    fun stringTest2() {
        expectTokens(
                " \n    \t\"Hello \\\"World\"\n    \t\n",
                arrayListOf(Token(Tokens.STRING_LITERAL, "Hello \"World"))
        )
    }

    @Test
    fun intTest() {
        expectTokens(
                "10",
                arrayListOf(Token(Tokens.INT_LITERAL, "10"))
        )
    }
}