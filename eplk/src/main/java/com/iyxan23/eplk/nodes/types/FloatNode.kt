package com.iyxan23.eplk.nodes.types

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Token
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkFloat
import com.iyxan23.eplk.objects.EplkObject

/**
 * Simply a float value, example: 0.1
 */
data class FloatNode(
    val float: Token
) : Node() {

    override val startPosition get() = float.startPosition
    override val endPosition get() = float.endPosition

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().success(EplkFloat(float.value!!.toFloat(), scope))
    }
}