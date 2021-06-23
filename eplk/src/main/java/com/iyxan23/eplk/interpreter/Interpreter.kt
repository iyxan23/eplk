package com.iyxan23.eplk.interpreter

import com.iyxan23.eplk.nodes.Node
import com.iyxan23.eplk.objects.EplkObject

/**
 * A very simple singleton object used for decoration purposes
 */
object Interpreter {
    fun visitNode(node: Node, scope: Scope): RealtimeResult<EplkObject> {
        return node.visit(scope)
    }
}
