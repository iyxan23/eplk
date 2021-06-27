package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.objects.EplkObject

class FunctionCallNode(
    val nodeToCall: Node,
    val arguments: Array<Node>,
    override val startPosition: Position,
    override val endPosition: Position
) : Node() {

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        val nodeToCallResult = result.register(nodeToCall.visit(scope))
        if (result.hasError) return result

        // evaluate the arguments
        val evaluatedArguments = ArrayList<EplkObject>()

        arguments.forEach {
            val argumentResult = result.register(it.visit(scope))
            if (result.hasError) return result

            evaluatedArguments.add(argumentResult as EplkObject)
        }

        // Then call the function
        val functionResult = result.register(
            nodeToCallResult!!.call(evaluatedArguments.toTypedArray(), startPosition, endPosition)
        )

        if (result.hasError) return result

        return result.success(functionResult as EplkObject)
    }
}