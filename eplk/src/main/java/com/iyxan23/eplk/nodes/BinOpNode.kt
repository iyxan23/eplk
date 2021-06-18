package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.objects.EplkObject

// A 2 number operation, example: 1 + 1
data class BinOpNode(
    val leftNode: Node,
    val operatorToken: Token,
    val rightNode: Node,
) : Node() {

    override val startPosition get() = leftNode.startPosition
    override val endPosition get() = rightNode.endPosition

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
    }
}