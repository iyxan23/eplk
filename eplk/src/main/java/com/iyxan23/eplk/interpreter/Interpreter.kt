package com.iyxan23.eplk.interpreter

import com.iyxan23.eplk.nodes.Node

object Interpreter {
    fun visitNode(node: Node, scope: Scope) {
        node.visit(scope)
    }
}
