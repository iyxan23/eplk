package com.iyxan23.eplk.parser

import com.iyxan23.eplk.Tokens
import com.iyxan23.eplk.errors.SyntaxError
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.nodes.*

class Parser(private val tokens: ArrayList<Token>) {

    private var currentToken: Token = tokens[0]
    private var indexToken = 0

    fun parse(): ParseResult {
        val result = expression()

        // Check if we're not at the end of the file
        if (!result.hasError && currentToken.token != Tokens.EOF) {
            // Looks like theres some code after this, but those code doesn't get parsed for some reason
            // there must be a syntax error
            result.failure(SyntaxError(
                "Expected '+', '-', '*', or '/'",
                currentToken.startPosition,
                currentToken.endPosition,
            ))
        }

        return result
    }

    private fun advance(): Token {
        indexToken++

        if (indexToken < tokens.size) {
            currentToken = tokens[indexToken]
        }

        return currentToken
    }

    private val unaryTokens = arrayOf(Tokens.PLUS, Tokens.MINUS)

    // factor = [INT|FLOAT]|[[PLUS|MINUS] factor]
    private fun factor(): ParseResult {
        val result = ParseResult()
        val oldToken = currentToken.copy()

        when {
            // Check if the current token contains a unary operator (+ and -)
            unaryTokens.contains(oldToken.token) -> {
                result.register(advance())
                val factor = result.register(factor())

                if (result.hasError) return result

                return result.success(UnaryOpNode(oldToken, factor as Node))
            }

            // Check if the current token is an int
            oldToken.token == Tokens.INT_LITERAL -> {
                result.register(advance())
                return result.success(IntegerNode(oldToken))
            }

            // Check if the current token is a float
            oldToken.token == Tokens.FLOAT_LITERAL -> {
                result.register(advance())
                return result.success(FloatNode(oldToken))
            }

            // Check if the current token is an open parentheses
            oldToken.token == Tokens.PAREN_OPEN -> {
                result.register(advance())
                val expression = result.register(expression())

                if (result.hasError) return result

                // Check if the parentheses is closed
                return if (currentToken.token == Tokens.PAREN_CLOSE) {
                    result.register(advance())
                    result.success(expression as Node)
                } else {
                    result.failure(SyntaxError(
                        "Expected an integer or a float literal",
                        currentToken.startPosition,
                        currentToken.endPosition,
                    ))
                }
            }
        }

        return result.failure(SyntaxError(
            "Expected an integer or a float literal",
            oldToken.startPosition,
            oldToken.endPosition,
        ))
    }

    private val termOperators = arrayOf(Tokens.MUL, Tokens.DIV)

    // term = factor [[MUL|DIV] factor]*
    private fun term(): ParseResult {
        val result = ParseResult()
        val leftNodeResult = result.register(factor()) as Node?

        if (result.hasError) return result

        var leftNode = leftNodeResult as Node

        while (termOperators.contains(currentToken.token)) {
            val operatorToken = currentToken.copy()
            result.register(advance())
            val rightNode = result.register(factor()) as Node?

            if (result.hasError) return result

            leftNode = BinOpNode(leftNode, operatorToken, rightNode!!)
        }

        return result.success(leftNode)
    }

    private val expressionOperators = arrayOf(Tokens.PLUS, Tokens.MINUS)

    // expression = term [[PLUS|MINUS] term]*
    private fun expression(): ParseResult {
        val result = ParseResult()
        val leftNodeResult = result.register(term()) as Node?

        if (result.hasError) return result

        var leftNode = leftNodeResult as Node

        while (expressionOperators.contains(currentToken.token)) {
            val operatorToken = currentToken.copy()
            result.register(advance())
            val rightNode = result.register(term()) as Node?

            if (result.hasError) return result

            leftNode = BinOpNode(leftNode, operatorToken, rightNode!!)
        }

        return result.success(leftNode)
    }
}
