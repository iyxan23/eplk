package com.iyxan23.eplk.errors.runtime

import com.iyxan23.eplk.errors.EplkRuntimeError
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position

class EplkIndexOutOfBoundsError(
    override val detail: String,
    override val startPosition: Position,
    override val endPosition: Position,
    override val scope: Scope
) : EplkRuntimeError("IndexOutOfBoundsError", detail, startPosition, endPosition, scope)