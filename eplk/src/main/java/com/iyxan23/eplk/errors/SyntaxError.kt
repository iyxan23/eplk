package com.iyxan23.eplk.errors

import com.iyxan23.eplk.lexer.models.Position

data class SyntaxError(
    override val detail: String,
    override val startPosition: Position,
    override val endPosition: Position,
) : EplkError("SyntaxError", detail, startPosition, endPosition) {

    override fun toString(): String = super.toString()
}

