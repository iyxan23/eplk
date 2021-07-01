package com.iyxan23.eplk.nodes.control

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkObject

class BreakNode(
    override val startPosition: Position,
    override val endPosition: Position
) : Node() {

    override fun visit(scope: Scope): RealtimeResult<EplkObject>
        = RealtimeResult<EplkObject>().successBreak()
}