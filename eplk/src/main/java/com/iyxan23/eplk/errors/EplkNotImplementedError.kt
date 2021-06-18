package com.iyxan23.eplk.errors

import com.iyxan23.eplk.lexer.models.Position

class EplkNotImplementedError(
    details: String,
    override val startPosition: Position,
    override val endPosition: Position
) : EplkRuntimeError("NotImplementedError", details, startPosition, endPosition)