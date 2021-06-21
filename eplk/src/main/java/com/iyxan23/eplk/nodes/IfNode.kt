package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.errors.EplkTypeError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.objects.EplkBoolean
import com.iyxan23.eplk.objects.EplkInteger
import com.iyxan23.eplk.objects.EplkObject

// TODO: 6/21/21 Implement elif and else
class IfNode(
    val condition: Node,
    val expression: Node,
    override val startPosition: Position,
    override val endPosition: Position,
) : Node() {

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
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

        // Now return the expression if the evaluatedCondition is true
        if (evaluatedCondition.value) {
            // then let's evaluate the expression
            val expressionResult = result.register(expression.visit(scope))
            if (result.hasError) return result

            return result.success(expressionResult!!)

        } else {
            // TODO: 6/21/21 Make void return type
            return result.success(EplkInteger(-1, scope))
        }
    }
}