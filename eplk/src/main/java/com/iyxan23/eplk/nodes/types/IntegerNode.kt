package com.iyxan23.eplk.nodes.types

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkInteger
import com.iyxan23.eplk.objects.EplkObject

/**
 * Simply a number, example: 1
 */
data class IntegerNode(
    val number: Token
) : Node() {

    override val startPosition get() = number.startPosition
    override val endPosition get() = number.endPosition

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().success(EplkInteger(number.value!!.toInt(), scope))
    }
}