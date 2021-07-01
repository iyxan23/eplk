package com.iyxan23.eplk.nodes.control

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkObject

class ReturnNode(
    val returnValue: Node?,

    override val startPosition: Position,
    override val endPosition: Position
) : Node() {

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()
        if (returnValue == null) return result.returnValue(null)

        val returnValueResult = result.register(returnValue.visit(scope))
        if (result.shouldReturn) return result

        return result.returnValue(returnValueResult)
    }
}