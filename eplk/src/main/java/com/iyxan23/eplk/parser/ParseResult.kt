package com.iyxan23.eplk.parser

import com.iyxan23.eplk.errors.EplkError
import com.iyxan23.eplk.nodes.Node

/**
 * This class is used to:
 * 1. Represent a result
 * 2. Fail-early when there is an error
 *
 * @see Parser
 */
class ParseResult() {

    var error: EplkError? = null
        private set

    var node: Node? = null
        private set

    val hasError get() = error != null

    private var advancementCount = 0
    var reverseCount = 0

    fun registerAdvancement() {
        advancementCount++
    }

    fun register(result: ParseResult): Any? {
        if (result.error != null) {
            this.error = result.error
        }

        return result.node
    }

    fun tryRegister(result: ParseResult): Any? {
        if (result.hasError) {
            reverseCount = result.advancementCount
            return null
        }

        return register(result)
    }

    fun success(node: Node): ParseResult {
        this.node = node
        return this
    }

    fun failure(error: EplkError): ParseResult {
        if (!hasError || advancementCount == 0) this.error = error
        return this
    }

    override fun toString(): String {
        return if (error != null) { "Error: $error" } else { "Success: $node" }
    }
}
