package com.iyxan23.eplk.objects

import com.iyxan23.eplk.errors.EplkNotImplementedError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position

abstract class EplkObject(open val scope: Scope) {
    abstract val objectName: String

    open fun operatorPlus(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
            "+ operator is not implemented by $objectName",
            startPosition,
            endPosition
        ))
    }

    open fun operatorMinus(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
            "- operator is not implemented by $objectName",
            startPosition,
            endPosition
        ))
    }

    open fun operatorMultiply(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
            "* operator is not implemented by $objectName",
            startPosition,
            endPosition
        ))
    }

    open fun operatorDivide(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
            "/ operator is not implemented by $objectName",
            startPosition,
            endPosition
        ))
    }
}
