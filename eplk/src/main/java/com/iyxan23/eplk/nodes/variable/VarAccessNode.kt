package com.iyxan23.eplk.nodes.variable

import com.iyxan23.eplk.errors.runtime.EplkNotDefinedError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkObject

class VarAccessNode(
    val variableName: String,
    override val startPosition: Position,
    override val endPosition: Position,
) : Node() {

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        // check if the variable exists in the current scope
        if (!scope.symbolTable.variables.containsKey(variableName)) {
            return result.failure(
                EplkNotDefinedError(
                    "Variable $variableName is not defined in this scope",
                    startPosition,
                    endPosition,
                    scope
                )
            )
        }

        return result.success(scope.symbolTable.variables[variableName]!!)
    }
}