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
        returnValue != null

    // This is used to pass data between nodes
    val passedData: HashMap<String, String> by lazy { HashMap() }

    // variables used to determine if the node is doing return, continue, or break
    private var isBreaking = false
    private var isContinuing = false
    private var returnValue: EplkObject? = null

    fun register(result: RealtimeResult<T>): T? {
        if (result.shouldReturn) error = result.error
        value = result.value
        return value
    }

    fun success(value: T, dataToPass: Map<String, String>? = null): RealtimeResult<T> {
        this.value = value

        if (dataToPass != null) {
            passedData.putAll(dataToPass)
        }

        return this
    }

    fun successContinue(): RealtimeResult<T> {
        isContinuing = true
        return this
    }

    fun successBreak(): RealtimeResult<T> {
        isBreaking = true
        return this
    }

    fun returnValue(value: EplkObject): RealtimeResult<T> {
        returnValue = value
        return this
    }

    fun failure(error: EplkError): RealtimeResult<T> {
        this.error = error
        return this
    }

    override fun toString(): String {
        return if (shouldReturn) { "Error: $error" } else { "Success: $value" }
    }
}
