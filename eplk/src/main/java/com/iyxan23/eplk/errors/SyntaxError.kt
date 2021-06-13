package com.iyxan23.eplk.errors

import com.iyxan23.eplk.Position

data class SyntaxError(
    override val detail: String,
    override val startPosition: Position,
    override val endPosition: Position,
) : Error("SyntaxError", detail, startPosition, endPosition)

