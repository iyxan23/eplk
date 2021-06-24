package com.iyxan23.eplk.errors

import com.iyxan23.eplk.lexer.models.Position
import java.lang.StringBuilder

/**
 * This class represents an error in EPLK
 */
open class EplkError(
    open val name: String,
    open val detail: String,
    open val startPosition: Position,
    open val endPosition: Position,
) {
    open fun generateString(withPosition: Boolean = true): String {
        val lineCode = startPosition.code.split("\n")[startPosition.line]
        val error = StringBuilder()

        error.appendLine()
        error.appendLine(lineCode)
        error.append(" ".repeat(startPosition.column - 1))
        error.append("^".repeat(endPosition.column - startPosition.column))
        error.appendLine()
        error.append("$name: $detail\n")

        if (withPosition) error.append("at filename ${startPosition.filename} line ${startPosition.line}\n")

        return error.toString()
    }
}