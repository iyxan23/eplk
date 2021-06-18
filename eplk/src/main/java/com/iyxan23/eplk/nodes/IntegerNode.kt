package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.objects.EplkInteger

// Simply a number, example: 1
data class IntegerNode(
    val number: Token
) : Node() {

    override val startPosition get() = number.startPosition
    override val endPosition get() = number.endPosition

    override fun visit(scope: Scope): RealtimeResult {
        return RealtimeResult().success(EplkInteger(number.value!!.toInt()))
    }
}