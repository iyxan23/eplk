package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.objects.EplkObject

class FunctionDefinitionNode(
    val functionName: String,
    val parameters: Array<String>,
    val expression: Node,
    override val startPosition: Position
) : Node() {
    override val endPosition: Position = expression.endPosition

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        TODO("Not yet implemented")
    }
}