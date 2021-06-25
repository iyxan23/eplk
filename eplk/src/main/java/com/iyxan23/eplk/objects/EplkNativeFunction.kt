package com.iyxan23.eplk.objects

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position

// Note: What I meant by native is that this function is made in the java/kotlin language
class EplkNativeFunction(
    scope: Scope,
    val functionName: String,
    val parameters: Array<String>,
    val functionCallback:
        (
            scope: Scope,
            arguments: Array<EplkObject>,
            startPosition: Position,
            endPosition: Position
        ) -> RealtimeResult<EplkObject>

) : EplkObject(scope) {
    override val objectName: String = "Native function"
    override fun toString(): String = "Native function $functionName(${parameters.joinToString(", ")})"

    override fun call(
        arguments: Array<EplkObject>,
        startPosition: Position,
        endPosition: Position
    ): RealtimeResult<EplkObject>
        = functionCallback.invoke(scope, arguments, startPosition, endPosition)
}