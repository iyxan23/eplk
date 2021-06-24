package com.iyxan23.eplk.objects

import com.iyxan23.eplk.interpreter.Scope

class EplkString(
    val value: String,
    override val scope: Scope,
) : EplkObject(scope) {
    override val objectName: String = "String"
    override fun toString(): String = "\"$value\""
}