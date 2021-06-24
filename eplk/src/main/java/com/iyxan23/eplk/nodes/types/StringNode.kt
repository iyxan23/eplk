package com.iyxan23.eplk.nodes.types

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkObject
import com.iyxan23.eplk.objects.EplkString

class StringNode(
    private val stringToken: Token
) : Node() {
    override val startPosition: Position = stringToken.startPosition
    override val endPosition: Position = stringToken.endPosition

    val value = stringToken.value

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().success(EplkString(stringToken.value!!, scope))
    }
}