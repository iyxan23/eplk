package com.iyxan23.eplk

/**
 * This data class stores the position where the lexer / interpreter / parser is doing stuff
 */
data class Position(
    var index: Int,
    var column: Int,
    var line: Int,
    val filename: String,
) {
    fun advance(currentChar: Char?): Position {
        index++
        column++

        if (currentChar == '\n') {
            line++
            column = 0
        }

        return this
    }
}