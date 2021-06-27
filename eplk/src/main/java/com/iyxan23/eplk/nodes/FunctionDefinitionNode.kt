package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position
import com.iyxan23.eplk.objects.EplkFunction
import com.iyxan23.eplk.objects.EplkObject
import com.iyxan23.eplk.objects.EplkVoid

class FunctionDefinitionNode(
    val functionName: String,
    val parameters: Array<String>,
    val statements: StatementsNode,
    override val startPosition: Position
) : Node() {
    override val endPosition: Position = statements.endPosition

    override fun visit(scope: Scope): RealtimeResult<EplkObject> {
        scope.symbolTable.variables[functionName] =
            EplkFunction(scope, functionName, parameters, statements)

        return RealtimeResult<EplkObject>().success(EplkVoid(scope))
    }
}