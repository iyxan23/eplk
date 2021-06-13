package com.iyxan23.eplk.errors

import com.iyxan23.eplk.Position

abstract class Error(
    open val name: String,
    open val detail: String,
    open val startPosition: Position,
    open val endPosition: Position,
)