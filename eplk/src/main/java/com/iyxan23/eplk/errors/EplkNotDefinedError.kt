package com.iyxan23.eplk.errors

import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position

class EplkNotDefinedError(
    override val detail: String,
    override val startPosition: Position,
    override val endPosition: Position,
    override val scope: Scope,
) : EplkRuntimeError("NotDefinedError", detail, startPosition, endPosition, scope)