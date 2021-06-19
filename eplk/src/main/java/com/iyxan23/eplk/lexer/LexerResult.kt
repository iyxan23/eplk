package com.iyxan23.eplk.lexer

import com.iyxan23.eplk.errors.EplkError
import com.iyxan23.eplk.lexer.models.Token

/**
 * This class stores an arraylist of tokens and an error
 *
 * If error is not null, then there must be an error
 * If error is null, token should NOT be null
 */
data class LexerResult(
    val tokens: ArrayList<Token>?,
    val error: EplkError?,
)
