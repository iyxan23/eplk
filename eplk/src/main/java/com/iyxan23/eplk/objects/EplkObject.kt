package com.iyxan23.eplk.objects

import com.iyxan23.eplk.Tokens
import com.iyxan23.eplk.errors.runtime.EplkNotImplementedError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position

/**
 * An abstract class used to make EPLK objects
 */
abstract class EplkObject(open val scope: Scope) {
    abstract val objectName: String

    abstract override fun toString(): String

    // Note: These operator and comparison functions aren't going to be callable inside eplk.
    //       you can say these are "native" functions that is used by the interpreter to do
    //       operation between objects.
    open fun operatorPlus(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(
            EplkNotImplementedError(
            "+ operator is not implemented by $objectName",
            startPosition,
            endPosition,
            scope
        ))
    }

    open fun operatorMinus(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(
            EplkNotImplementedError(
            "- operator is not implemented by $objectName",
            startPosition,
            endPosition,
            scope
        ))
    }

    open fun operatorMultiply(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(
            EplkNotImplementedError(
            "* operator is not implemented by $objectName",
            startPosition,
            endPosition,
            scope
        ))
    }

    open fun operatorDivide(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(
            EplkNotImplementedError(
            "/ operator is not implemented by $objectName",
            startPosition,
            endPosition,
            scope
        ))
    }

    open fun operatorPow(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(
            EplkNotImplementedError(
            "^ operator is not implemented by $objectName",
            startPosition,
            endPosition,
            scope
        ))
    }

    open fun notOperator(startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(
            EplkNotImplementedError(
            "comparison is not implemented by $objectName",
            startPosition,
            endPosition,
            scope
        ))
    }

    open fun andOperator(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(
            EplkNotImplementedError(
            "and operator is not implemented by $objectName",
            startPosition,
            endPosition,
            scope
        ))
    }

    open fun orOperator(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(
            EplkNotImplementedError(
            "or operator is not implemented by $objectName",
            startPosition,
            endPosition,
            scope
        ))
    }

    open fun increment(startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(
            EplkNotImplementedError(
                "$objectName can't be incremented",
                startPosition,
                endPosition,
                scope
            )
        )
    }

    open fun decrement(startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(
            EplkNotImplementedError(
                "$objectName can't be decremented",
                startPosition,
                endPosition,
                scope
            )
        )
    }

    // When this object is being indexed, like for example:
    // variable[1]
    open fun index(eplkObject: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(
            EplkNotImplementedError(
                "$objectName can't be indexed",
                startPosition,
                endPosition,
                scope
            )
        )
    }

    // When this object is being called, like for example:
    // variable()
    open fun call(arguments: Array<EplkObject>, startPosition: Position, endPosition: Position): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(
            EplkNotImplementedError(
                "$objectName is not callable",
                startPosition,
                endPosition,
                scope
            )
        )
    }

    // TODO: 6/21/21 make a better way of doing this
    // Returns a list of one of these tokens:
    // Tokens.EQUAL, Tokens.NOT_EQUAL, Tokens.GREATER_THAN, Tokens.LESSER_THAN,
    // Tokens.GREATER_AND_EQUAL_THAN, Tokens.LESSER_AND_EQUAL_THAN
    open fun comparisonTo(other: EplkObject, startPosition: Position, endPosition: Position): RealtimeResult<Array<Tokens>> {
        return RealtimeResult<Array<Tokens>>().failure(
            EplkNotImplementedError(
            "comparison is not implemented by $objectName",
            startPosition,
            endPosition,
            scope
        ))
    }
}
