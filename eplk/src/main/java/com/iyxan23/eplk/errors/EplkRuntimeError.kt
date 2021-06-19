package com.iyxan23.eplk.errors

import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import java.lang.StringBuilder

open class EplkRuntimeError(
    override val name: String,
    override val detail: String,
    override val startPosition: Position,
    override val endPosition: Position,
    open val scope: Scope
): EplkError("Runtime Error", detail, startPosition, endPosition) {

    /**
     * This function generates a traceback from the scope provided in the constructor
     */
    fun generateTraceback(): String {
        var currentScope: Scope? = scope.copy()
        var currentPosition: Position? = startPosition.copy()

        val result = StringBuilder()

        result.append("Traceback: ")

        while (currentScope != null) {
            result.appendLine(" - Filename ${currentPosition!!.filename} inside ${currentScope.name} at line ${currentPosition.line}")

            currentPosition = currentScope.parentPosition
            currentScope = currentScope.parent
        }

        return result.toString()
    }

    override fun toString(): String {
        return "Error $name: $detail\n" + generateTraceback()
    }
}