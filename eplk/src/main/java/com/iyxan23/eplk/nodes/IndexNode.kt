package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.objects.EplkObject

class IndexNode(
    val nodeToIndex: Node,
    val indexValue: Node,
    override val endPosition: Position,
) : Node() {
    override val startPosition: Position
        get() = nodeToIndex.startPosition

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        // Get the value
        val valueResult = result.register(indexValue.visit(scope))
        if (result.shouldReturn) return result

        val value = valueResult!!

        // And the object that is to be indexed
        val nodeToIndexResult = result.register(nodeToIndex.visit(scope))
        if (result.shouldReturn) return result

        val objectToIndex = nodeToIndexResult!!

        // Then index them
        val indexResult = result.register(objectToIndex.index(
            value,
            startPosition,
            endPosition
        ))

        if (result.shouldReturn) return result

        // And return it
        return result.success(indexResult!!)
    }
}