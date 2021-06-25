package com.iyxan23.eplk.objects

import com.iyxan23.eplk.errors.runtime.EplkIndexOutOfBoundsError
import com.iyxan23.eplk.errors.runtime.EplkTypeError
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

    override fun index(
        eplkObject: EplkObject,
        startPosition: Position,
        endPosition: Position
    ): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        // Check the type
        if (eplkObject !is EplkInteger) {
            return result.failure(EplkTypeError(
                "Index value can only bean Integer, got ${eplkObject.objectName} instead",
                startPosition,
                endPosition,
                scope
            ))
        }

        // Also check the bounds
        if (eplkObject.value >= list.size || eplkObject.value < 0) {
            return result.failure(EplkIndexOutOfBoundsError(
                "Index is ${if (eplkObject.value >= list.size) "too big" else "too small"}, index provided is ${eplkObject.value} but the list size is ${list.size}",
                startPosition,
                endPosition,
                scope
            ))
        }

        // Ok, return the value at the specified index
        return result.success(list[eplkObject.value])
    }
}