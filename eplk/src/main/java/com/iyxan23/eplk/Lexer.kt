package com.iyxan23.eplk

import com.iyxan23.eplk.errors.IllegalCharacterError
import com.iyxan23.eplk.errors.SyntaxError
import com.iyxan23.eplk.errors.Error
import java.lang.StringBuilder

class Lexer(
    private val filename: String,
    private val code: String
) {

    private val spaces = arrayOf(' ', '\t', '\n')
    private val position = Position(-1, 0, 0, filename)
    private var currentChar: Char? = null

    private var errorThrown: Error? = null

    // What this function does is to jump one char
    private fun advance() {
        position.advance(currentChar)
        currentChar =   if (position.index >= code.length) null
                        else code[position.index]
    }

    fun doLexicalAnalysis(): LexerResult {
        val tokens: ArrayList<Token> = ArrayList()

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
                    tokens.add(Token(Tokens.PLUS, null))
                    advance()
                }

                currentChar == '-' -> {
                    tokens.add(Token(Tokens.MINUS, null))
                    advance()
                }

                currentChar == '*' -> {
                    tokens.add(Token(Tokens.MUL, null))
                    advance()
                }

                currentChar == '/' -> {
                    tokens.add(Token(Tokens.DIV, null))
                    advance()
                }

                currentChar == '^' -> {
                    tokens.add(Token(Tokens.POW, null))
                    advance()
                }

                currentChar == '(' -> {
                    tokens.add(Token(Tokens.PAREN_OPEN, null))
                    advance()
                }

                currentChar == ')' -> {
                    tokens.add(Token(Tokens.PAREN_CLOSE, null))
                    advance()
                }

                currentChar == '"' -> {
                    // Parse the string, if parseStringLiteral returns null, return an error
                    val string = parseStringLiteral() ?: return LexerResult(null, errorThrown)
                    tokens.add(Token(Tokens.STRING_LITERAL, string))
                    advance()
                }

                currentChar!!.isDigit() -> {
                    val result = parseNumberLiteral()

                    val number = result.first
                    val isFloat = result.second

                    if (isFloat) {
                        tokens.add(Token(Tokens.FLOAT_LITERAL, number))
                    } else {
                        tokens.add(Token(Tokens.INT_LITERAL, number))
                    }
                }

                else -> {
                    throwError(IllegalCharacterError(currentChar!!, position))
                    return LexerResult(null, errorThrown)
                }
            }
        }

        // Tokenization successful
        return LexerResult(tokens, null)
    }

    // "Utilities"
    private fun throwError(error: com.iyxan23.eplk.errors.Error) { errorThrown = error }

    //                                     int     is float?
    private fun parseNumberLiteral(): Pair<String, Boolean> {
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

        return Pair(builder.toString(), dotCount != 0)
    }

    private val escapeCharacters = mapOf(
        'n' to '\n',
        't' to '\t',
    )

    private fun parseStringLiteral(): String? {
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
                '"' -> return builder.toString() // Alright we're done
                '\\' -> escape = true
                else -> builder.append(currentChar) // append the char to build the string
            }

            advance()
        }

        // wat? the string doesn't end? throw an error
        throwError(SyntaxError("EOL while reading a string literal", stringStartPosition, position))

        return null
    }
}