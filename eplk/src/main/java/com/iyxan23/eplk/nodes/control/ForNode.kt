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

class ForNode(
    override val startPosition: Position,
    val firstExpression: Node,
    val secondExpression: Node,
    val thirdExpression: Node,
    val statements: StatementsNode,
) : Node() {

    override val endPosition: Position = statements.endPosition

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        // Evaluate the first expression first
        val result = RealtimeResult<EplkObject>()
        result.register(evalFirstExpression(scope))
        if (result.shouldReturn) return result

        // Now
        while (true) {
            // Check if we can loop
            val checkLoopResult = checkLoop(scope)
            if (checkLoopResult.hasError) return result.failure(checkLoopResult.error!!)

            if (!checkLoopResult.value!!.value) {
                // Ok, the second expression returns false, we should stop looping
                break
            }

            // Evaluate the third expression
            result.register(evalThirdExpression(scope))
            if (result.shouldReturn) return result

            // Then evaluate the expression!
            result.register(statements.visit(scope))
            if (result.hasError) return result

            if (result.isContinuing) continue
            if (result.isBreaking) break
        }

        // For loop finished
        return result.success(EplkVoid(scope))
    }

    private fun evalFirstExpression(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()
        val firstExpressionResult = result.register(firstExpression.visit(scope))
        if (result.shouldReturn) return result

        return result.success(firstExpressionResult!!)
    }

    private fun checkLoop(scope: Scope): RealtimeResult<EplkBoolean> {
        val result = RealtimeResult<EplkObject>()
        val secondExpressionResult = result.register(secondExpression.visit(scope))

        if (result.hasError) return RealtimeResult<EplkBoolean>().failure(result.error!!)

        val evaluatedSecondExpression = secondExpressionResult as EplkObject
        if (evaluatedSecondExpression !is EplkBoolean) {
            return RealtimeResult<EplkBoolean>().failure(
                EplkTypeError(
                "Second expression for a for loop should evaluate into a Boolean object, got ${evaluatedSecondExpression.objectName} instead.",
                secondExpression.startPosition,
                secondExpression.endPosition,
                scope
            )
            )
        }

        return RealtimeResult<EplkBoolean>().success(evaluatedSecondExpression)
    }

    private fun evalThirdExpression(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()
        val thirdExpressionResult = result.register(thirdExpression.visit(scope))
        if (result.shouldReturn) return result

        return result.success(thirdExpressionResult!!)
    }
}