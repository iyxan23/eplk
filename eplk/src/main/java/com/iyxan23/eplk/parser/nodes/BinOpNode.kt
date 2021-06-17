package com.iyxan23.eplk.parser.nodes

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Token

// A 2 number operation, example: 1 + 1
data class BinOpNode(
    val leftNode: Node,
    val operatorToken: Token,
    val rightNode: Node,
) : Node() {
    override fun visit(node: Node, scope: Scope): RealtimeResult {
        if (node !is BinOpNode) throw IllegalArgumentException("Visited with a different node")
        TODO("Not yet implemented")
    }
}