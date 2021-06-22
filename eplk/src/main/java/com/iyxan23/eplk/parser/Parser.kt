package com.iyxan23.eplk.parser

import com.iyxan23.eplk.Tokens
import com.iyxan23.eplk.errors.SyntaxError
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.nodes.*
import com.iyxan23.eplk.nodes.variable.VarAccessNode
import com.iyxan23.eplk.nodes.variable.VarDeclarationNode

/**
 * This class parses a list of tokens generated by the lexer into an AST (Abstract Syntax Tree)
 *
 * @see parse
 */
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

    // while-expression = WHILE PAREN_OPEN expression PAREN_CLOSE expression
    private fun whileExpression(): ParseResult {
        val result = ParseResult()

        val startPosition = currentToken.startPosition.copy()

        if (currentToken.token != Tokens.WHILE) {
            return result.failure(SyntaxError(
                "Expected 'while'",
                currentToken.startPosition,
                currentToken.endPosition
            ))
        }

        // ===========================================================
        result.registerAdvancement()
        advance()
        // ===========================================================

        if (currentToken.token != Tokens.PAREN_OPEN) {
            return result.failure(SyntaxError(
                "Expected an open parentheses '(' after 'while'",
                currentToken.startPosition,
                currentToken.endPosition,
            ))
        }

        // ===========================================================
        result.registerAdvancement()
        advance()
        // ===========================================================

        val conditionResult = result.register(expression())
        if (result.hasError) return result

        val condition = conditionResult as Node

        if (currentToken.token != Tokens.PAREN_CLOSE) {
            return result.failure(SyntaxError(
                "Expected a close parentheses ')' after the third for loop expression",
                currentToken.startPosition,
                currentToken.endPosition,
            ))
        }

        // ===========================================================
        result.registerAdvancement()
        advance()
        // ===========================================================

        val expressionResult = result.register(expression())
        if (result.hasError) return result

        val expression = expressionResult as Node

        return result.success(WhileNode(condition, expression, startPosition))
    }

    // for-expression = FOR PAREN_OPEN expression1 SEMICOLON expression2 SEMICOLON expression3 PAREN_CLOSE expression
    private fun forExpression(): ParseResult {
        val result = ParseResult()

        val startPosition = currentToken.startPosition.copy()
        if (currentToken.token != Tokens.FOR) {
            return result.failure(SyntaxError(
                "Expected 'for'",
                currentToken.startPosition,
                currentToken.endPosition
            ))
        }

        // ===========================================================
        result.registerAdvancement()
        advance()
        // ===========================================================

        if (currentToken.token != Tokens.PAREN_OPEN) {
            return result.failure(SyntaxError(
                "Expected an open parentheses '(' after 'for'",
                currentToken.startPosition,
                currentToken.endPosition,
            ))
        }

        // ===========================================================
        result.registerAdvancement()
        advance()
        // ===========================================================

        val firstExpressionResult = result.register(expression())
        if (result.hasError) return result

        val firstExpression = firstExpressionResult as Node

        if (currentToken.token != Tokens.SEMICOLON) {
            return result.failure(SyntaxError(
                "Expected a semicolon after the first for loop expression",
                currentToken.startPosition,
                currentToken.endPosition,
            ))
        }

        // ===========================================================
        result.registerAdvancement()
        advance()
        // ===========================================================

        val secondExpressionResult = result.register(expression())
        if (result.hasError) return result

        val secondExpression = secondExpressionResult as Node

        if (currentToken.token != Tokens.SEMICOLON) {
            return result.failure(SyntaxError(
                "Expected a semicolon after the second for loop expression",
                currentToken.startPosition,
                currentToken.endPosition,
            ))
        }

        // ===========================================================
        result.registerAdvancement()
        advance()
        // ===========================================================

        val thirdExpressionResult = result.register(expression())
        if (result.hasError) return result

        val thirdExpression = thirdExpressionResult as Node

        if (currentToken.token != Tokens.PAREN_CLOSE) {
            return result.failure(SyntaxError(
                "Expected a close parentheses ')' after the third for loop expression",
                currentToken.startPosition,
                currentToken.endPosition,
            ))
        }

        // ===========================================================
        result.registerAdvancement()
        advance()
        // ===========================================================

        val expressionResult = result.register(expression())
        if (result.hasError) return result

        val expression = expressionResult as Node

        return result.success(
            ForNode(startPosition, firstExpression, secondExpression, thirdExpression, expression)
        )
    }

    // if-expression = IF PAREN_OPEN expression PAREN_CLOSE expression
    private fun ifExpression(): ParseResult {
        val result = ParseResult()

        val startPosition = currentToken.startPosition

        val statements = ArrayList<Pair<Node, Node>>()

        //////////////////////////////////////////////////////////////
        // IF STATEMENT
        //////////////////////////////////////////////////////////////

        if (currentToken.token != Tokens.IF) {
            return result.failure(SyntaxError(
                "Expected an integer literal, float literal, identifier, 'if', 'true', 'false', '+', '-', or '('",
                currentToken.startPosition,
                currentToken.endPosition,
            ))
        }

        // ===========================================================
        result.registerAdvancement()
        advance()
        // ===========================================================

        if (currentToken.token != Tokens.PAREN_OPEN) {
            return result.failure(SyntaxError(
                "Expected an open parentheses '(' after 'if'",
                currentToken.startPosition,
                currentToken.endPosition,
            ))
        }

        // ===========================================================
        result.registerAdvancement()
        advance()
        // ===========================================================

        // Parse the condition
        val conditionExpressionResult = result.register(expression())
        if (result.hasError) return result

        val conditionExpression = conditionExpressionResult as Node

        if (currentToken.token != Tokens.PAREN_CLOSE) {
            return result.failure(SyntaxError(
                "Expected a close parentheses ')' after an expression",
                currentToken.startPosition,
                currentToken.endPosition,
            ))
        }

        // ===========================================================
        result.registerAdvancement()
        advance()
        // ===========================================================

        // Parse the expression
        val expressionResult = result.register(expression())
        if (result.hasError) return result

        val expression = expressionResult as Node

        // Save that to the statements
        statements.add(Pair(conditionExpression, expression))

        // Now check for elif(s)
        while (currentToken.token == Tokens.ELIF) {
            //////////////////////////////////////////////////////////////
            // ELIF STATEMENT
            //////////////////////////////////////////////////////////////

            // ===========================================================
            result.registerAdvancement()
            advance()
            // ===========================================================

            if (currentToken.token != Tokens.PAREN_OPEN) {
                return result.failure(SyntaxError(
                    "Expected an open parentheses '(' after 'elif'",
                    currentToken.startPosition,
                    currentToken.endPosition,
                ))
            }

            // ===========================================================
            result.registerAdvancement()
            advance()
            // ===========================================================

            // Parse the condition
            val conditionExpressionResultElif = result.register(expression())
            if (result.hasError) return result

            val conditionExpressionElif = conditionExpressionResultElif as Node

            if (currentToken.token != Tokens.PAREN_CLOSE) {
                return result.failure(SyntaxError(
                    "Expected a close parentheses ')' after an expression",
                    currentToken.startPosition,
                    currentToken.endPosition,
                ))
            }

            // ===========================================================
            result.registerAdvancement()
            advance()
            // ===========================================================

            // Parse the expression
            val expressionResultElif = result.register(expression())
            if (result.hasError) return result

            val expressionElif = expressionResultElif as Node

            // Save it to statements
            statements.add(Pair(conditionExpressionElif, expressionElif))
        }

        // Finally, check the else statement

        //////////////////////////////////////////////////////////////
        // ELSE STATEMENT
        //////////////////////////////////////////////////////////////

        if (currentToken.token != Tokens.ELSE) {
            return result.failure(SyntaxError(
                "Expected 'else'. An else block is required in an expression if",
                currentToken.startPosition,
                currentToken.endPosition,
            ))
        }

        // ===========================================================
        result.registerAdvancement()
        advance()
        // ===========================================================

        // Parse the expression
        val expressionResultElse = result.register(expression())
        if (result.hasError) return result

        val expressionElse = expressionResultElse as Node

        // ===========================================================
        result.registerAdvancement()
        advance()
        // ===========================================================

        // -----------------------------------------------------------
        // Done
        // -----------------------------------------------------------

        return result.success(IfNode(
            statements.toTypedArray(),
            expressionElse,
            startPosition,
            expression.endPosition
        ))
    }

    // atom = [INT|FLOAT] | IDENTIFIER | [PAREN_OPEN expression* PAREN_CLOSE] | [TRUE|FALSE] | if-expression | for-expression | while-expression
    private fun atom(): ParseResult {
        val result = ParseResult()
        val oldToken = currentToken.copy()

        when (oldToken.token) {

            // Check if the current token is an int
            Tokens.INT_LITERAL -> {
                result.registerAdvancement()
                advance()

                return result.success(IntegerNode(oldToken))
            }

            // Check if the current token is a float
            Tokens.FLOAT_LITERAL -> {
                result.registerAdvancement()
                advance()

                return result.success(FloatNode(oldToken))
            }

            // Check if the current token is a boolean literal (true / false)
            Tokens.TRUE, Tokens.FALSE -> {
                result.registerAdvancement()
                advance()

                return result.success(
                    BooleanNode(
                        oldToken.token == Tokens.TRUE,
                        oldToken.startPosition,
                        oldToken.endPosition
                    )
                )
            }

            // Check if the current token is an identifier (variable)
            Tokens.IDENTIFIER -> {
                result.registerAdvancement()
                advance()

                return result.success(VarAccessNode(
                    oldToken.value!!,
                    oldToken.startPosition,
                    oldToken.endPosition
                ))
            }

            // Check if the current token is an open parentheses
            Tokens.PAREN_OPEN -> {
                result.registerAdvancement()
                advance()

                val expression = result.register(expression())

                if (result.hasError) return result

                // Check if the parentheses is closed
                return if (currentToken.token == Tokens.PAREN_CLOSE) {
                    result.registerAdvancement()
                    advance()
                    result.success(expression as Node)
                } else {
                    result.failure(SyntaxError(
                        "Expected an integer or a float literal",
                        currentToken.startPosition,
                        currentToken.endPosition,
                    ))
                }
            }

            // Check if this is an if expression
            Tokens.IF -> {
                val ifResult = result.register(ifExpression())
                if (result.hasError) return result

                return result.success(ifResult as Node)
            }

            // Check if this is an for loop
            Tokens.FOR -> {
                val forResult = result.register(forExpression())
                if (result.hasError) return result

                return result.success(forResult as Node)
            }

            // Check if this is a while statement
            Tokens.WHILE -> {
                val whileResult = result.register(whileExpression())
                if (result.hasError) return result

                return result.success(whileResult as Node)
            }

            else -> {
                return result.failure(SyntaxError(
                    "Expected an integer literal, float literal, identifier, 'if', 'for', 'true', 'false', '+', '-', or '('",
                    oldToken.startPosition,
                    oldToken.endPosition,
                ))
            }
        }
    }

    // power = atom [POW factor]*
    private fun power(): ParseResult {
        val result = ParseResult()
        val leftNodeResult = result.register(atom()) as Node?

        if (result.hasError) return result

        var leftNode = leftNodeResult as Node

        while (currentToken.token == Tokens.POW) {
            val operatorToken = currentToken.copy()

            result.registerAdvancement()
            advance()

            val rightNode = result.register(factor()) as Node?

            if (result.hasError) return result

            leftNode = BinOpNode(leftNode, operatorToken, rightNode!!)
        }

        return result.success(leftNode)
    }

    private val unaryTokens = arrayOf(Tokens.PLUS, Tokens.MINUS)

    // factor = [[PLUS|MINUS] factor] | power
    private fun factor(): ParseResult {
        val result = ParseResult()
        val oldToken = currentToken.copy()

        // Check if the current token contains a unary operator (+ and -)
        if (unaryTokens.contains(oldToken.token)) {
            result.registerAdvancement()
            advance()

            val factor = result.register(factor())

            if (result.hasError) return result

            return result.success(UnaryOpNode(oldToken, factor as Node))
        } else {
            return power()
        }
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

            result.registerAdvancement()
            advance()

            val rightNode = result.register(factor()) as Node?

            if (result.hasError) return result

            leftNode = BinOpNode(leftNode, operatorToken, rightNode!!)
        }

        return result.success(leftNode)
    }

    private val arithmeticOperators = arrayOf(Tokens.PLUS, Tokens.MINUS)

    // arithmetic-expression = term [[PLUS|MINUS] term]*
    private fun arithmeticExpression(): ParseResult {
        val result = ParseResult()
        val leftNodeResult = result.register(term()) as Node?

        if (result.hasError) return result

        var leftNode = leftNodeResult as Node

        while (arithmeticOperators.contains(currentToken.token)) {
            // Get the operator and the next term
            val operatorToken = currentToken.copy()

            result.registerAdvancement()
            advance()

            val rightNode = result.register(term()) as Node?

            if (result.hasError) return result

            leftNode = BinOpNode(leftNode, operatorToken, rightNode!!)
        }

        return result.success(leftNode)
    }

    private val comparisonOperators = arrayOf(
        Tokens.DOUBLE_EQUALS,
        Tokens.NOT_EQUAL,
        Tokens.GREATER_THAN,
        Tokens.LESSER_THAN,
        Tokens.GREATER_OR_EQUAL_THAN,
        Tokens.LESSER_OR_EQUAL_THAN
    )

    // comparison-expression = NOT comparison-expression | arithmetic-expression [[comparison operators] arithmetic-expression]*
    private fun comparisonExpression(): ParseResult {
        val result = ParseResult()

        // Check if the current token is the NOT token
        if (currentToken.token == Tokens.NOT) {
            val notToken = currentToken.copy()

            result.registerAdvancement()
            advance()

            // then do the comparison expression
            val comparisonExpression = result.register(comparisonExpression()) as Node?

            if (result.hasError) return result

            return result.success(UnaryOpNode(notToken, comparisonExpression as Node))

        } else {
            // if no NOT token, let's do an arithmetic expression, very similar to term and factor
            val arithmeticExpressionResult = result.register(arithmeticExpression()) as Node?

            if (result.hasError) return result

            var leftNode = arithmeticExpressionResult as Node

            while (comparisonOperators.contains(currentToken.token)) {
                val operatorToken = currentToken.copy()

                result.registerAdvancement()
                advance()

                val rightNode = result.register(arithmeticExpression()) as Node?

                if (result.hasError) return result

                leftNode = BinOpNode(leftNode, operatorToken, rightNode!!)
            }

            return result.success(leftNode)
        }
    }

    // expression = KEYWORD:VAR IDENTIFIER EQUAL expression | comparison-expression [[AND|OR] comparison-expression]*
    private fun expression(): ParseResult {
        val result = ParseResult()

        // Check if this is a keyword
        if (currentToken.token == Tokens.KEYWORD) {
            // Check if this is a var keyword
            if (currentToken.value == "var") {
                val varToken = currentToken.copy()

                result.registerAdvancement()
                advance()

                val identifierToken = currentToken.copy()

                // Check if the next token is an identifier
                if (identifierToken.token != Tokens.IDENTIFIER) {
                    return result.failure(SyntaxError(
                        "Expected an identifier",
                        currentToken.startPosition,
                        currentToken.endPosition
                    ))
                }

                result.registerAdvancement()
                advance()

                // And the next one an equal
                if (currentToken.token != Tokens.EQUAL) {
                    return result.failure(SyntaxError(
                        "Expected '='",
                        currentToken.startPosition,
                        currentToken.endPosition
                    ))
                }

                result.registerAdvancement()
                advance()

                // Ok, now we're setup, let's parse the expression
                val expressionResult = result.register(expression()) as Node?
                if (result.hasError) return result

                val expression = expressionResult as Node

                // return a VarDeclarationNode
                return result.success(
                    VarDeclarationNode(
                        identifierToken.value!!,
                        expression,
                        varToken.startPosition
                    )
                )

            } else {
                throw NotImplementedError("Keyword ${currentToken.value} is not implemented")
            }

        } else {
            // do a comparison expression, very similar to term and factor
            val comparisonExpressionResult = result.register(comparisonExpression()) as Node?

            if (result.hasError) return result

            var leftNode = comparisonExpressionResult as Node

            while (currentToken.token == Tokens.AND || currentToken.token == Tokens.OR) {
                val operatorToken = currentToken.copy()

                result.registerAdvancement()
                advance()

                val rightNode = result.register(comparisonExpression()) as Node?

                if (result.hasError) return result

                leftNode = BinOpNode(leftNode, operatorToken, rightNode!!)
            }

            return result.success(leftNode)
        }
    }
}
