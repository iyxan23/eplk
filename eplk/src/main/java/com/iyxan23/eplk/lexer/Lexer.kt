package com.iyxan23.eplk.lexer

import com.iyxan23.eplk.Tokens
import com.iyxan23.eplk.errors.EplkError
import com.iyxan23.eplk.errors.IllegalCharacterError
import com.iyxan23.eplk.errors.SyntaxError
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.lexer.models.Token

/**
 * This class turns a string code into a list of tokens
 *
 * @see doLexicalAnalysis
 */
class Lexer(
    filename: String,
    private val code: String
) {

    private val spaces = arrayOf(' ', '\t', '\n')
    private val position = Position(-1, 0, 0, filename, code)
    private var currentChar: Char? = null

    private var errorThrown: EplkError? = null

    private val tokens: ArrayList<Token> = ArrayList()

    // What this function does is to jump one char
    private fun advance() {
        position.advance(currentChar)
        currentChar =   if (position.index >= code.length) null
                        else code[position.index]
    }

    fun doLexicalAnalysis(): LexerResult {
        advance()

        while (currentChar != null) {
            // Ignore spaces
            if (spaces.contains(currentChar)) {
                advance()
                continue
            }

            // Now let's do the tokenization
            when {
                currentChar == '+' -> {
                    var tokenToAdd = Tokens.PLUS
                    val beforePosition = position.copy()
                    advance()

                    // Check for ++ (Double plus)
                    if (currentChar == '+') {
                        tokenToAdd = Tokens.DOUBLE_PLUS
                        advance()
                    }

                    tokens.add(Token(tokenToAdd, null, beforePosition))
                }

                currentChar == '-' -> {
                    var tokenToAdd = Tokens.MINUS
                    val beforePosition = position.copy()
                    advance()

                    // Check for ->
                    if (currentChar == '>') {
                        tokenToAdd = Tokens.ARROW
                        advance()

                    // Check for -- (double minus)
                    } else if (currentChar == '-') {
                        tokenToAdd = Tokens.DOUBLE_MINUS
                        advance()
                    }

                    tokens.add(Token(tokenToAdd, null, beforePosition, position.copy()))
                }

                currentChar == '*' -> {
                    tokens.add(Token(Tokens.MUL, null, position.copy()))
                    advance()
                }

                currentChar == '/' -> {
                    tokens.add(Token(Tokens.DIV, null, position.copy()))
                    advance()
                }

                currentChar == '^' -> {
                    tokens.add(Token(Tokens.POW, null, position.copy()))
                    advance()
                }

                currentChar == '!' -> {
                    var tokenToAdd = Tokens.NOT
                    val beforePosition = position.copy()
                    advance()

                    // Check for !=
                    if (currentChar == '=') {
                        tokenToAdd = Tokens.NOT_EQUAL
                        advance()
                    }

                    tokens.add(Token(tokenToAdd, null, beforePosition))
                }

                currentChar == '&' -> {
                    val beforePosition = position.copy()
                    advance()

                    // Check for &&
                    if (currentChar == '&') {
                        tokens.add(Token(Tokens.AND, null, beforePosition))
                        advance()

                    } else {
                        throwError(SyntaxError("Expected another '&'", position.copy()))
                        return LexerResult(null, errorThrown)
                    }
                }

                currentChar == '|' -> {
                    val beforePosition = position.copy()
                    advance()

                    // Check for ||
                    if (currentChar == '|') {
                        tokens.add(Token(Tokens.OR, null, beforePosition))
                        advance()

                    } else {
                        throwError(SyntaxError("Expected another '|'", position.copy()))
                        return LexerResult(null, errorThrown)
                    }
                }

                currentChar == '=' -> {
                    var tokenToAdd = Tokens.EQUAL
                    val beforePosition = position.copy()
                    advance()

                    // Check for double equals
                    if (currentChar == '=') {
                        tokenToAdd = Tokens.DOUBLE_EQUALS
                        advance()
                    }

                    tokens.add(Token(tokenToAdd, null, beforePosition))
                }

                currentChar == '>' -> {
                    var tokenToAdd = Tokens.GREATER_THAN
                    val beforePosition = position.copy()
                    advance()

                    // Check for >=
                    if (currentChar == '=') {
                        tokenToAdd = Tokens.GREATER_OR_EQUAL_THAN
                        advance()
                    }

                    tokens.add(Token(tokenToAdd, null, beforePosition))
                }

                currentChar == '<' -> {
                    var tokenToAdd = Tokens.LESSER_THAN
                    val beforePosition = position.copy()
                    advance()

                    // Check for <=
                    if (currentChar == '=') {
                        tokenToAdd = Tokens.LESSER_OR_EQUAL_THAN
                        advance()
                    }

                    tokens.add(Token(tokenToAdd, null, beforePosition))
                }

                currentChar == ',' -> {
                    tokens.add(Token(Tokens.COMMA, null, position.copy()))
                    advance()
                }

                currentChar == ';' -> {
                    tokens.add(Token(Tokens.SEMICOLON, null, position.copy()))
                    advance()
                }

                currentChar == '(' -> {
                    tokens.add(Token(Tokens.PAREN_OPEN, null, position.copy()))
                    advance()
                }

                currentChar == ')' -> {
                    tokens.add(Token(Tokens.PAREN_CLOSE, null, position.copy()))
                    advance()
                }

                currentChar == '[' -> {
                    tokens.add(Token(Tokens.BRACKET_OPEN, null, position.copy()))
                    advance()
                }

                currentChar == ']' -> {
                    tokens.add(Token(Tokens.BRACKET_CLOSE, null, position.copy()))
                    advance()
                }

                currentChar == '{' -> {
                    tokens.add(Token(Tokens.BRACES_OPEN, null, position.copy()))
                    advance()
                }

                currentChar == '}' -> {
                    tokens.add(Token(Tokens.BRACES_CLOSE, null, position.copy()))
                    advance()
                }

                currentChar == '"' -> {
                    // Parse the string, if parseStringLiteral returns null, return an error
                    parseStringLiteral()

                    if (errorThrown != null) return LexerResult(null, errorThrown)
                }

                currentChar!!.isDigit() -> {
                    parseNumberLiteral()

                    if (errorThrown != null) return LexerResult(null, errorThrown)
                }

                currentChar in 'a'..'z' ||
                currentChar in 'A'..'Z' -> {
                    parseIdentifier()

                    if (errorThrown != null) return LexerResult(null, errorThrown)
                }

                else -> {
                    throwError(IllegalCharacterError(currentChar!!, position))
                    return LexerResult(null, errorThrown)
                }
            }
        }

        // Don't forget the EOF (End Of File) token
        tokens.add(Token(Tokens.EOF, null, position.copy()))

        // Tokenization successful
        return LexerResult(tokens, null)
    }

    // "Utilities"
    private fun throwError(error: EplkError) { errorThrown = error }

    private fun parseNumberLiteral() {
        val numberStartPosition = position.copy()
        val builder = StringBuilder()
        var dotCount = 0

        while (true) {
            // Return the number if we're at the end of the file
            if (currentChar == null) break

            // Check if this is a dot (floating point)
            else if (currentChar == '.') {
                builder.append(currentChar)
                dotCount++
            }

            // or if it's not a digit or it's a space
            else if (!currentChar!!.isDigit() || spaces.contains(currentChar)) break

            // else, just add the character
            else builder.append(currentChar)

            advance()
        }

        // Alright we're done parsing

        if (dotCount > 1) {
            throwError(SyntaxError(
                "A number cannot contain more than 1 dot",
                numberStartPosition,
                position
            ))
        }

        if (dotCount == 1) {
            tokens.add(Token(Tokens.FLOAT_LITERAL, builder.toString(), numberStartPosition, position.copy()))
        } else {
            tokens.add(Token(Tokens.INT_LITERAL, builder.toString(), numberStartPosition, position.copy()))
        }
    }

    private val escapeCharacters = mapOf(
        'n' to '\n',
        't' to '\t',
    )

    private fun parseStringLiteral() {
        val stringStartPosition = position.copy()
        advance() // We want to skip the first "

        // Used to indicate an escape like \"
        var escape = false
        val builder = StringBuilder()

        // Now start looping
        while (currentChar != null) {
            if (escape) {
                builder.append(
                    if (escapeCharacters.containsKey(currentChar)) escapeCharacters[currentChar]
                    else currentChar
                )

                escape = false

                advance()
                continue
            }

            when (currentChar) {
                '"' -> {
                    advance()

                    // Add the string literal token
                    tokens.add(Token(Tokens.STRING_LITERAL, builder.toString(), stringStartPosition, position.copy()))
                    return

                } // Alright we're done
                '\\' -> escape = true
                else -> builder.append(currentChar) // append the char to build the string
            }

            advance()
        }

        // wat? the string doesn't end? throw an error
        throwError(SyntaxError("EOL while reading a string literal", stringStartPosition, position.copy()))
    }

    companion object {
        /**
         * A list of keywords
         */
        val keywords = arrayOf(
            "var"
        )
    }

    private fun parseIdentifier() {
        val identifierStartPosition = position.copy()
        val identifierBuilder = StringBuilder()

        loop@
        while (currentChar != null) {
            when {
                currentChar in 'a'..'z' ||
                currentChar in 'A'..'Z' ||
                currentChar in '0'..'9' ||
                currentChar == '_' ->
                    identifierBuilder.append(currentChar)

                spaces.contains(currentChar) -> break@loop

                else -> break@loop
            }

            advance()
        }

        val identifier = identifierBuilder.toString()
        var addValue = false
        val tokenToAdd =
            when (identifier) {
                "true" -> Tokens.TRUE
                "false" -> Tokens.FALSE

                "if" -> Tokens.IF
                "elif" -> Tokens.ELIF
                "else" -> Tokens.ELSE

                "for" -> Tokens.FOR

                "while" -> Tokens.WHILE

                "fun" -> Tokens.FUN

                else -> {
                    addValue = true
                    if (keywords.contains(identifier)) Tokens.KEYWORD else Tokens.IDENTIFIER
                }
            }

        tokens.add(
            Token(
                tokenToAdd,
                if (addValue) identifier else null,
                identifierStartPosition,
                position.copy()
            )
        )
    }
}