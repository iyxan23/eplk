package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.Tokens
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.objects.EplkObject

class IncrementOrDecrementNode(
    val incDecToken: Token,
    val nodeToIncDec: Node

) : Node() {
    override val startPosition: Position
        get() = nodeToIncDec.startPosition

    override val endPosition: Position
        get() = incDecToken.endPosition

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        val nodeToIncDecResult = result.register(nodeToIncDec.visit(scope))
        if (result.shouldReturn) return result

        when (incDecToken.token) {
            Tokens.DOUBLE_PLUS -> {
                result.register(nodeToIncDecResult!!.increment(startPosition, endPosition))
                if (result.shouldReturn) return result

                return result.success(nodeToIncDecResult)
            }

            Tokens.DOUBLE_MINUS -> {
                result.register(nodeToIncDecResult!!.decrement(startPosition, endPosition))
                if (result.shouldReturn) return result

                return result.success(nodeToIncDecResult)
            }

            else -> throw NotImplementedError("Token ${incDecToken.token.name} is not implemented in IncrementOrDecrementNode")
        }
    }
}