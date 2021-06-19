package com.iyxan23.eplk.interpreter

import com.iyxan23.eplk.nodes.Node

/**
 * A very simple singleton object used for decoration purposes
 */
object Interpreter {
    fun visitNode(node: Node, scope: Scope) {
        node.visit(scope)
    }
}
