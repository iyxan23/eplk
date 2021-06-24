package com.iyxan23.eplk.nodes.types

import com.iyxan23.eplk.Tokens
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkBoolean
import com.iyxan23.eplk.objects.EplkObject

class BooleanNode(
    booleanToken: Token
) : Node() {

    override val startPosition: Position = booleanToken.startPosition
    override val endPosition: Position = booleanToken.endPosition

    val value = booleanToken.token == Tokens.TRUE

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().success(
            EplkBoolean(value, scope)
        )
    }
}