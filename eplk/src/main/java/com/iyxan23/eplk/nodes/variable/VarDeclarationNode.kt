package com.iyxan23.eplk.nodes.variable

import com.iyxan23.eplk.errors.runtime.EplkDefinitionError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkObject
import com.iyxan23.eplk.objects.EplkVoid

class VarDeclarationNode(
    val variableName: String,
    val variableValue: Node?,

    override val startPosition: Position,
    override val endPosition: Position,
) : Node() {

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        // we only need to check the variable in the local scope, we can't declare variables on the parent scope while we're in a child scope
        if (scope.symbolTable.variables.containsKey(variableName)) {
            return result.failure(
                EplkDefinitionError(
                    "Variable $variableName is already declared in this scope",
                    startPosition, endPosition, scope
                )
            )
        }

        if (variableValue != null) {
            val visitValue = result.register(variableValue.visit(scope))
            if (result.hasError) return result

            scope.symbolTable.variables[variableName] = visitValue as EplkObject

            return result.success(visitValue)
        } else {
            scope.symbolTable.variables[variableName] = EplkVoid(scope)

            return result.success(EplkVoid(scope))
        }
    }
}