package com.iyxan23.eplk.objects

import com.iyxan23.eplk.Tokens
import com.iyxan23.eplk.errors.runtime.EplkTypeError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position

class EplkString(
    val value: String,
    override val scope: Scope,
) : EplkObject(scope) {
    override val objectName: String = "String"
    override fun toString(): String = value

    override fun operatorPlus(
        other: EplkObject,
        startPosition: Position,
        endPosition: Position
    ): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        // Check the type
        if (other !is EplkString) {
            return result.failure(EplkTypeError(
                "String cannot be added with ${other.objectName}",
                startPosition,
                endPosition,
                scope
            ))
        }

        return result.success(EplkString(value + other.value, scope))
    }

    override fun operatorMultiply(
        other: EplkObject,
        startPosition: Position,
        endPosition: Position
    ): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        // Check the type
        if (other !is EplkInteger) {
            return result.failure(EplkTypeError(
                "String cannot be multiplied with ${other.objectName}",
                startPosition,
                endPosition,
                scope
            ))
        }

        return result.success(EplkString(value.repeat(other.value), scope))
    }

    override fun comparisonTo(
        other: EplkObject,
        startPosition: Position,
        endPosition: Position
    ): RealtimeResult<Array<Tokens>> {
        val result = RealtimeResult<Array<Tokens>>()
        val tokens = ArrayList<Tokens>()

        // Check the type
        if (other !is EplkString) {
            return result.failure(EplkTypeError(
                "String cannot be compared with ${other.objectName}",
                startPosition,
                endPosition,
                scope
            ))
        }

        if (other.value == value) {
            tokens.add(Tokens.DOUBLE_EQUALS)
        } else {
            tokens.add(Tokens.NOT_EQUAL)
        }

        return result.success(tokens.toTypedArray())
    }
}