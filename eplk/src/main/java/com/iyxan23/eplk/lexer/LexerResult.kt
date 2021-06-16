package com.iyxan23.eplk.lexer

import com.iyxan23.eplk.Token
import com.iyxan23.eplk.errors.EplkError

data class LexerResult(
    val tokens: ArrayList<Token>?,
    val error: EplkError?,
)
