package com.iyxan23.eplk.interpreter

import com.iyxan23.eplk.errors.EplkError

/**
 * This class is used to:
 * 1. Represent a result in interpreting
 * 2. Fail-early when an error occurred
 */
class RealtimeResult<T> {
    var error: EplkError? = null
        private set

    var value: T? = null
        private set

    val hasError get() = error != null

    fun register(result: RealtimeResult<T>): T? {
        if (result.hasError) error = result.error
        value = result.value
        return value
    }

    fun success(value: T): RealtimeResult<T> {
        this.value = value
        return this
    }

    fun failure(error: EplkError): RealtimeResult<T> {
        this.error = error
        return this
    }

    override fun toString(): String {
        return if (hasError) { "Error: $error" } else { "Success: $value" }
    }
}
