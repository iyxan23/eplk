package com.iyxan23.eplk.interpreter

import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.objects.EplkObject

/**
 * This class represents a scope in EPLK
 */
data class Scope(
    val name: String,
    val symbolTable: SymbolTable = SymbolTable(),
    val parent: Scope? = null,
    val parentPosition: Position? = null,
) {
    // TODO: 6/26/21 Learn more about static scoping
    fun searchVariable(name: String): EplkObject? {
        // First search it on the local scope
        if (symbolTable.variables.containsKey(name)) {
            // Got the variable, return it
            return symbolTable.variables[name]

        } else {
            // Ok, check the parent scope if it's null or no
            if (parent == null) {
                // Then this variable doesn't exist
                return null
            }

            // Recursively call this function on the parent
            return parent.searchVariable(name)
        }
    }
}
