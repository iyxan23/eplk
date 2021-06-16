package com.iyxan23.eplk.lexer.models

import com.iyxan23.eplk.Tokens

data class Token(
    val token: Tokens,
    val value: String?,
    val startPosition: Position,
    val endPosition: Position,
) {
    // for tokens that only have 1 position
    constructor(token: Tokens, value: String?, position: Position) :
            this(token, value, position, position.copy().advance(' '))
}
