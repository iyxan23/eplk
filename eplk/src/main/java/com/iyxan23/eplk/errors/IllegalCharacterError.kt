package com.iyxan23.eplk.errors

import com.iyxan23.eplk.lexer.models.Position

data class IllegalCharacterError(
    val character: Char,
    val position: Position,
) : EplkError("IllegalCharacterError", "Invalid character $character", position, position.copy().advance(character))
