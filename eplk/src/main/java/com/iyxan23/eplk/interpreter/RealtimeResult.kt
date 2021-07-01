package com.iyxan23.eplk.interpreter

import com.iyxan23.eplk.errors.EplkError
import com.iyxan23.eplk.objects.EplkObject

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

    val shouldReturn get() =
        error != null       ||
        isBreaking          ||
        isContinuing        ||
        isReturning

    // This is used to pass data between nodes
    val passedData: HashMap<String, String> by lazy { HashMap() }

    // variables used to determine if the node is doing return, continue, or break
    var isBreaking = false
    var isContinuing = false
    var isReturning = false
    var returnValue: EplkObject? = null

    private fun reset() {
        isBreaking = false
        isContinuing = false
        isReturning = false
        error = null
        returnValue = null
    }

    fun register(result: RealtimeResult<T>): T? {
        if (result.hasError) error = result.error

        value = result.value
        isBreaking = result.isBreaking
        isContinuing = result.isContinuing
        isReturning = result.isReturning
        returnValue = result.returnValue

        return value
    }

    fun success(value: T, dataToPass: Map<String, String>? = null): RealtimeResult<T> {
        reset()

        this.value = value

        if (dataToPass != null) {
            passedData.putAll(dataToPass)
        }

        return this
    }

    fun successContinue(): RealtimeResult<T> {
        reset()
        isContinuing = true
        return this
    }

    fun successBreak(): RealtimeResult<T> {
        reset()
        isBreaking = true
        return this
    }

    fun returnValue(value: EplkObject?): RealtimeResult<T> {
        reset()
        returnValue = value
        isReturning = true
        return this
    }

    fun failure(error: EplkError): RealtimeResult<T> {
        reset()
        this.error = error
        return this
    }

    override fun toString(): String {
        return if (shouldReturn) { "Error: $error" } else { "Success: $value" }
    }
}
