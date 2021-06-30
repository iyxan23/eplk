package com.iyxan23.eplk.nodes.operation

import com.iyxan23.eplk.Tokens
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkBoolean
import com.iyxan23.eplk.objects.EplkFloat
import com.iyxan23.eplk.objects.EplkInteger
import com.iyxan23.eplk.objects.EplkObject

/**
 * A single number operation, example: -1
 */
data class UnaryOpNode(
    val tokenOperator: Token,
    val node: Node,
) : Node() {

    override val startPosition get() = tokenOperator.startPosition
    override val endPosition get() = node.endPosition

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        val visitResult = node.visit(scope)
        if (visitResult.shouldReturn) return result

        // Check if the node result is a integer or a float
        when (visitResult.value) {
            // this is an integer
            is EplkInteger -> {
                val integerNumber = visitResult.value as EplkInteger

                // check the operator if it's a plus or minus
                return when (tokenOperator.token) {
                    // This is a minus, multiply it by -1
                    Tokens.MINUS -> {
                        result.success(EplkInteger(integerNumber.value * -1, scope))
                    }

                    // this is a plus
                    // if the value is negative, multiply it by -1, otherwise don't
                    Tokens.PLUS -> {
                        result.success(
                            EplkInteger(
                                integerNumber.value * ( if (integerNumber.value < 0) -1 else 1 ),
                                scope
                            )
                        )
                    }

                    // this shouldn't happen
                    else -> throw RuntimeException("tokenOperator is neither minus or plus")
                }
            }

            // this is a float
            is EplkFloat -> {
                val floatNumber = visitResult.value as EplkFloat

                // check the operator
                return when (tokenOperator.token) {
                    // this is a minus, multiply it by -1
                    Tokens.MINUS -> {
                        result.success(EplkFloat(floatNumber.value * -1, scope))
                    }

                    // this is a plus
                    // if the float is negative, multiply it by -1, otherwise don't
                    Tokens.PLUS -> {
                        result.success(
                            EplkFloat(
                                floatNumber.value * ( if (floatNumber.value < 0) 1 else -1 ),
                                scope
                            )
                        )
                    }

                    // this shouldn't happen
                    else -> throw RuntimeException("tokenOperator is neither minus or plus")
                }
            }

            // This is a boolean
            is EplkBoolean -> {
                val booleanValue = visitResult.value as EplkBoolean

                when (tokenOperator.token) {
                    Tokens.NOT -> {
                        return result.success(booleanValue.notOperator(startPosition, endPosition).value!!)
                    }

                    else -> throw RuntimeException("tokenOperator is not a '!' (not) operator")
                }
            }

            // not float or integer? weird, this shouldn't happen
            else -> {
                throw RuntimeException("${visitResult.value!!.javaClass.canonicalName} object doesn't exist")
            }
        }
    }
}