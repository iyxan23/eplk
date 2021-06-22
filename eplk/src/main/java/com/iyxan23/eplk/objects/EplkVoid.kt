package com.iyxan23.eplk.objects

import com.iyxan23.eplk.interpreter.Scope

class EplkVoid(scope: Scope) : EplkObject(scope) {
    override val objectName: String = "Void"
    override fun toString(): String = ""
}