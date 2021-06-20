package com.iyxan23.eplk.errors

import com.iyxan23.eplk.lexer.models.Position

data class SyntaxError(
    override val detail: String,
    override val startPosition: Position,
    override val endPosition: Position = startPosition.copy().advance(' '), // For 1 character errors
) : EplkError("SyntaxError", detail, startPosition, endPosition)

