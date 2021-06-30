package com.iyxan23.eplk.nodes.operation

import com.iyxan23.eplk.Tokens
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkBoolean
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
        if (result.shouldReturn) return result

        val rightResult = result.register(rightNode.visit(scope))
        if (result.shouldReturn) return result

        val leftObject = leftResult as EplkObject
        val rightObject = rightResult as EplkObject

        return when (operatorToken.token) {
            Tokens.PLUS     -> leftObject.operatorPlus      (rightObject, startPosition, endPosition)
            Tokens.MINUS    -> leftObject.operatorMinus     (rightObject, startPosition, endPosition)
            Tokens.MUL      -> leftObject.operatorMultiply  (rightObject, startPosition, endPosition)
            Tokens.DIV      -> leftObject.operatorDivide    (rightObject, startPosition, endPosition)
            Tokens.POW      -> leftObject.operatorPow       (rightObject, startPosition, endPosition)

            Tokens.OR       -> leftObject.orOperator        (rightObject, startPosition, endPosition)
            Tokens.AND      -> leftObject.andOperator       (rightObject, startPosition, endPosition)

            Tokens.DOUBLE_EQUALS, Tokens.NOT_EQUAL,
            Tokens.GREATER_THAN, Tokens.LESSER_THAN,
            Tokens.GREATER_OR_EQUAL_THAN, Tokens.LESSER_OR_EQUAL_THAN -> {
                val comparisonResult = leftObject.comparisonTo(rightObject, startPosition, endPosition)
                if (comparisonResult.hasError) return RealtimeResult<EplkObject>().failure(comparisonResult.error!!)

                return RealtimeResult<EplkObject>().success(
                    EplkBoolean(comparisonResult.value!!.contains(operatorToken.token), scope)
                )
            }

            else -> {
                throw RuntimeException("Operator token is neither plus, minus, multiply, divide, nor comparison operators")
            }
        }
    }
}