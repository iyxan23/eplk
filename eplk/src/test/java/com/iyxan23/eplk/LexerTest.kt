package com.iyxan23.eplk

import com.iyxan23.eplk.errors.IllegalCharacterError
import com.iyxan23.eplk.errors.EplkError
import com.iyxan23.eplk.errors.SyntaxError
import com.iyxan23.eplk.lexer.Lexer
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.lexer.models.Token
import org.junit.Test

class LexerTest {

    private val filename = "<TEST>"

    private fun expectTokens(code: String, shouldBe: ArrayList<Any>) {
        val result = Lexer(filename, code).doLexicalAnalysis()

        println("Error: ${result.error}")
        assert(result.error == null)
        println("Returned Tokens: ${result.tokens}\n")
        checkTokens(result.tokens!!, shouldBe)
    }

    private fun expectError(code: String, expectedError: EplkError) {
        val result = Lexer(filename, code).doLexicalAnalysis()

        println("Error: ${result.error}")
        println("Returned Tokens: ${result.tokens}\n")
        assert(result.error != null)
        assert(result.error!! == expectedError)
    }

    private fun checkTokens(tokens: ArrayList<Token>, expect: ArrayList<Any>) {
        var index = 0
        tokens.forEach { token ->
            assert(token.token == expect[index] as Tokens)
            index++

            assert(token.value == expect[index] as String?)
            index++
        }
    }

// =================================================================================================

    @Test
    fun stringTest() {
        expectTokens(
                "\"Hello World\"",
                arrayListOf(
                    Tokens.STRING_LITERAL, "Hello World",
                    Tokens.EOF, null
                ) as ArrayList<Any>
        )
    }

    @Test
    fun stringTest2() {
        expectTokens(
                " \n    \t\"Hello \\\"World\"\n    \t\n",
                arrayListOf(
                    Tokens.STRING_LITERAL, "Hello \"World",
                    Tokens.EOF, null
                ) as ArrayList<Any>
        )
    }

    @Test
    fun stringTest3() {
        expectTokens(
                "\"Hello \\n World\"",
                arrayListOf(
                    Tokens.STRING_LITERAL, "Hello \n World",
                    Tokens.EOF, null
                ) as ArrayList<Any>
        )
    }

    @Test
    fun intTest() {
        expectTokens(
                "10",
                arrayListOf(
                    Tokens.INT_LITERAL, "10",
                    Tokens.EOF, null
                ) as ArrayList<Any>
        )
    }

    @Test
    fun intTest2() {
        expectTokens(
                "   \n\n \t  1234567890 \t\n    \t\t",
                arrayListOf(
                    Tokens.INT_LITERAL, "1234567890",
                    Tokens.EOF, null
                ) as ArrayList<Any>
        )
    }

    @Test
    fun floatTest() {
        expectTokens(
                "1.5",
                arrayListOf(
                    Tokens.FLOAT_LITERAL, "1.5",
                    Tokens.EOF, null
                ) as ArrayList<Any>
        )
    }

    @Test
    fun floatTest2() {
        expectTokens(
                "  \t \t \n\n  12312302433.51434234\n\n  ",
                arrayListOf(
                    Tokens.FLOAT_LITERAL, "12312302433.51434234",
                    Tokens.EOF, null
                ) as ArrayList<Any>
        )
    }

    @Test
    fun floatTest3() {
        expectTokens(
                "0.5",
                arrayListOf(
                    Tokens.FLOAT_LITERAL, "0.5",
                    Tokens.EOF, null
                ) as ArrayList<Any>
        )
    }

    @Test
    fun charactersTest() {
        expectTokens(
                "+-*/^()",
                arrayListOf(
                    Tokens.PLUS, null,
                    Tokens.MINUS, null,
                    Tokens.MUL, null,
                    Tokens.DIV, null,
                    Tokens.POW, null,
                    Tokens.PAREN_OPEN, null,
                    Tokens.PAREN_CLOSE, null,
                    Tokens.EOF, null,
                ) as ArrayList<Any>
        )
    }

    @Test
    fun identifierTest() {
        expectTokens(
            "hello_world",
            arrayListOf(
                Tokens.IDENTIFIER, "hello_world",
                Tokens.EOF, null
            ) as ArrayList<Any>
        )
    }

    @Test
    fun keywordTest() {
        expectTokens(
            "var",
            arrayListOf(
                Tokens.KEYWORD, "var",
                Tokens.EOF, null
            ) as ArrayList<Any>
        )
    }

    @Test
    fun variableTest() {
        expectTokens(
            "var hello_world = 1 + 1",
            arrayListOf(
                Tokens.KEYWORD, "var",
                Tokens.IDENTIFIER, "hello_world",
                Tokens.EQUAL, null,
                Tokens.INT_LITERAL, "1",
                Tokens.PLUS, null,
                Tokens.INT_LITERAL, "1",
                Tokens.EOF, null
            ) as ArrayList<Any>
        )
    }

    @Test
    fun variableAccessTest() {
        expectTokens(
            "1 + hello_world",
            arrayListOf(
                Tokens.INT_LITERAL, "1",
                Tokens.PLUS, null,
                Tokens.IDENTIFIER, "hello_world",
                Tokens.EOF, null
            ) as ArrayList<Any>
        )
    }

    @Test
    fun comparisonOperatorsTest() {
        expectTokens(
            "= == != > < >= <= ! || &&",
            arrayListOf(
                Tokens.EQUAL, null,
                Tokens.DOUBLE_EQUALS, null,
                Tokens.NOT_EQUAL, null,
                Tokens.GREATER_THAN, null,
                Tokens.LESSER_THAN, null,
                Tokens.GREATER_OR_EQUAL_THAN, null,
                Tokens.LESSER_OR_EQUAL_THAN, null,
                Tokens.NOT, null,
                Tokens.OR, null,
                Tokens.AND, null,
                Tokens.EOF, null,
            ) as ArrayList<Any>
        )
    }

    @Test
    fun comparisonExpressionTest() {
        expectTokens(
            "5 + 12 / variable > 10 + 5 * variable",
            arrayListOf(
                Tokens.INT_LITERAL, "5",
                Tokens.PLUS, null,
                Tokens.INT_LITERAL, "12",
                Tokens.DIV, null,
                Tokens.IDENTIFIER, "variable",
                Tokens.GREATER_THAN, null,
                Tokens.INT_LITERAL, "10",
                Tokens.PLUS, null,
                Tokens.INT_LITERAL, "5",
                Tokens.MUL, null,
                Tokens.IDENTIFIER, "variable",
                Tokens.EOF, null,
            ) as ArrayList<Any>
        )
    }

    @Test
    fun ifTest() {
        expectTokens(
            "if (something) { } else { }",
            arrayListOf(
                Tokens.IF, null,

                Tokens.PAREN_OPEN, null,
                Tokens.IDENTIFIER, "something",
                Tokens.PAREN_CLOSE, null,

                Tokens.BRACKET_OPEN, null,
                Tokens.BRACKET_CLOSE, null,

                Tokens.ELSE, null,

                Tokens.BRACKET_OPEN, null,
                Tokens.BRACKET_CLOSE, null,

                Tokens.EOF, null,
            ) as ArrayList<Any>
        )
    }

    @Test
    fun forTest() {
        expectTokens(
            "for (var a = 0; a < 10; var a = a + 1)",
            arrayListOf(
                Tokens.FOR, null,

                Tokens.PAREN_OPEN, null,

                Tokens.KEYWORD, "var",
                Tokens.IDENTIFIER, "a",
                Tokens.EQUAL, null,
                Tokens.INT_LITERAL, "0",

                Tokens.SEMICOLON, null,

                Tokens.IDENTIFIER, "a",
                Tokens.LESSER_THAN, null,
                Tokens.INT_LITERAL, "10",

                Tokens.SEMICOLON, null,

                Tokens.KEYWORD, "var",
                Tokens.IDENTIFIER, "a",
                Tokens.EQUAL, null,
                Tokens.IDENTIFIER, "a",
                Tokens.PLUS, null,
                Tokens.INT_LITERAL, "1",

                Tokens.PAREN_CLOSE, null,

                Tokens.EOF, null,
            ) as ArrayList<Any>
        )
    }

    @Test
    fun whileTest() {
        expectTokens(
            "while (true) 1 + 1",
            arrayListOf(
                Tokens.WHILE, null,
                Tokens.PAREN_OPEN, null,
                Tokens.TRUE, null,
                Tokens.PAREN_CLOSE, null,

                Tokens.INT_LITERAL, "1",
                Tokens.PLUS, null,
                Tokens.INT_LITERAL, "1",

                Tokens.EOF, null,
            ) as ArrayList<Any>
        )
    }

    @Test
    fun expectedOrErrorTest() {
        expectError(
            "|",
            SyntaxError("Expected another '|'", Position(1, 2, 0, filename))
        )
    }

    @Test
    fun expectedAndErrorTest() {
        expectError(
            "&",
            SyntaxError("Expected another '&'", Position(1, 2, 0, filename))
        )
    }

    @Test
    fun illegalCharacterError() {
        // TODO: 6/12/21 change this to some character we wont use in future implementation
        expectError(
                ":",
                IllegalCharacterError(':', Position(0, 1, 0, filename))
        )
    }
}