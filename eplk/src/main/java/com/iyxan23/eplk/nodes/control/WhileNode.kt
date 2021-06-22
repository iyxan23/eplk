package com.iyxan23.eplk.nodes.control

import com.iyxan23.eplk.errors.EplkTypeError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkBoolean
import com.iyxan23.eplk.objects.EplkObject
import com.iyxan23.eplk.objects.EplkVoid

class WhileNode(
    val condition: Node,
    val expression: Node,
    override val startPosition: Position
) : Node() {

    override val endPosition: Position = expression.endPosition

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        while (true) {
            val checkLoopResult = checkLoop(scope)
            if (checkLoopResult.hasError) return result.failure(checkLoopResult.error!!)

            if (!checkLoopResult.value!!.value) {
                // Ok, the condition returns false, we should stop looping
                break
            }

            // Alright execute the expression
            val expressionResult = result.register(evalExpression(scope))
            if (result.hasError) return result
        }

        // While loop finished
        return result.success(EplkVoid(scope))
    }

    private fun checkLoop(scope: Scope): RealtimeResult<EplkBoolean> {
        val result = RealtimeResult<EplkObject>()
        val conditionResult = result.register(condition.visit(scope))

        if (result.hasError) return RealtimeResult<EplkBoolean>().failure(result.error!!)

        val evaluatedCondition = conditionResult as EplkObject
        if (evaluatedCondition !is EplkBoolean) {
            return RealtimeResult<EplkBoolean>().failure(
                EplkTypeError(
                "Condition for a while loop should evaluate into a Boolean object, got ${evaluatedCondition.objectName} instead.",
                condition.startPosition,
                condition.endPosition,
                scope
            ))
        }

        return RealtimeResult<EplkBoolean>().success(evaluatedCondition)
    }

    private fun evalExpression(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()
        val thirdExpressionResult = result.register(expression.visit(scope))
        if (result.hasError) return result

        return result.success(thirdExpressionResult!!)
    }
}