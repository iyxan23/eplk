package com.iyxan23.eplk.interpreter

import com.iyxan23.eplk.parser.nodes.*

object Interpreter {
    fun visitNode(node: Node, scope: Scope) {
        node.visit(node, scope)
    }
}
