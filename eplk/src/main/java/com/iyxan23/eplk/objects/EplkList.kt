package com.iyxan23.eplk.objects

import com.iyxan23.eplk.interpreter.Scope

class EplkList(
    scope: Scope,
    val list: ArrayList<EplkObject>,

) : EplkObject(scope) {

    override val objectName: String = "List"
    override fun toString(): String = "[${list.joinToString(", ")}]"
}