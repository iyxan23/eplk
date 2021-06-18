package com.iyxan23.eplk.interpreter

import com.iyxan23.eplk.errors.EplkError

class RealtimeResult<T>() {
    var error: EplkError? = null
        private set

    var value: T? = null
        private set

    val hasError get() = error != null

    fun register(result: RealtimeResult<T>): Any? {
        if (result.hasError) error = result.error
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
}
