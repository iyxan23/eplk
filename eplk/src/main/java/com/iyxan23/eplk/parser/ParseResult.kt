package com.iyxan23.eplk.parser

import com.iyxan23.eplk.errors.EplkError
import com.iyxan23.eplk.nodes.Node

class ParseResult() {

    var error: EplkError? = null
        private set

    var node: Node? = null
        private set

    val hasError get() = error != null

    fun register(result: Any): Any? {
        if (result is ParseResult) {
            if (result.error != null) {
                this.error = result.error
            }

            return result.node
        }

        return result
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
