package com.iyxan23.eplk.errors

import com.iyxan23.eplk.lexer.models.Position

/**
 * This class represents an error in EPLK
 */
abstract class EplkError(
    open val name: String,
    open val detail: String,
    open val startPosition: Position,
    open val endPosition: Position,
)