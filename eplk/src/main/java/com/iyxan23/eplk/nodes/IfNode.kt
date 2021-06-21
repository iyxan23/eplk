package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.errors.EplkTypeError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.objects.EplkBoolean
import com.iyxan23.eplk.objects.EplkObject

class IfNode(
    /**
     * Array<Pair<condition: Node, expression: Node>>
     */
    val statements: Array<Pair<Node, Node>>,
    val elseExpression: Node,

    override val startPosition: Position,
    override val endPosition: Position,
) : Node() {

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        statements.forEach {
            val condition = it.first
            val expression = it.second

            val conditionResult = result.register(evaluateCondition(scope, condition))
            if (result.hasError) return result

            val evaluatedCondition = conditionResult as EplkBoolean

            // Now return the expression if the evaluated condition is true
            if (evaluatedCondition.value) {
                // then let's evaluate the expression
                val expressionResult = result.register(expression.visit(scope))
                if (result.hasError) return result

                return result.success(expressionResult!!)
            }
        }

        // Looks like no statements are true, let's evaluate the else block instead
        val elseResult = result.register(elseExpression.visit(scope))
        if (result.hasError) return result

        return result.success(elseResult!!)
    }

    /**
     * This function evaluates the condition given and returns the EplkBoolean output of it
     */
    private fun evaluateCondition(scope: Scope, condition: Node): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        // First, let's evaluate the condition
        val conditionResult = result.register(condition.visit(scope))
        if (result.hasError) return result

        val evaluatedCondition = conditionResult as EplkObject

        // Must check for the type
        if (evaluatedCondition !is EplkBoolean) {
            return result.failure(EplkTypeError(
                "Condition for an if statement should evaluate into a boolean. Expected Boolean, got ${evaluatedCondition.objectName} instead",
                condition.startPosition,
                condition.endPosition,
                scope,
            ))
        }

        return result.success(evaluatedCondition)
    }
}