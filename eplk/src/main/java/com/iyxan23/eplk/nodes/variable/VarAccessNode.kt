package com.iyxan23.eplk.nodes.variable

import com.iyxan23.eplk.errors.runtime.EplkDefinitionError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkObject

class VarAccessNode(
    identifierToken: Token,
) : Node() {

    val variableName = identifierToken.value!!
    override val startPosition: Position = identifierToken.startPosition
    override val endPosition: Position = identifierToken.endPosition

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        val variable = scope.searchVariable(variableName)
            ?: return result.failure(
                EplkDefinitionError(
                    "Variable $variableName is not defined in this scope",
                    startPosition,
                    endPosition,
                    scope
                )
            )

        return result.success(variable)
    }
}