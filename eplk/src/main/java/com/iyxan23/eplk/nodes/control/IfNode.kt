package com.iyxan23.eplk.nodes.control

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.nodes.StatementsNode
import com.iyxan23.eplk.objects.EplkObject
import com.iyxan23.eplk.objects.EplkVoid

class IfNode(
    condition: Node,
    statements: StatementsNode,
    val elifNodes: Array<ElifNode>,
    val elseNode: StatementsNode?,
    val isSingleLine: Boolean = false,

    override val startPosition: Position,
    override val endPosition: Position

) : BaseIfNode(condition, statements, startPosition, endPosition) {

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        val conditionResult = super.visit(scope)

        result.register(conditionResult)
        if (result.shouldReturn) return result

        when {
            conditionResult.passedData["condition_result"] == "if" -> {
                // Alright then let's just return what BaseIfNode gave us
                return result.success(conditionResult.value!!)
            }

            conditionResult.passedData["condition_result"] == "else" -> {
                // Execute elif statements, if any
                elifNodes.forEach { node ->
                    val elifResult = node.visit(scope)

                    result.register(elifResult)
                    if (result.shouldReturn) return result

                    if (elifResult.passedData["condition_result"] == "if") {
                        // alright this elif statement succeed, let's return it's value if we're an expression
                        return result.success(if (isSingleLine) elifResult.value!! else EplkVoid(scope))
                    }
                }

                // looks like none of the elif statements are true, check if we have else
                if (elseNode != null) {
                    // then execute the else node
                    val elseResult = result.register(elseNode.visit(scope))
                    if (result.shouldReturn) return result

                    // check if we're in single line, if yes, then return the value right away
                    if (isSingleLine) return result.success(elseResult!!)
                }

                return result.success(EplkVoid(scope))
            }

            else -> throw IllegalStateException("condition_result is neither if nor else")
        }
    }
}