package com.iyxan23.eplk.nodes.variable

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkObject

class VarDeclarationNode(
    val variableName: String,
    val variableValue: Node,
    override val startPosition: Position
) : Node() {

    override val endPosition: Position
        get() = variableValue.endPosition

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()
        val visitValue = result.register(variableValue.visit(scope))

        if (result.hasError) return result

        scope.symbolTable.variables[variableName] = visitValue as EplkObject

        return result.success(visitValue)
    }
}