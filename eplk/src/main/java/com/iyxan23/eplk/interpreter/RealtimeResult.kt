package com.iyxan23.eplk.interpreter

import com.iyxan23.eplk.errors.EplkError

class RealtimeResult() {
    var error: EplkError? = null
        private set

    var value: Any? = null
        private set

    val hasError get() = error != null

    fun register(result: RealtimeResult): Any? {
        if (result.hasError) error = result.error
        return value
    }

    fun success(value: Any): RealtimeResult {
        this.value = value
        return this
    }

    fun failure(error: EplkError): RealtimeResult {
        this.error = error
        return this
    }
}
