package com.iyxan23.eplk.errors

import com.iyxan23.eplk.Position

data class IllegalCharacterError(
    val character: Char,
    val position: Position,
) : EplkError("IllegalCharacterError", "Invalid character $character", position, position)
