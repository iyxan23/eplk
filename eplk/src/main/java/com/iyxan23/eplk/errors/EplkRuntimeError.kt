package com.iyxan23.eplk.errors

import com.iyxan23.eplk.lexer.models.Position

open class EplkRuntimeError(
    override val name: String,
    override val detail: String,
    override val startPosition: Position,
    override val endPosition: Position,
): EplkError(name, detail, startPosition, endPosition)