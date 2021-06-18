package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.Tokens
import com.iyxan23.eplk.errors.SyntaxError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.objects.EplkFloat
import com.iyxan23.eplk.objects.EplkInteger
import java.lang.RuntimeException

// A single number operation, example: -1
data class UnaryOpNode(
    val tokenOperator: Token,
    val node: Node,
) : Node() {

    override val startPosition get() = tokenOperator.startPosition
    override val endPosition get() = node.endPosition

    override fun visit(scope: Scope): RealtimeResult {
        val result = RealtimeResult()

        // Check if the node is a integer or a float
        when (node) {
            // this is an integer node
            is IntegerNode -> {
                // get the integer
                val integerNumber = result.register(node.visit(scope)) as EplkInteger
                if (result.hasError) return result

                // check the operator if it's a plus or minus
                return when (tokenOperator.token) {
                    // This is a minus, multiply it by -1
                    Tokens.MINUS -> {
                        result.success(EplkInteger(integerNumber.value * -1))
                    }

                    // this is a plus
                    // if the value is negative, multiply it by -1, otherwise don't
                    Tokens.PLUS -> {
                        result.success(
                            EplkInteger(
                                integerNumber.value * ( if (integerNumber.value < 0) -1 else 1 )
                            )
                        )
                    }

                    // this shouldn't happen
                    else -> {
                        throw RuntimeException("tokenOperator is neither minus or plus")
                    }
                }
            }

            // this is a float node
            is FloatNode -> {
                // get the float
                val floatNumber = result.register(node.visit(scope)) as EplkFloat
                if (result.hasError) return result

                // check the operator
                return when (tokenOperator.token) {
                    // this is a minus, multiply it by -1
                    Tokens.MINUS -> {
                        result.success(EplkFloat(floatNumber.value * -1))
                    }

                    // this is a plus
                    // if the float is negative, multiply it by -1, otherwise don't
                    Tokens.PLUS -> {
                        result.success(
                            EplkFloat(
                                floatNumber.value * ( if (floatNumber.value < 0) 1 else -1 )
                            )
                        )
                    }

                    // this shouldn't happen
                    else -> {
                        throw RuntimeException("tokenOperator is neither minus or plus")
                    }
                }
            }

            // not an integer or float? this is an error!
            else -> {
                return result.failure(SyntaxError(
                    "Expected an Integer or Float",
                    startPosition,
                    endPosition
                ))
            }
        }
    }
}