package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Token

// A single number operation, example: -1
data class UnaryOpNode(
    val tokenOperator: Token,
    val node: Node,
) : Node() {
    override fun visit(scope: Scope): RealtimeResult {
        TODO("Not yet implemented")
    }
}