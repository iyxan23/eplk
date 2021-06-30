package com.iyxan23.eplk.nodes.types

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkList
import com.iyxan23.eplk.objects.EplkObject

class ListNode(
    override val startPosition: Position,
    override val endPosition: Position,

    val items: ArrayList<Node> = ArrayList(),
) : Node() {

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        // Evaluate each items
        val result = RealtimeResult<EplkObject>()
        val resultItems = ArrayList<EplkObject>()

        items.forEach { node ->
            val nodeResult = result.register(node.visit(scope))
            if (result.shouldReturn) return result

            resultItems.add(nodeResult!!)
        }

        return result.success(EplkList(scope, resultItems))
    }
}