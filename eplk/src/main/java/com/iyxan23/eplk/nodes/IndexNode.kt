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
        TODO("Not yet implemented")
    }
}