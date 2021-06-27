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
import java.lang.IllegalArgumentException

class IfNode(
    /**
     * Array<Pair<condition: Node, expression(s): StatementsNode>>
     */
    val statements: Array<Pair<Node, StatementsNode>>,
    val elseExpression: Node?,
    val isMultiline: Boolean,

    // elseExpression mustn't be null if isMultiline is false as expression ifs are required to have an else branch

    override val startPosition: Position,
    override val endPosition: Position,
) : Node() {

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        if (!isMultiline && elseExpression == null) throw IllegalArgumentException("Inline if statement should have an else case")

        statements.forEach {
            val condition = it.first
            val statements = it.second

            val conditionResult = result.register(evaluateCondition(scope, condition))
            if (result.hasError) return result

            val evaluatedCondition = conditionResult as EplkBoolean

            // Now check if the evaluated condition is true
            if (evaluatedCondition.value) {
                // Check if this is a multiline statement
                if (isMultiline) {
                    // Then execute the statement(s)
                    result.register(statements.visit(scope))
                    if (result.hasError) return result

                    return result.success(EplkVoid(scope))

                } else {
                    // then let's evaluate the expression and return it
                    val expressionResult = result.register(statements.visit(scope))
                    if (result.hasError) return result

                    return result.success(expressionResult!!)
                }
            }
        }

        // Looks like none of the conditions were true, let's go to the else branch

        // Check if this is a multiline if statement
        if (isMultiline) {
            // Is there any else branch?
            if (elseExpression != null) {
                // Alright then execute it
                result.register(elseExpression.visit(scope))
                if (result.hasError) return result

                return result.success(EplkVoid(scope))
            }

            // nope there isn't, let's just get out
            return result.success(EplkVoid(scope))

        } else {
            // Looks like no statements are true, let's evaluate the else block instead
            val elseResult = result.register(elseExpression!!.visit(scope))
            if (result.hasError) return result

            return result.success(elseResult!!)
        }
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