package com.iyxan23.eplk.parser.nodes

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Token

// A single number operation, example: -1
data class UnaryOpNode(
    val tokenOperator: Token,
    val node: Node,
) : Node() {
    override fun visit(node: Node, scope: Scope): RealtimeResult {
        if (node !is BinOpNode) throw IllegalArgumentException("Visited with a different node")
        TODO("Not yet implemented")
    }
}