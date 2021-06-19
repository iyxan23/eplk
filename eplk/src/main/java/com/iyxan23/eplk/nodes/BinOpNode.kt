package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.Tokens
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.objects.EplkObject

/**
 * A simple 2 number operation, example: 1 + 1
 */
data class BinOpNode(
    val leftNode: Node,
    val operatorToken: Token,
    val rightNode: Node,
) : Node() {

    override val startPosition get() = leftNode.startPosition
    override val endPosition get() = rightNode.endPosition

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        val leftResult = result.register(leftNode.visit(scope))
        if (result.hasError) return result

        val rightResult = result.register(rightNode.visit(scope))
        if (result.hasError) return result

        val leftObject = leftResult as EplkObject
        val rightObject = rightResult as EplkObject

        return when (operatorToken.token) {
            Tokens.PLUS     -> leftObject.operatorPlus      (rightObject, startPosition, endPosition)
            Tokens.MINUS    -> leftObject.operatorMinus     (rightObject, startPosition, endPosition)
            Tokens.MUL      -> leftObject.operatorMultiply  (rightObject, startPosition, endPosition)
            Tokens.DIV      -> leftObject.operatorDivide    (rightObject, startPosition, endPosition)
            Tokens.POW      -> leftObject.operatorPow       (rightObject, startPosition, endPosition)

            else -> {
                throw RuntimeException("Operator token is neither plus, minus, multiply, nor divide")
            }
        }
    }
}