package com.iyxan23.eplk

import com.iyxan23.eplk.errors.EplkError

data class LexerResult(
        val tokens: ArrayList<Token>?,
        val error: EplkError?,
)
