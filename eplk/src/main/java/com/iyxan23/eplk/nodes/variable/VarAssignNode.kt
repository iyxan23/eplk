package com.iyxan23.eplk.nodes.variable

import com.iyxan23.eplk.errors.runtime.EplkDefinitionError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkObject

class VarAssignNode(
    val variableName: String,
    val value: Node,

    override val startPosition: Position,
) : Node() {

    override val endPosition: Position
        get() = value.endPosition

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        // Check if this variable has already declared
        if (scope.searchVariable(variableName) == null) {
            // no
            return result.failure(EplkDefinitionError(
                "Variable $variableName has not been declared in this scope",
                startPosition, endPosition, scope
            ))
        }

        // then visit the variable value, and set the value to it
        val visitValue = result.register(value.visit(scope))
        if (result.shouldReturn) return result

        scope.assignVariable(variableName, visitValue!!)

        return result.success(visitValue)
    }
}