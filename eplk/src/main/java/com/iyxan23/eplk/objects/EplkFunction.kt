package com.iyxan23.eplk.objects

import com.iyxan23.eplk.errors.runtime.EplkCallError
import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.interpreter.SymbolTable
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.nodes.StatementsNode
import kotlin.math.exp

class EplkFunction(
    scope: Scope,
    val functionName: String,
    val parameters: Array<String>,
    val statements: StatementsNode,
) : EplkObject(scope) {

    override val objectName: String = "Function"
    override fun toString(): String = "Function $functionName(${parameters.joinToString(", ")})"

    override fun call(
        arguments: Array<EplkObject>,
        startPosition: Position,
        endPosition: Position
    ): RealtimeResult<EplkObject> {
        val result = RealtimeResult<EplkObject>()

        if (arguments.size < parameters.size) {
            return result.failure(EplkCallError(
                "Too little arguments provided, expected ${parameters.size}, got ${arguments.size}",
                startPosition,
                endPosition,
                scope
            ))
        }

        if (arguments.size > parameters.size) {
            return result.failure(EplkCallError(
                "Too many arguments provided, expected ${parameters.size}, got ${arguments.size}",
                startPosition,
                endPosition,
                scope
            ))
        }

        val functionScope = Scope(
            "function $functionName(${parameters.joinToString(", ")})",
            SymbolTable(),
            scope,
            startPosition
        )

        parameters.forEachIndexed { index: Int, name: String ->
            functionScope.symbolTable.variables[name] = arguments[index]
        }

        // Alright let's execute the expression
        val expressionResult = result.register(statements.visit(functionScope))
        if (result.hasError) return result

        return result.success(expressionResult!!)
    }
}