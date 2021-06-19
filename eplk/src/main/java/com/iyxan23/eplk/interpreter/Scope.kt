package com.iyxan23.eplk.interpreter

import com.iyxan23.eplk.lexer.models.Position

/**
 * This class represents a scope in EPLK
 */
data class Scope(
    val name: String,
    val symbolTable: SymbolTable = SymbolTable(),
    val parent: Scope? = null,
    val parentPosition: Position? = null,
)

// TODO: 6/19/21 Implement searching for variables / objects / etc for the parent scope
