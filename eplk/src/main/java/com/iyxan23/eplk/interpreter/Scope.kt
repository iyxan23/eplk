package com.iyxan23.eplk.interpreter

import com.iyxan23.eplk.lexer.models.Position

/**
 * This class represents a scope in EPLK
 */
data class Scope(
    val name: String,
    val parent: Scope? = null,
    val parentPosition: Position? = null,
)
