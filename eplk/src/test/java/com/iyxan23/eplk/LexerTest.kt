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

    private fun expectError(code: String, errorName: String) {
        val result = Lexer(code).doLexicalAnalysis()

        println("Error: ${result.error}")
        println("Returned Tokens: ${result.tokens}\n")
        assert(result.error != null)
        assert(result.error!!.name == errorName)
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
    fun stringTest3() {
        expectTokens(
                "\"Hello \\n World\"",
                arrayListOf(Token(Tokens.STRING_LITERAL, "Hello \n World"))
        )
    }

    @Test
    fun intTest() {
        expectTokens(
                "10",
                arrayListOf(Token(Tokens.INT_LITERAL, "10"))
        )
    }

    @Test
    fun intTest2() {
        expectTokens(
                "   \n\n \t  1234567890 \t\n    \t\t",
                arrayListOf(Token(Tokens.INT_LITERAL, "1234567890"))
        )
    }

    @Test
    fun illegalCharacterError() {
        // TODO: 6/12/21 change this to some character we wont use in future implementation
        expectError(
                ":",
                "IllegalCharacter"
        )
    }
}