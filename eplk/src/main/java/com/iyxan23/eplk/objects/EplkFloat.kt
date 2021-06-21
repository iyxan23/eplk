package com.iyxan23.eplk.objects

import com.iyxan23.eplk.Tokens
import com.iyxan23.eplk.errors.EplkNotImplementedError
import com.iyxan23.eplk.errors.EplkTypeError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import kotlin.math.pow

/**
 * A float object in EPLK
 */
class EplkFloat(
    val value: Float,
    override val scope: Scope
) : EplkObject(scope) {

    override val objectName = "Float"

    override fun toString(): String = value.toString()

    override fun operatorPlus(
        other: EplkObject,
        startPosition: Position,
        endPosition: Position
    ): RealtimeResult<EplkObject> {

        return when (other) {
            is EplkInteger ->
                RealtimeResult<EplkObject>().success(EplkFloat(value + other.value, scope))

            is EplkFloat ->
                RealtimeResult<EplkObject>().success(EplkFloat(value + other.value, scope))

            else ->
                RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
                    "+ operator with ${other.objectName} is not supported",
                    startPosition,
                    endPosition,
                    scope
                ))
        }
    }

    override fun operatorMinus(
        other: EplkObject,
        startPosition: Position,
        endPosition: Position
    ): RealtimeResult<EplkObject> {

        return when (other) {
            is EplkInteger ->
                RealtimeResult<EplkObject>().success(EplkFloat(value - other.value, scope))

            is EplkFloat ->
                RealtimeResult<EplkObject>().success(EplkFloat(value - other.value, scope))

            else ->
                RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
                    "- operator with ${other.objectName} is not supported",
                    startPosition,
                    endPosition,
                    scope
                ))
        }
    }

    override fun operatorMultiply(
        other: EplkObject,
        startPosition: Position,
        endPosition: Position
    ): RealtimeResult<EplkObject> {

        return when (other) {
            is EplkInteger ->
                RealtimeResult<EplkObject>().success(EplkFloat(value * other.value, scope))

            is EplkFloat ->
                RealtimeResult<EplkObject>().success(EplkFloat(value * other.value, scope))

            else ->
                RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
                    "* operator with ${other.objectName} is not supported",
                    startPosition,
                    endPosition, scope
                ))
        }
    }

    override fun operatorDivide(
        other: EplkObject,
        startPosition: Position,
        endPosition: Position
    ): RealtimeResult<EplkObject> {

        return when (other) {
            is EplkInteger ->
                RealtimeResult<EplkObject>().success(EplkFloat(value / other.value, scope))

            is EplkFloat ->
                RealtimeResult<EplkObject>().success(EplkFloat(value / other.value, scope))

            else ->
                RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
                    "/ operator with ${other.objectName} is not supported",
                    startPosition,
                    endPosition,
                    scope
                ))
        }
    }

    override fun operatorPow(
        other: EplkObject,
        startPosition: Position,
        endPosition: Position
    ): RealtimeResult<EplkObject> {

        return when (other) {
            is EplkInteger ->
                RealtimeResult<EplkObject>().success(EplkFloat(value.pow(other.value), scope))

            is EplkFloat ->
                RealtimeResult<EplkObject>().success(EplkFloat(value.pow(other.value), scope))

            else ->
                RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
                    "^ operator with ${other.objectName} is not supported",
                    startPosition,
                    endPosition,
                    scope
                ))
        }
    }

    override fun comparisonTo(
        other: EplkObject,
        startPosition: Position,
        endPosition: Position
    ): RealtimeResult<Array<Tokens>> {
        when (other) {
            is EplkInteger -> {
                val tokens = ArrayList<Tokens>()

                val comparison = value.compareTo(other.value)

                if (comparison == 0) {
                    tokens.add(Tokens.DOUBLE_EQUALS)
                    tokens.add(Tokens.GREATER_OR_EQUAL_THAN)
                    tokens.add(Tokens.LESSER_OR_EQUAL_THAN)
                } else {
                    tokens.add(Tokens.NOT_EQUAL)
                }

                if (value > other.value) {
                    tokens.add(Tokens.GREATER_THAN)
                } else {
                    tokens.add(Tokens.LESSER_THAN)
                }

                return RealtimeResult<Array<Tokens>>().success(tokens.toTypedArray())
            }

            is EplkFloat -> {
                val tokens = ArrayList<Tokens>()

                if (value == other.value) {
                    tokens.add(Tokens.DOUBLE_EQUALS)
                    tokens.add(Tokens.GREATER_OR_EQUAL_THAN)
                    tokens.add(Tokens.LESSER_OR_EQUAL_THAN)
                } else {
                    tokens.add(Tokens.NOT_EQUAL)
                }

                if (value > other.value) {
                    tokens.add(Tokens.GREATER_THAN)
                } else {
                    tokens.add(Tokens.LESSER_THAN)
                }

                return RealtimeResult<Array<Tokens>>().success(tokens.toTypedArray())
            }

            else ->
                return RealtimeResult<Array<Tokens>>().failure(EplkTypeError(
                    "Comparison with Integer must be either an other Integer or Float. Expected Integer / Float, got ${other.objectName}",
                    startPosition,
                    endPosition,
                    scope
                ))
        }
    }
}