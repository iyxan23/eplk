package com.iyxan23.eplk.objects

import com.iyxan23.eplk.errors.EplkNotImplementedError
import com.iyxan23.eplk.interpreter.RealtimeResult

class EplkFloat(val value: Float) : EplkObject() {
    override val objectName = "Float"

    override fun operatorPlus(other: EplkObject): RealtimeResult<EplkObject> {
        return when (other) {
            is EplkInteger ->
                RealtimeResult<EplkObject>().success(EplkFloat(value + other.value))

            is EplkFloat ->
                RealtimeResult<EplkObject>().success(EplkFloat(value + other.value))

            else ->
                RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
                    "+ operator with ${other.objectName} is not supported",
                    startPosition!!,
                    endPosition!!
                ))
        }
    }

    override fun operatorMinus(other: EplkObject): RealtimeResult<EplkObject> {
        return when (other) {
            is EplkInteger ->
                RealtimeResult<EplkObject>().success(EplkFloat(value - other.value))

            is EplkFloat ->
                RealtimeResult<EplkObject>().success(EplkFloat(value - other.value))

            else ->
                RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
                    "+ operator with ${other.objectName} is not supported",
                    startPosition!!,
                    endPosition!!
                ))
        }
    }

    override fun operatorMultiply(other: EplkObject): RealtimeResult<EplkObject> {
        return when (other) {
            is EplkInteger ->
                RealtimeResult<EplkObject>().success(EplkFloat(value * other.value))

            is EplkFloat ->
                RealtimeResult<EplkObject>().success(EplkFloat(value * other.value))

            else ->
                RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
                    "+ operator with ${other.objectName} is not supported",
                    startPosition!!,
                    endPosition!!
                ))
        }
    }

    override fun operatorDivide(other: EplkObject): RealtimeResult<EplkObject> {
        return when (other) {
            is EplkInteger ->
                RealtimeResult<EplkObject>().success(EplkFloat(value / other.value))

            is EplkFloat ->
                RealtimeResult<EplkObject>().success(EplkFloat(value / other.value))

            else ->
                RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
                    "+ operator with ${other.objectName} is not supported",
                    startPosition!!,
                    endPosition!!
                ))
        }
    }
}