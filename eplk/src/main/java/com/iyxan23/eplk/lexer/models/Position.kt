package com.iyxan23.eplk.lexer.models

/**
 * This data class stores the position where the lexer / interpreter / parser is doing stuff
 */
data class Position(
    var index: Int,
    var column: Int,
    var line: Int,
    val filename: String,
    val code: String,
) {
    fun advance(currentChar: Char?): Position {
        index++
        column++

        if (currentChar == '\n') {
            line++
            column = 1
        }

        return this
    }
}
