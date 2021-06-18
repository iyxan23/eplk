package com.iyxan23.eplk.nodes

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope
import com.iyxan23.eplk.lexer.models.Position

abstract class Node() {

    abstract val startPosition: Position
    abstract val endPosition: Position

    abstract fun visit(scope: Scope): RealtimeResult
}
