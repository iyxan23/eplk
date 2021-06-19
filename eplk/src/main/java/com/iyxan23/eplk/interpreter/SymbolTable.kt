package com.iyxan23.eplk.interpreter

import com.iyxan23.eplk.objects.EplkObject

/**
 * This class stores every classes, objects, functions, and variables in a scope
 */
class SymbolTable {
    val variables = HashMap<String, EplkObject>()
}