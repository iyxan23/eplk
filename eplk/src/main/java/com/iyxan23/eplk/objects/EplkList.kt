package com.iyxan23.eplk.objects

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position

class EplkList(
    scope: Scope,
    val list: ArrayList<EplkObject>,

) : EplkObject(scope) {

    override val objectName: String = "List"
    override fun toString(): String = "[${list.joinToString(", ")}]"

    override fun operatorPlus(
        other: EplkObject,
        startPosition: Position,
        endPosition: Position
    ): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        // Check if the other object is a list
        if (other is EplkList) {
            // Do addAll
            return result.success(EplkList(scope, (list + other.list) as ArrayList<EplkObject>))
        }

        // If not, then add the object to the list
        list.add(other)

        return result.success(this)
    }
}