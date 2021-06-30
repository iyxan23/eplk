package com.iyxan23.eplk.nodes.control

import com.iyxan23.eplk.errors.runtime.EplkTypeError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.nodes.StatementsNode
import com.iyxan23.eplk.objects.EplkBoolean
import com.iyxan23.eplk.objects.EplkObject
import com.iyxan23.eplk.objects.EplkVoid

open class BaseIfNode(
    val condition: Node,
    val statements: StatementsNode,

    override val startPosition: Position,
    override val endPosition: Position,
) : Node() {

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        val conditionResult = result.register(evaluateCondition(scope, condition))
        if (result.shouldReturn) return result

        val conditionValue = conditionResult as EplkBoolean

        // Check if the condition value is true
        if (conditionValue.value) {
            // Yep run the statement
            val statementsResult = result.register(statements.visit(scope))
            if (result.shouldReturn) return result

            return result.success(statementsResult!!, mapOf(
                "condition_result" to "if"
            ))

        } else {
            return result.success(EplkVoid(scope), mapOf(
                "condition_result" to "else"
            ))
        }
    }

    /**
     * This function evaluates the condition given and returns the EplkBoolean output of it
     */
    private fun evaluateCondition(scope: Scope, condition: Node): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        // First, let's evaluate the condition
        val conditionResult = result.register(condition.visit(scope))
        if (result.shouldReturn) return result

        val evaluatedCondition = conditionResult as EplkObject

        // Must check for the type
        if (evaluatedCondition !is EplkBoolean) {
            return result.failure(
                EplkTypeError(
                    "Condition for an if statement should've evaluated into a boolean, got ${evaluatedCondition.objectName} instead",
                    condition.startPosition,
                    condition.endPosition,
                    scope,
                ))
        }

        return result.success(evaluatedCondition)
    }
}