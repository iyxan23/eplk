package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.errors.runtime.EplkNotDefinedError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.objects.EplkObject

class FunctionCallNode(
    val functionName: String,
    val arguments: Array<EplkObject>,
    override val startPosition: Position,
    override val endPosition: Position
) : Node() {

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        if (!scope.symbolTable.variables.containsKey(functionName)) {
            result.failure(EplkNotDefinedError(
                "Function $functionName is not defined in this scope",
                startPosition,
                endPosition,
                scope
            ))
        }

        val functionResult = result.register(
            scope.symbolTable.variables[functionName]!!.call(arguments, startPosition, endPosition)
        )

        if (result.hasError) return result

        return result.success(functionResult as EplkObject)
    }
}