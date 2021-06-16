package com.iyxan23.eplk.parser

import com.iyxan23.eplk.Token
import com.iyxan23.eplk.Tokens
import com.iyxan23.eplk.errors.SyntaxError
import com.iyxan23.eplk.parser.nodes.BinOpNode
import com.iyxan23.eplk.parser.nodes.Node
import com.iyxan23.eplk.parser.nodes.NumberNode
import com.iyxan23.eplk.parser.nodes.UnaryOpNode

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

    private val numberLiterals = arrayOf(Tokens.INT_LITERAL, Tokens.FLOAT_LITERAL)
    private val unaryTokens = arrayOf(Tokens.PLUS, Tokens.MINUS)

    // factor = [INT|FLOAT]|[[PLUS|MINUS] factor]
    private fun factor(): ParseResult {
        val result = ParseResult()
        val oldToken = currentToken

        // Check if the current token contains a unary operator (+ and -)
        when {
            unaryTokens.contains(oldToken.token) -> {
                result.register(advance())
                val factor = result.register(factor())

                if (result.hasError) return result

                return result.success(UnaryOpNode(oldToken, factor as Node))
            }

            // Check if the current token is a number (int or float)
            numberLiterals.contains(oldToken.token) -> {
                result.register(advance())
                return result.success(NumberNode(oldToken))
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

    // term = factor [[MUL|DIV) factor]*
    private fun term(): ParseResult {
        val result = ParseResult()
        var leftNode = result.register(factor()) as Node

        if (result.hasError) return result

        while (termOperators.contains(currentToken.token)) {
            val operatorToken = currentToken
            result.register(advance())
            val rightNode = result.register(factor()) as Node

            if (result.hasError) return result

            leftNode = BinOpNode(leftNode, operatorToken, rightNode)
        }

        return result.success(leftNode)
    }

    private val expressionOperators = arrayOf(Tokens.PLUS, Tokens.MINUS)

    // expression = term [[PLUS|MINUS] term]*
    private fun expression(): ParseResult {
        val result = ParseResult()
        var leftNode = result.register(term()) as Node

        if (result.hasError) return result

        while (expressionOperators.contains(currentToken.token)) {
            val operatorToken = currentToken
            result.register(advance())
            val rightNode = result.register(term()) as Node

            if (result.hasError) return result

            leftNode =  BinOpNode(leftNode, operatorToken, rightNode)
        }

        return result.success(leftNode)
    }
}
