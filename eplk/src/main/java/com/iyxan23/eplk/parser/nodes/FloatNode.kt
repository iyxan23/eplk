package com.iyxan23.eplk.parser.nodes

import com.iyxan23.eplk.lexer.models.Token

// Simply a float value, example: 0.1
data class FloatNode(
    val float: Token
) : Node()