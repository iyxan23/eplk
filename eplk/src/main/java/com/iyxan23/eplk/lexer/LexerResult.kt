package com.iyxan23.eplk.lexer

import com.iyxan23.eplk.errors.EplkError
import com.iyxan23.eplk.lexer.models.Token

data class LexerResult(
    val tokens: ArrayList<Token>?,
    val error: EplkError?,
)
