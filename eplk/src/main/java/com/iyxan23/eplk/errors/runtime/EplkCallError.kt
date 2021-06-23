package com.iyxan23.eplk.errors.runtime

import com.iyxan23.eplk.errors.EplkRuntimeError
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position

class EplkCallError(
    detail: String,
    startPosition: Position,
    endPosition: Position,
    scope: Scope
) : EplkRuntimeError("CallError", detail, startPosition, endPosition, scope)