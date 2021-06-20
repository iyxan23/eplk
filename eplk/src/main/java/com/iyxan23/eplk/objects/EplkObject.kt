package com.iyxan23.eplk.objects

import com.iyxan23.eplk.errors.EplkNotImplementedError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position

/**
 * An abstract class used to make EPLK objects
 */
abstract class EplkObject(open val scope: Scope) {
    abstract val objectName: String

    abstract override fun toString(): String

    open fun operatorPlus(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
            "+ operator is not implemented by $objectName",
            startPosition,
            endPosition,
            scope
        ))
    }

    open fun operatorMinus(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
            "- operator is not implemented by $objectName",
            startPosition,
            endPosition,
            scope
        ))
    }

    open fun operatorMultiply(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
            "* operator is not implemented by $objectName",
            startPosition,
            endPosition,
            scope
        ))
    }

    open fun operatorDivide(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
            "/ operator is not implemented by $objectName",
            startPosition,
            endPosition,
            scope
        ))
    }

    open fun operatorPow(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
            "^ operator is not implemented by $objectName",
            startPosition,
            endPosition,
            scope
        ))
    }
}
