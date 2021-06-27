package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.objects.EplkObject
import com.iyxan23.eplk.objects.EplkVoid

/**
 * This node stores a list of statements that will later be executed by the interpreter / an other node
 */
class StatementsNode(
    val statements: Array<Node>,
) : Node() {

    override val startPosition: Position
        get() = statements[0].startPosition

    override val endPosition: Position
        get() = statements[statements.size - 1].endPosition

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        statements.forEach { node ->
            result.register(node.visit(scope))
            if (result.hasError) return result
        }

        return result.success(EplkVoid(scope))
    }
}