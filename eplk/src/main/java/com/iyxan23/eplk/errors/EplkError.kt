package com.iyxan23.eplk.errors

import com.iyxan23.eplk.lexer.models.Position

/**
 * This class represents an error in EPLK
 */
open class EplkError(
    open val name: String,
    open val detail: String,
    open val startPosition: Position,
    open val endPosition: Position,
) {
    override fun toString(): String {
        return "$name: $detail\n at filename ${startPosition.filename} line ${startPosition.line}\n"
    }
}