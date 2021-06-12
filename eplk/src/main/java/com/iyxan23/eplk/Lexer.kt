package com.iyxan23.eplk

import java.lang.StringBuilder

class Lexer(private val code: String) {

    private val spaces = arrayOf(' ', '\t', '\n')
    private var charIndex = 0
    private var currentChar: Char? = null
    private val reader = StringBuilder()

    private var errorThrown: Error? = null

    private fun advance() {
        charIndex++
        currentChar =   if (charIndex >= code.length) null
                        else code[charIndex]
    }

    fun doLexicalAnalysis(): LexerResult {
        val tokens: ArrayList<Token> = ArrayList()

        while (currentChar != null) {
            // Ignore spaces
            if (spaces.contains(currentChar)) continue

            // Now let's do the tokenization
            if (currentChar == '(') {
                tokens.add(Token(Tokens.PAREN_OPEN, null))
            } else if (currentChar == ')') {
                tokens.add(Token(Tokens.PAREN_CLOSE, null))
            } else if (currentChar == '"') {
                // Parse the string, if parseStringLiteral returns null, return an error
                val string = parseStringLiteral() ?: return LexerResult(null, errorThrown)
                tokens.add(Token(Tokens.STRING_LITERAL, string))
            }

            advance()
        }

        // Tokenization successful
        return LexerResult(tokens, null)
    }

    // "Utilities"
    private fun throwError(error: Error) { errorThrown = error }

    private fun parseStringLiteral(): String? {
        val stringPosition = charIndex
        advance() // We want to skip the first "

        // Used to indicate an escape like \"
        var escape = false
        val builder = StringBuilder()

        // Now start looping
        while (currentChar != '"' && currentChar != null) {
            if (escape) {
                builder.append(currentChar)
                escape = false
            }

            when (currentChar) {
                '"' -> return builder.toString() // We're done

                '\\' -> escape = true

                else -> builder.append(currentChar) // append the char to build the string
            }

            advance()
        }

        // wat? the string doesn't end? throw an error
        throwError(Error("NoStringEndError", "The string at $stringPosition doesn't seem to end"))

        return null
    }
}