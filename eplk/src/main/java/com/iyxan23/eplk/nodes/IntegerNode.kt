package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Token

// Simply a number, example: 1
data class IntegerNode(
    val number: Token
) : Node() {
    override fun visit(node: Node, scope: Scope): RealtimeResult {
        if (node !is BinOpNode) throw IllegalArgumentException("Visited with a different node")
        TODO("Not yet implemented")
    }
}