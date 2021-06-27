package com.iyxan23.eplk.interpreter

import com.iyxan23.eplk.errors.runtime.EplkTypeError
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.objects.EplkNativeFunction
import com.iyxan23.eplk.objects.EplkObject
import com.iyxan23.eplk.objects.EplkString
import com.iyxan23.eplk.objects.EplkVoid

object StandardDefinitions {
    fun generateScope(name: String): Scope {
        val scope = Scope(name)

        scope.symbolTable.variables["println"] = EplkNativeFunction(scope, "println", arrayOf("text"))
        { functionScope: Scope,
          arguments: Array<EplkObject>,
          startPosition: Position,
          endPosition: Position ->

            val result = RealtimeResult<EplkObject>()
            val `object` = arguments[0]

            println(`object`.toString())

            return@EplkNativeFunction result.success(EplkVoid(functionScope))
        }

        return scope
    }
}