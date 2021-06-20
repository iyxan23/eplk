package com.iyxan23.eplk.objects

import com.iyxan23.eplk.Tokens
import com.iyxan23.eplk.errors.EplkTypeError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position

class EplkBoolean(
    private val value: Boolean,
    override val scope: Scope,
) : EplkObject(scope) {

    override val objectName: String = "Boolean"

    override fun toString(): String = value.toString()

    override fun comparisonTo(
        other: EplkObject,
        startPosition: Position,
        endPosition: Position
    ): RealtimeResult<Array<Tokens>> {
        val result = RealtimeResult<Array<Tokens>>()
        val comparisonResult = ArrayList<Tokens>()

        // Make sure to be the same type
        if (other !is EplkBoolean) {
            return result.failure(EplkTypeError(
                "Comparison on boolean must be with the same type. Expected Boolean, got ${other.objectName}",
                startPosition,
                endPosition,
                scope
            ))
        }

        // Now do comparisons
        if (value == other.value) comparisonResult.add(Tokens.EQUAL)
        else comparisonResult.add(Tokens.NOT_EQUAL)

        return result.success(comparisonResult.toTypedArray())
    }
}