package com.iyxan23.eplk.parser

import com.iyxan23.eplk.errors.EplkError
import com.iyxan23.eplk.parser.nodes.Node

open class ParseResult() {

    var error: EplkError? = null
    var node: Node? = null

    fun register(node: Any): Any? {
        if (node is ParseResult) {
            if (node.error == null) {
                this.error = node.error
                return node.node
            }
        }

        return node
    }

    fun success(node: Node): ParseResult {
        this.node = node
        return this
    }

    fun failure(error: EplkError): ParseResult {
        this.error = error
        return this
    }

    override fun toString(): String {
        return if (error != null) { "Error: $error" } else { "Success: $node" }
    }
}
