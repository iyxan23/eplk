package com.iyxan23.eplk.parser.nodes

import com.iyxan23.eplk.interpreter.RealtimeResult
import com.iyxan23.eplk.interpreter.Scope

abstract class Node() {
    abstract fun visit(node: Node, scope: Scope): RealtimeResult
}
