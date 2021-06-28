package com.iyxan23.eplk.interpreter

import com.iyxan23.eplk.errors.runtime.EplkTypeError
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.objects.*
import java.util.*

object StandardDefinitions {
    fun generateScope(name: String): Scope {
        val scope = Scope(name)

        scope.symbolTable.variables["println"] = EplkNativeFunction(scope, "println", arrayOf("text"))
        { functionScope: Scope,
          arguments: Array<EplkObject>,
          _: Position,
          _: Position ->

            val result = RealtimeResult<EplkObject>()
            val `object` = arguments[0]

            println(`object`.toString())

            return@EplkNativeFunction result.success(EplkVoid(functionScope))
        }

        scope.symbolTable.variables["print"] = EplkNativeFunction(scope, "print", arrayOf("text"))
        { functionScope: Scope,
          arguments: Array<EplkObject>,
          _: Position,
          _: Position ->

            val result = RealtimeResult<EplkObject>()
            val `object` = arguments[0]

            print(`object`.toString())

            return@EplkNativeFunction result.success(EplkVoid(functionScope))
        }

        scope.symbolTable.variables["random"] = EplkNativeFunction(scope, "random", arrayOf("min", "max"))
        { functionScope: Scope,
          arguments: Array<EplkObject>,
          startPosition: Position,
          endPosition: Position ->
            val result = RealtimeResult<EplkObject>()

            val min = arguments[0]
            val max = arguments[1]

            if (min !is EplkInteger) {
                return@EplkNativeFunction result.failure(EplkTypeError(
                    "The argument min provided is expected to be an Integer, got ${min.objectName} instead",
                    startPosition, endPosition, scope
                ))
            }

            if (max !is EplkInteger) {
                return@EplkNativeFunction result.failure(EplkTypeError(
                    "The argument max provided is expected to be an Integer, got ${min.objectName} instead",
                    startPosition, endPosition, scope
                ))
            }

            return@EplkNativeFunction result.success(EplkInteger((min.value..max.value).random(), functionScope))
        }

        return scope
    }
}