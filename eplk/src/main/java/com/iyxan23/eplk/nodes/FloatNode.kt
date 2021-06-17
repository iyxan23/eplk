package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Token

// Simply a float value, example: 0.1
data class FloatNode(
    val float: Token
) : Node() {
    override fun visit(scope: Scope): RealtimeResult {
        TODO("Not yet implemented")
    }
}