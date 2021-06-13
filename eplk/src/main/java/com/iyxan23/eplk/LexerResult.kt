package com.iyxan23.eplk

import com.iyxan23.eplk.errors.Error

data class LexerResult(
        val tokens: ArrayList<Token>?,
        val error: Error?,
)
