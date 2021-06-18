package com.iyxan23.eplk.interpreter

import com.iyxan23.eplk.lexer.models.Position

data class Scope(
    val name: String,
    val parent: Scope? = null,
    val parentPosition: Position? = null,
)
