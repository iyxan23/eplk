package com.iyxan23.eplk.objects

import com.iyxan23.eplk.errors.EplkNotImplementedError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position

abstract class EplkObject {
    abstract val objectName: String

    var startPosition: Position? = null
        private set

    var endPosition: Position? = null
        private set

    var scope: Scope? = null
        private set

    fun setPosition(startPosition: Position, endPosition: Position): EplkObject {
        this.startPosition = startPosition
        this.endPosition = endPosition

        return this
    }

    fun setScope(scope: Scope): EplkObject {
        this.scope = scope

        return this
    }

    fun operatorPlus(other: EplkObject): RealtimeResult<EplkObject> {
        return RealtimeResult<EplkObject>().failure(EplkNotImplementedError(
            "+ operator is not implemented by $objectName",
            startPosition!!,
            endPosition!!
        ))
    }
}
